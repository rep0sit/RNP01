package main;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class testGUI extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public testGUI() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		textField = new JTextField();
		add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		add(btnNewButton);

	}

}
