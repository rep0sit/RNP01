package main;

import java.io.IOException;

import javax.swing.JOptionPane;

public class MailFile {

	public static void main(String[] args) throws IOException {
		MailFileImpl mailFile;
		
		if(args.length != 2)
		{
			JOptionPane.showMessageDialog(null, "You must enter 2 Parameters: email and attachment", "ERROR!", 0);
		}
		else
		{
			String fileConfig = "Konfigurationsdatei.txt";
			
			String recipient = args[0];
			String attachment = args[1];
			
			mailFile = new MailFileImpl(recipient, fileConfig, attachment);
			
			LoginGUI loginGui = new LoginGUI();
			
			String emailFromKonfigFile = mailFile.getEmailFromKonfigfile();
			loginGui.openGui(emailFromKonfigFile);
			
			//Uebergabe des Passworts funktioniert noch nicht so ganz....
			System.out.println("MAIN: "+mailFile.getPassword());
			mailFile.loginSMTP();
			mailFile.sendMail();
			mailFile.quitSMTP();
			
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
