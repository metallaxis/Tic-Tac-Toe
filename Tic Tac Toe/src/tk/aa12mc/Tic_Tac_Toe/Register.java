package tk.aa12mc.Tic_Tac_Toe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Register implements ActionListener {
	
	private JFrame frame = new JFrame();
	private JButton Done = new JButton();
	private JLabel Details = new JLabel();
	private JLabel Player = new JLabel();
	private JLabel Email = new JLabel();
	private JLabel Password = new JLabel();
	private JTextField player = new JTextField();
	private JTextField email = new JTextField();
	private JTextField pass = new JTextField();
	
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
		
		Email.setText("Email:");
		Email.setBounds(30, 50, 200, 50);
		
		email.setBounds(105, 65, 100, 20);
		
		Password.setText("Password:");
		Password.setBounds(30, 70, 200, 50);
		
		pass.setBounds(105, 85, 100, 20);
		
		frame.add(Done);
		frame.add(Details);
		frame.add(Player);
		frame.add(Email);
		frame.add(Password);
		frame.add(player);
		frame.add(email);
		frame.add(pass);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == Done) {
			frame.dispose();
		}
	}
}