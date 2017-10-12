package main;

/**
 * @author Nelli Welker, Etienne Onasch
 *
 */
public interface MailFile {
//<<<<<<< HEAD

	/*
	 * 
	 */
	public String getEmailFromKonfigfile();
	/*
	 * 
	 */
	public void getPasswordFromLogin(char[] password);
//=======
	/**
	 * Methode f¸r einen login auf einem SMTP Server. <br>
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
	 * Methode um alle relevanten Streams zu schlieﬂen.<br>
	 */
	public void closeFile();
//>>>>>>> c81a5f280bb4979422611a830d9f42a69f522719
}
