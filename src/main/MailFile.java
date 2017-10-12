package main;

/**
 * @author Nelli Welker, Etienne Onasch
 *
 */
public interface MailFile {

	/**
	 * Methode, um die Email-Adresse aus der Konfigurationsdatei auszulesen
	 * 
	 */
	public String getEmailFromKonfigfile();
	/**
	 * Methode, um das Passwort aus der Eingabe des Absenders zu holen
	 * 
	 */
	public void getPasswordFromLogin(char[] password);
	/**
	 * Methode für einen login auf einem SMTP Server. <br>
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
	 * Methode um alle relevanten Streams zu schließen.<br>
	 */
	public void closeFile();
}
