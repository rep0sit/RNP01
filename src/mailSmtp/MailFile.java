package mailSmtp;

import java.awt.Dimension;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

import javax.swing.JPasswordField;
import javax.swing.UIManager;

/**
	 * @author nelli and etienne
	 *
	 */
	public class MailFile {

		/**
		 * @param args
		 */
		public static void main(String[] args) {
			String recipient = args[0];
			String attachment = args[1];
			//get Properties
			String konfigFile = "Konfigurationsdatei.txt";
			Properties props = MailUtils.getProp(konfigFile);
			String mail = props.getProperty("Mailadresse");
			
			final String password, message = "Enter your password!";
			if( System.console() == null ){ 
//				UIManager.put("OptionPane.minimuSize", new Dimension(1000,1000));
				  final JPasswordField pf = new JPasswordField(); 
				  password = JOptionPane.showConfirmDialog( null, pf, message,
				    JOptionPane.OK_CANCEL_OPTION,
				    JOptionPane.QUESTION_MESSAGE ) == JOptionPane.OK_OPTION ? 
				      new String( pf.getPassword() ) : "";
			}
			else {
			  password = new String( System.console().readPassword( "%s> ", message ) );
			}
			
			MailFileLogic mailFile;
			try {
				mailFile = new MailFileLogic(recipient, attachment, password, props);
				mailFile.loginSMTP();
				mailFile.sendMail();
				mailFile.quitSMTP();
				mailFile.closeFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
