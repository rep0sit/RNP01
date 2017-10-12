package main;

/**
 * @author Nelli Welker, Etienne Onasch
 *
 */
public class MailFileImpl implements MailFile{

	@Override
	public String getEmailFromKonfigfile() {
		return "nelli.welker@web.de";
	}

	@Override
	public void getPasswordFromLogin(char[] pw) {
							
		System.out.println("PW: "+pw);
	}
	@Override
	public void loginSMTP() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void quitSMTP() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMail() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeFile() {
		// TODO Auto-generated method stub
		
	}

}
