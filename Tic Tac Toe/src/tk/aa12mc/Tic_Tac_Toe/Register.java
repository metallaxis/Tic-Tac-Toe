package tk.aa12mc.Tic_Tac_Toe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import database.DBConnection;
import database.User;

public class Register implements ActionListener {

	private JFrame frame = new JFrame();
	private JButton Done = new JButton();
	private String username, email, password, max_Char;
	private JLabel Details = new JLabel();
	private JLabel Player = new JLabel();
	private JLabel Email = new JLabel();
	private JLabel Password = new JLabel();
	private JTextField player = new JTextField();
	private JTextField email1 = new JTextField();
	private JPasswordField pass = new JPasswordField();

	Register() {

		frame.setIconImage(Constants.SETTINGSFRAME_ICON.getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(235, 240);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setVisible(true);

		Done.setText("Done");
		Done.setBounds(77, 120, 80, 70);
		Done.addActionListener(this);

		Details.setText("Enter your details bellow:");
		Details.setBounds(30, 10, 200, 50);

		Player.setText("Player:");
		Player.setBounds(30, 30, 200, 50);

		player.setBounds(105, 45, 100, 20);
		player.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (player.getText().length() > 20) {
					max_Char = "Allowed maximum of 20 characters in username";
					JOptionPane.showMessageDialog(frame, max_Char, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		Email.setText("Email:");
		Email.setBounds(30, 50, 200, 50);

		email1.setBounds(105, 65, 100, 20);
		email1.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (player.getText().length() > 40) {
					max_Char = "Allowed maximum of 40 characters in email";
					JOptionPane.showMessageDialog(frame, max_Char, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		Password.setText("Password:");
		Password.setBounds(30, 70, 200, 50);

		pass.setBounds(105, 85, 100, 20);
		pass.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (String.valueOf(pass.getPassword()).length() > 20) {
					max_Char = "Allowed maximum of 20 characters in password";
					JOptionPane.showMessageDialog(frame, max_Char, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		frame.add(Done);
		frame.add(Details);
		frame.add(Player);
		frame.add(Email);
		frame.add(Password);
		frame.add(player);
		frame.add(email1);
		frame.add(pass);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == Done) {

			username = player.getText();
			email = email1.getText();
			password = String.valueOf(pass.getPassword());

			User findUser = DBConnection.findUser(username, password);

			if (findUser == null) {
				DBConnection.addUser(username, email, password);
				frame.dispose();
				new Settings();
			} else {
				JOptionPane.showMessageDialog(frame, "An account with this username already exists", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}