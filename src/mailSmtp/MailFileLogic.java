/**
 * 
 */
package mailSmtp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Properties;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
	 * @author nelli and etienne
	 *
	 */
	public class MailFileLogic {

		private String recipient;
		private String attachment;
		private String password;
		private Properties props;
		
		private String mailAddr;
		private String userName;
		private String host;
		private int port;
		private String msgBody;
		private SSLSocket smtpSocket;
		private BufferedReader smtpIn;
		private PrintWriter smtpOut;
		private String crlf = "\r\n";
		private File logFile;
		private FileWriter fw;
		
		
		public MailFileLogic(String recipient, String attachment, String password, Properties props) throws IOException {
			this.recipient = recipient;
			this.attachment = attachment;
			this.password = password;
			this.props = props;
			
			mailAddr = props.getProperty("Mailadresse");
			userName = props.getProperty("Benutzername");
			host = props.getProperty("Hostname");
			port = Integer.parseInt(props.getProperty("Portnummer"));
			msgBody = props.getProperty("Nachrichtenbody");
			logFile = new File("logFile.txt");
			fw = new FileWriter(logFile,true);	
		}
		/**
		 * Stellt die Verbindung zum SMTP-Server her
		 * 
		 */
		public void loginSMTP() {
			try {
				smtpSocket = (SSLSocket)((SSLSocketFactory) SSLSocketFactory.getDefault()).createSocket(host,port);
			
				smtpIn = new BufferedReader(new InputStreamReader(smtpSocket.getInputStream()));
//				smtpOut = new OutputStreamWriter(smtpSocket.getOutputStream());
				smtpOut = new PrintWriter(smtpSocket.getOutputStream());
				String result = smtpIn.readLine();
				System.out.println("receive ---> "+result);
				//Um sicherzustellen, dass die beiden hosts kommunizieren
				// HELO <SP> <domain> <CRLF>
				sendCommand("HELO "+host);
				// AUTH PLAIN
				sendCommand("AUTH PLAIN ");
				//Base64 encodete Login-Daten
				String pwUserEncoded = Base64.getEncoder().encodeToString((userName+"\0"+userName+"\0"+password).getBytes());
				sendCommand(pwUserEncoded);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/**
		 * Quit the connection to SMTPServer
		 */
		public void quitSMTP(){
			try {
				sendCommand("QUIT");
				//smtpIn.close();
				//smtpOut.close();
				smtpSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/**
		 * Send the email with an attachment
		 */
		public void sendMail() {
			//System.out.println("Absender " + recipient + " anhang " + konfigFile + " password " + getPassword_deprecated());
			String boundary = "xyzzy_0123456789_xyzzy";
			String textTest ="Hello...i am here.";
			try{
				sendCommand("MAIL FROM: " + mailAddr);
				sendCommand("RCPT TO: " + recipient);
				sendCommand("DATA");
				//Header
				smtpOut.print("From: "+mailAddr+" "+crlf);
				smtpOut.print("To: "+recipient+ " " + crlf);
				smtpOut.print("Subject: "+"Test" + " " + crlf);
				//Body
				smtpOut.print("MIME-Version: 1.0 "+crlf);          
				smtpOut.print("Content-Type: multipart/mixed; boundary= "+boundary+" "+crlf);
				smtpOut.print(crlf);
				smtpOut.print("--"+boundary+crlf);
				//Text der Nachricht
				smtpOut.print("Content-Type: text/plain; charset=ISO-8859-1 "+crlf);
				smtpOut.print(crlf);
				smtpOut.print(msgBody + crlf);
				smtpOut.print(crlf);
				smtpOut.print("--"+boundary+crlf);
				//Datei als Anhang
				String dateiName = attachment.substring(attachment.lastIndexOf("\\")+1, attachment.length());
				smtpOut.print("Content-Type: application/octet-stream " + crlf);
				smtpOut.print("Content-Transfer-Encoding: base64 " + crlf);
				smtpOut.print("Content-Disposition: attachment;" + crlf + " filename="+dateiName+crlf);
				smtpOut.print(crlf);

				//File kodieren
				File file = new File(attachment);
				String encoded = "";
				FileInputStream fileInputStreamReader = new FileInputStream(file);
				byte[] bytes = new byte[(int)file.length()];
				fileInputStreamReader.read(bytes);
				encoded = Base64.getMimeEncoder().encodeToString(bytes);
				//Senden der kodierten File-Datei
				smtpOut.print(encoded);

				//Abschliessen
				smtpOut.print(crlf);
				smtpOut.print("--"+boundary+"--"+crlf);
				smtpOut.print(crlf+"."+crlf);     
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		private boolean sendCommand(String command) throws IOException {
			smtpOut.print(command + crlf);
			smtpOut.flush();
			System.out.println("send ---> "+ command);
			writeToLogFile(command);
			String result = smtpIn.readLine();
			System.out.println("receive ---> "+ result);	
			writeToLogFile(result);
			return(result.substring(0, 3).equals("250"));
		}
		private void writeToLogFile(String command) throws IOException {
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			fw.write(ts.toString() + " ---> "+ command + "\n");	
		}
		/**
		 * 
		 */
		public void closeFile() throws IOException {
			fw.close();
		}
}
