package main;

/**
 * @author Nelli Welker, Etienne Onasch
 *
 */
public class MailFileImpl implements MailFile{

	@Override
//<<<<<<< HEAD
	public String getEmailFromKonfigfile() {
		return "nelli.welker@web.de";
	}

	@Override
	public void getPasswordFromLogin(char[] pw) {
							
		System.out.println("PW: "+pw);
	}
//=======
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
		
//>>>>>>> c81a5f280bb4979422611a830d9f42a69f522719
	}

}
