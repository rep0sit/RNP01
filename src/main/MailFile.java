package main;

/**
 * @author Nelli Welker, Etienne Onasch
 *
 */
public interface MailFile {
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
