package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Properties;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * @author Nelli Welker, Etienne Onasch
 *
 */
class MailFileLogicImpl implements MailFileLogic{
	
	private String konfigFile;
	private String recipient;
	//infos from Konfigfile
	private String mailAddr;
	private String userName;
	private String host;
	private int port;
	private String msgBody;
	//Attachmentfile
	private String attachmentFile;
	//Infos from LoginGUI
	private static String password_deprecated;
	private String password;
	private Properties props;
	
	private SSLSocket smtpSocket;
	private BufferedReader smtpIn;
	private OutputStreamWriter smtpOut;
	private String crlf = "\r\n";
	
	private File logFile;
	private FileWriter fw;
	
	private LoginGUI loginGui;
	
	MailFileLogicImpl(String recipient, String attachment, LoginGUI loginGui) throws IOException{
		this.recipient = recipient;
		this.konfigFile = MailUtils.CONFIG_FILE;
		this.loginGui = loginGui;
//		
		
		attachmentFile = attachment;
		
		props = MailUtils.getProp(konfigFile);
		mailAddr = props.getProperty("Mailadresse");
		setUserName(props.getProperty("Benutzername"));
		host = props.getProperty("Hostname");
		port = Integer.parseInt(props.getProperty("Portnummer"));
		msgBody = props.getProperty("Nachrichtenbody");
		logFile = new File("logFile.txt");
		fw = new FileWriter(logFile,true);
	}
	/**
	 * Methode, um die E-Mail-Adresse aus der Konfigurationsdatei auszulesen
	 */
	@Override
	public String getEmailFromKonfigfile() {
		return mailAddr;
	}
	
	
	/**
	 * Methode, um das Passwort aus dem Login zu holen und zu setzen
	 * @deprecated
	 */
	public static void givePasswordToImpl(char[] pw) {
		setPassword(pw.toString());
		// System.out.println("MailFileImpl PW: "+getPassword());
	}
	@Override
	public void loginSMTP() {
		try {
			smtpSocket = (SSLSocket)((SSLSocketFactory) SSLSocketFactory.getDefault()).createSocket(host,port);
		
			smtpIn = new BufferedReader(new InputStreamReader(smtpSocket.getInputStream()));
			smtpOut = new OutputStreamWriter(smtpSocket.getOutputStream());
			String result = smtpIn.readLine();
			System.out.println("receive ---> "+result);
			//Um sicherzustellen, dass die beiden hosts kommunizieren
			// HELO <SP> <domain> <CRLF>
			sendCommand("HELO "+host);
			//Base64 encodete Login-Daten
			this.password = loginGui.getPassword().toString();
			String userNameEncoded = Base64.getEncoder().encodeToString(userName.getBytes());
			String passwordEncoded = Base64.getEncoder().encodeToString(password.getBytes());
			// AUTH PLAIN
			sendCommand("AUTH PLAIN "+userNameEncoded+"\0"+userNameEncoded+"\0"+passwordEncoded);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * quitSMTP() quit the connection to SMTPServer
	 */
	@Override
	public void quitSMTP() {
		try {
			smtpIn.close();
			smtpOut.close();
			smtpSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Send the email with an attachment
	 */
	@Override
	public void sendMail() {
		System.out.println("Absender " + recipient + " anhang " + konfigFile + " password " + getPassword_deprecated());
		String boundary = "xyzzy_0123456789_xyzzy";
		String textTest ="Hello...i am here.";
		//			String crlf = "\r\n";
		try{
			sendCommand("MAIL FROM: " + mailAddr);
			sendCommand("RCPT TO: " + recipient);
			sendCommand("DATA");
			//Header
			smtpOut.write("From: "+mailAddr+" "+crlf);
			smtpOut.write("To: "+recipient+ " " + crlf);
			smtpOut.write("Subject: "+"Test" + " " + crlf);
			//Body
			smtpOut.write("MIME-Version: 1.0 "+crlf);          
			smtpOut.write("Content-Type: multipart/mixed; boundary= "+boundary+" "+crlf);
			smtpOut.write(crlf);
			smtpOut.write("--"+boundary+crlf);
			//Text der Nachricht
			smtpOut.write("Content-Type: text/plain; charset=ISO-8859-1 "+crlf);
			smtpOut.write(crlf);
			smtpOut.write(msgBody + crlf);
			smtpOut.write(crlf);
			smtpOut.write("--"+boundary+crlf);
			//Datei als Anhang
			String dateiName = attachmentFile.substring(attachmentFile.lastIndexOf("\\")+1, attachmentFile.length());
			smtpOut.write("Content-Type: application/octet-stream " + crlf);
			smtpOut.write("Content-Transfer-Encoding: base64 " + crlf);
			smtpOut.write("Content-Disposition: attachment;" + crlf + " filename="+dateiName+crlf);
			smtpOut.write(crlf);

			//File kodieren
			File file = new File(attachmentFile);
			String encoded = "";
			FileInputStream fileInputStreamReader = new FileInputStream(file);
			byte[] bytes = new byte[(int)file.length()];
			fileInputStreamReader.read(bytes);
			encoded = Base64.getMimeEncoder().encodeToString(bytes);
			//Senden der kodierten File-Datei
			smtpOut.write(encoded);

			//Abschlie�en
			smtpOut.write(crlf);
			smtpOut.write("--"+boundary+"--"+crlf);
			smtpOut.write(crlf+"."+crlf);     
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void closeFile() throws IOException {
		fw.close();
	}
	
	private boolean sendCommand(String command) throws IOException {
		smtpOut.write(command + crlf);
		smtpOut.flush();
		System.out.println("send ---> "+ command);
		writeToLogFile(command);
		String result = smtpIn.readLine();
		System.out.println("receive ---> "+ result);	
		writeToLogFile(result);
		return (result.substring(0,  3).equals("250"));
	}
	/**
	 * Protokollierung der gesendeten und empfangenen Nachrichten in einer Log-Datei
	 * 
	 * @param command
	 * @throws IOException 
	 */
	private void writeToLogFile(String command) throws IOException {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		fw.write(ts.toString() + " ---> "+ command + "\n");
	}
	@Deprecated
	private static String getPassword_deprecated() {
		return password_deprecated;
	}
	@Override
	public String getPassword() {
		return password;
	}
	
	
	public static void setPassword(String password) {
		MailFileLogicImpl.password_deprecated = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setAttachmentFile(String attachment) {
		attachmentFile = attachment;
	}
//	public static void start(){
//		loginSMTP();
//		sendMail();
//		quitSMTP();
//		closeFile();
//	}
}
