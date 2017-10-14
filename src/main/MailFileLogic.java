package main;

import java.io.IOException;

/**
 * @author Nelli Welker, Etienne Onasch
 *
 */
interface MailFileLogic {

	/**
	 * Methode, um die Email-Adresse aus der Konfigurationsdatei auszulesen
	 * 
	 */
	public String getEmailFromKonfigfile();
//	/**
//	 * Methode, um das Passwort aus der Eingabe des Absenders zu holen
//	 * 
//	 */
//	public void givePasswordToImpl(char[] password);
	/**
	 * Methode f�r einen login auf einem SMTP Server. <br>
	 * 
	 */
	public void loginSMTP();
	/**
	 * Methode um sich vom SMTP Server abzumelden.<br>
	 */
	public void quitSMTP();
	/**
	 * Methode um eine Mail zu verschicken.<br>
	 */
	public void sendMail();
	/**
	 * Methode um alle relevanten Streams zu schlie�en.<br>
	 * @throws IOException 
	 */
	public void closeFile() throws IOException;
	//public String getPassword();
	public String getPassword();
}
