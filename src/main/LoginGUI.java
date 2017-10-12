package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class LoginGUI {

	private JFrame frame;
	private JTextField emailTextField;
	private JButton loginButton;
	private MailFile mailFile;
	private JPasswordField passwordField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI window = new LoginGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginGUI() {
		initialize();
		mailFile = new MailFileImpl();
		setStartValues();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 395, 220);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][300px:n:300px,grow]", "[][][][][][][]"));
		
		JLabel loginInstrLabel = new JLabel("Please enter your password!");
		frame.getContentPane().add(loginInstrLabel, "cell 1 0");
		
		JLabel emailLabel = new JLabel("E-Mail:");
		frame.getContentPane().add(emailLabel, "cell 0 2,alignx trailing");
		
		emailTextField = new JTextField();
		emailTextField.setEditable(false);
		frame.getContentPane().add(emailTextField, "cell 1 2,growx");
		emailTextField.setColumns(10);
		
		JLabel passwordLabel = new JLabel("Password:");
		frame.getContentPane().add(passwordLabel, "cell 0 4,alignx trailing");
		
		loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getPassword();
			}
		});
		
		passwordField = new JPasswordField();
		frame.getContentPane().add(passwordField, "cell 1 4,growx");
		frame.getContentPane().add(loginButton, "cell 1 6,growx");
	}
	public void setEmailFromKonfig(){
		emailTextField.setText(mailFile.getEmailFromKonfigfile());
	}
	public void getPassword(){
		char[] password = passwordField.getPassword();
		mailFile.getPasswordFromLogin(password);
	}
	private void setStartValues() {
		setEmailFromKonfig();
		
	}
}
