package main;
//Testkommentar

/**
 * @author nelli and etienne
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

}
