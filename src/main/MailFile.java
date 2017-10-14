package main;

import java.io.IOException;

import javax.swing.JOptionPane;

public class MailFile {

	public static void main(String[] args) throws IOException {
		MailFileLogic mailFileLogic;
		
		if(args.length != 2)
		{
			JOptionPane.showMessageDialog(null, "You must enter 2 Parameters: email and attachment", "ERROR!", 0);
		}
		else
		{
			//String fileConfig = "Konfigurationsdatei.txt";
			
			String recipient = args[0];
			String attachment = args[1];
			
			LoginGUI loginGui = new LoginGUI();
			mailFileLogic = new MailFileLogicImpl(recipient, attachment, loginGui);
			
			
			
			String emailFromKonfigFile = mailFileLogic.getEmailFromKonfigfile();
			loginGui.openGui(emailFromKonfigFile);
			
			//Uebergabe des Passworts funktioniert noch nicht so ganz....
			System.out.println("MAIN: " + mailFileLogic.getPassword());
			mailFileLogic.loginSMTP();
			mailFileLogic.sendMail();
			mailFileLogic.quitSMTP();
			
//			if(mailFile.password != null){
//				mailFile.givePasswordToImpl(loginGui.getPasswordFromGui());
//
//				mailFile.sendMail();
//			}else{
//				JOptionPane.showMessageDialog(null, "Password miss!", "You have to enter a password!", 0);
//			}
		}
	}
}
