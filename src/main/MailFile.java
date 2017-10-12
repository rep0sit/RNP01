package main;

/**
 * @author Nelli Welker, Etienne Onasch
 *
 */
public interface MailFile {
	public void loginSMTP();
	public void quitSMTP();
	public void sendMail();
	public void closeFile();
}
