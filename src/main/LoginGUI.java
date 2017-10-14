package main;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * @author nelli, etienne
 *
 */
class LoginGUI {

	private JFrame frame;
	private  JTextField emailTextField;
	private JButton loginButton;
	private  String _sender;
	private  JPasswordField passwordField;
	private char[] password;
//	private MailFileImpl mailFileImpl;
	
	/**
	 * Launch the application.
	 */
	public void openGui(String sender) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI window = new LoginGUI();
					window.frame.setVisible(true);
					_sender = sender;
					System.out.println("SENDER "+sender);
					setEmailFromKonfig();
					//enterOnPasswordField();
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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 258, 275);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(6, 3, 1, 2));
		
		JLabel loginInstrLabel = new JLabel("Please enter your password!");
		frame.getContentPane().add(loginInstrLabel);
		
		JLabel emailLabel = new JLabel("E-Mail:");
		frame.getContentPane().add(emailLabel);
		
		emailTextField = new JTextField();
		emailTextField.setEditable(false);
		frame.getContentPane().add(emailTextField);
		emailTextField.setColumns(10);
		
		JLabel passwordLabel = new JLabel("Password:");
		frame.getContentPane().add(passwordLabel);
		
		loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("BUTN");
				//MailFileLogicImpl.givePasswordToImpl(getPassword());
				password = passwordField.getPassword();
				//frame.dispose();
			}
		});
		
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
//					mailFileImpl.givePasswordToImpl(getPasswordFromGui());
					//MailFileLogicImpl.givePasswordToImpl(getPassword());
					password = passwordField.getPassword();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		frame.getContentPane().add(passwordField);
		frame.getContentPane().add(loginButton);
	}
	public  void setEmailFromKonfig(){
		emailTextField.setText(_sender);
		System.out.println("EMAILTEXTFELD: "+emailTextField.getText());
	}
	public char[] getPassword(){
		
		//System.out.println("PW tostring:"+password.toString());
		return password;
	}
	
}
