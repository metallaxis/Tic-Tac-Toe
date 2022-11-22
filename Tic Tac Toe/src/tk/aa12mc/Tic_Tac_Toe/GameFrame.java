package tk.aa12mc.Tic_Tac_Toe;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	private JButton[][] button = new JButton[3][3];
	private int spaces = 9;
	private long time, start, end;
	private ButtonListener buttonListener = new ButtonListener();
	private Settings settings;
	private ImageIcon playerIcon;
	private ImageIcon opponentIcon;
	private String playerName;
	private String opponentName;

	GameFrame(Settings settings) {
		this.settings = settings;
		choose();
		this.setTitle("TIC TAC TOE"); // sets title of the JFrame
		this.setIconImage(Constants.GAMEFRAME_ICON.getImage()); // Set GUI icon
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit of application
		this.setResizable(false); // Disable or enable resizing the this
		this.getContentPane().setBackground(new Color(255, 255, 255)); // Change Background color

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				button[i][j] = new JButton();
				button[i][j].setHorizontalTextPosition(JButton.CENTER);
				button[i][j].setVerticalTextPosition(JButton.CENTER);
				button[i][j].addActionListener(buttonListener);
				button[i][j].setSize(300, 300);
				button[i][j].setFocusable(false);
				this.add(button[i][j]);
			}
		}
		this.setSize(900, 937); // sets x and y dimensions
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(3, 3, 0, 0));
		this.setVisible(true); // make GUI visible
		start = System.currentTimeMillis();
	}

	public void choose() {
		int answer = JOptionPane.showOptionDialog(this, "Select what you want to start with:", null,
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, Constants.SELECT_RESPONSES, -1);

		switch (answer) {
		case 0:
			playerIcon = Constants.IMAGE_X;
			opponentIcon = Constants.IMAGE_O;
			playerName = settings.getName1();
			opponentName = settings.getName2();
			return;
		case 1:
			playerIcon = Constants.IMAGE_O;
			opponentIcon = Constants.IMAGE_X;
			playerName = settings.getName1();
			opponentName = settings.getName2();
			return;
		default:
			choose();
		}
	}

	public void Computer_Mode() {
		switch (settings.getLevel()) {
		case -1:
			return;
		case 0:
			easy_move();
			return;
		case 1:
			medium_move();
			return;
		case 2:
			hard_move();
			return;
		}
	}

	private void easy_move() {
		Random random = new Random();
		int r = random.nextInt(spaces);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (button[i][j].getIcon() == null) {
					if (r == 0) {
						button[i][j].setIcon(playerIcon);
						button[i][j].removeActionListener(buttonListener);
						change_turn_and_check_end();
						return;
					} else
						r--;
				}
			}
		}
	}

	private void medium_move() {
		int loosing_spaces = count_loosing_spaces();
		if (loosing_spaces > 0) {
			Random random = new Random();
			int r = random.nextInt(loosing_spaces);
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (looses(i, j)) {
						if (r == 0) {
							button[i][j].setIcon(playerIcon);
							button[i][j].removeActionListener(buttonListener);
							change_turn_and_check_end();
							return;
						} else
							r--;
					}
				}
			}
		} else {
			easy_move();
		}
	}

	private void hard_move() {
		int winning_spaces = count_winning_spaces();
		if (winning_spaces > 0) {
			Random random = new Random();
			int r = random.nextInt(winning_spaces);
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (wins(i, j)) {
						if (r == 0) {
							button[i][j].setIcon(playerIcon);
							button[i][j].removeActionListener(buttonListener);
							change_turn_and_check_end();
							return;
						} else
							r--;
					}
				}
			}
		} else {
			medium_move();
		}
	}

	private boolean wins(int i, int j) {
		if (button[i][j].getIcon() == null) {
			if (i == 0 && j == 0 && safe_equals(button[0][1], playerIcon) && safe_equals(button[0][2], playerIcon)) {
				return true;
			}
			if (safe_equals(button[0][0], playerIcon) & i == 0 && j == 1 && safe_equals(button[0][2], playerIcon)) {
				return true;
			}
			if (safe_equals(button[0][0], playerIcon) & safe_equals(button[0][1], playerIcon) && i == 0 && j == 2) {
				return true;
			} else if (i == 1 && j == 0 && safe_equals(button[1][1], playerIcon)
					&& safe_equals(button[1][2], playerIcon)) {
				return true;
			} else if (safe_equals(button[1][0], playerIcon) && i == 1 && j == 1
					&& safe_equals(button[1][2], playerIcon)) {
				return true;
			} else if (safe_equals(button[1][0], playerIcon) && safe_equals(button[1][1], playerIcon) && i == 1
					&& j == 2) {
				return true;
			} else if (i == 2 && j == 0 && safe_equals(button[2][1], playerIcon)
					&& safe_equals(button[2][2], playerIcon)) {
				return true;
			} else if (safe_equals(button[2][0], playerIcon) && i == 2 && j == 1
					&& safe_equals(button[2][2], playerIcon)) {
				return true;
			} else if (safe_equals(button[2][0], playerIcon) && safe_equals(button[2][1], playerIcon) && i == 2
					&& j == 2) {
				return true;
			} else if (i == 0 && j == 0 && safe_equals(button[1][0], playerIcon)
					&& safe_equals(button[2][0], playerIcon)) {
				return true;
			} else if (safe_equals(button[0][0], playerIcon) && i == 1 && j == 0
					&& safe_equals(button[2][0], playerIcon)) {
				return true;
			} else if (safe_equals(button[0][0], playerIcon) && safe_equals(button[1][0], playerIcon) && i == 2
					&& j == 0) {
				return true;
			} else if (i == 0 && j == 1 && safe_equals(button[1][1], playerIcon)
					&& safe_equals(button[2][1], playerIcon)) {
				return true;
			} else if (safe_equals(button[0][1], playerIcon) && i == 1 && j == 1
					&& safe_equals(button[2][1], playerIcon)) {
				return true;
			} else if (safe_equals(button[0][1], playerIcon) && safe_equals(button[1][1], playerIcon) && i == 2
					&& j == 1) {
				return true;
			} else if (i == 0 && j == 0 && safe_equals(button[1][1], playerIcon)
					&& safe_equals(button[2][2], playerIcon)) {
				return true;
			} else if (safe_equals(button[0][0], playerIcon) && i == 1 && j == 1
					&& safe_equals(button[2][2], playerIcon)) {
				return true;
			} else if (safe_equals(button[0][0], playerIcon) && safe_equals(button[1][1], playerIcon) && i == 2
					&& j == 2) {
				return true;
			} else if (i == 0 && j == 2 && safe_equals(button[1][1], playerIcon)
					&& safe_equals(button[2][0], playerIcon)) {
				return true;
			} else if (safe_equals(button[0][2], playerIcon) && i == 1 && j == 1
					&& safe_equals(button[2][0], playerIcon)) {
				return true;
			} else if (safe_equals(button[0][2], playerIcon) && safe_equals(button[1][1], playerIcon) && i == 2
					&& j == 0) {
				return true;
			} else if (i == 0 && j == 2 && safe_equals(button[1][2], playerIcon)
					&& safe_equals(button[2][2], playerIcon)) {
				return true;
			} else if (safe_equals(button[0][2], playerIcon) && i == 1 && j == 2
					&& safe_equals(button[2][2], playerIcon)) {
				return true;
			} else if (safe_equals(button[0][2], playerIcon) && safe_equals(button[1][2], playerIcon) && i == 2
					&& j == 2) {
				return true;
			}
		}
		return false;
	}

	private boolean looses(int i, int j) {
		if (button[i][j].getIcon() == null) {
			if (i == 0 && j == 0 && safe_equals(button[0][1], opponentIcon)
					&& safe_equals(button[0][2], opponentIcon)) {
				return true;
			}
			if (safe_equals(button[0][0], opponentIcon) & i == 0 && j == 1 && safe_equals(button[0][2], opponentIcon)) {
				return true;
			}
			if (safe_equals(button[0][0], opponentIcon) & safe_equals(button[0][1], opponentIcon) && i == 0 && j == 2) {
				return true;
			} else if (i == 1 && j == 0 && safe_equals(button[1][1], opponentIcon)
					&& safe_equals(button[1][2], opponentIcon)) {
				return true;
			} else if (safe_equals(button[1][0], opponentIcon) && i == 1 && j == 1
					&& safe_equals(button[1][2], opponentIcon)) {
				return true;
			} else if (safe_equals(button[1][0], opponentIcon) && safe_equals(button[1][1], opponentIcon) && i == 1
					&& j == 2) {
				return true;
			} else if (i == 2 && j == 0 && safe_equals(button[2][1], opponentIcon)
					&& safe_equals(button[2][2], opponentIcon)) {
				return true;
			} else if (safe_equals(button[2][0], opponentIcon) && i == 2 && j == 1
					&& safe_equals(button[2][2], opponentIcon)) {
				return true;
			} else if (safe_equals(button[2][0], opponentIcon) && safe_equals(button[2][1], opponentIcon) && i == 2
					&& j == 2) {
				return true;
			} else if (i == 0 && j == 0 && safe_equals(button[1][0], opponentIcon)
					&& safe_equals(button[2][0], opponentIcon)) {
				return true;
			} else if (safe_equals(button[0][0], opponentIcon) && i == 1 && j == 0
					&& safe_equals(button[2][0], opponentIcon)) {
				return true;
			} else if (safe_equals(button[0][0], opponentIcon) && safe_equals(button[1][0], opponentIcon) && i == 2
					&& j == 0) {
				return true;
			} else if (i == 0 && j == 1 && safe_equals(button[1][1], opponentIcon)
					&& safe_equals(button[2][1], opponentIcon)) {
				return true;
			} else if (safe_equals(button[0][1], opponentIcon) && i == 1 && j == 1
					&& safe_equals(button[2][1], opponentIcon)) {
				return true;
			} else if (safe_equals(button[0][1], opponentIcon) && safe_equals(button[1][1], opponentIcon) && i == 2
					&& j == 1) {
				return true;
			} else if (i == 0 && j == 0 && safe_equals(button[1][1], opponentIcon)
					&& safe_equals(button[2][2], opponentIcon)) {
				return true;
			} else if (safe_equals(button[0][0], opponentIcon) && i == 1 && j == 1
					&& safe_equals(button[2][2], opponentIcon)) {
				return true;
			} else if (safe_equals(button[0][0], opponentIcon) && safe_equals(button[1][1], opponentIcon) && i == 2
					&& j == 2) {
				return true;
			} else if (i == 0 && j == 2 && safe_equals(button[1][1], opponentIcon)
					&& safe_equals(button[2][0], opponentIcon)) {
				return true;
			} else if (safe_equals(button[0][2], opponentIcon) && i == 1 && j == 1
					&& safe_equals(button[2][0], opponentIcon)) {
				return true;
			} else if (safe_equals(button[0][2], opponentIcon) && safe_equals(button[1][1], opponentIcon) && i == 2
					&& j == 0) {
				return true;
			} else if (i == 0 && j == 2 && safe_equals(button[1][2], opponentIcon)
					&& safe_equals(button[2][2], opponentIcon)) {
				return true;
			} else if (safe_equals(button[0][2], opponentIcon) && i == 1 && j == 2
					&& safe_equals(button[2][2], opponentIcon)) {
				return true;
			} else if (safe_equals(button[0][2], opponentIcon) && safe_equals(button[1][2], opponentIcon) && i == 2
					&& j == 2) {
				return true;
			}
		}
		return false;
	}

	private int count_winning_spaces() {
		int count = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (wins(i, j)) {
					count++;
				}
			}
		}
		return count;
	}

	private int count_loosing_spaces() {
		int count = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (looses(i, j)) {
					count++;
				}
			}
		}
		return count;
	}

	public void restart() {
		int restart = JOptionPane.showOptionDialog(this, "Select an option:", null, JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, Constants.RESTART_RESPONSES, -1);
		if (restart == 0) {
			this.dispose();
			new GameFrame(settings);
		} else if (restart == 1){
			dispose();
			new Settings();
		} else if (restart == 2) {
			System.exit(0);
		}
	}

	public boolean safe_equals(JButton button, ImageIcon image) {
		if (button.getIcon() != null) {
			return button.getIcon().equals(image);
		} else {
			return false;
		}
	}

	public boolean checkWin() {
		if (safe_equals(button[0][0], playerIcon) && safe_equals(button[0][1], playerIcon)
				&& safe_equals(button[0][2], playerIcon)) {
			return true;
		} else if (safe_equals(button[1][0], playerIcon) && safe_equals(button[1][1], playerIcon)
				&& safe_equals(button[1][2], playerIcon)) {
			return true;
		} else if (safe_equals(button[2][0], playerIcon) && safe_equals(button[2][1], playerIcon)
				&& safe_equals(button[2][2], playerIcon)) {
			return true;
		} else if (safe_equals(button[0][0], playerIcon) && safe_equals(button[1][0], playerIcon)
				&& safe_equals(button[2][0], playerIcon)) {
			return true;
		} else if (safe_equals(button[0][1], playerIcon) && safe_equals(button[1][1], playerIcon)
				&& safe_equals(button[2][1], playerIcon)) {
			return true;
		} else if (safe_equals(button[0][2], playerIcon) && safe_equals(button[1][2], playerIcon)
				&& safe_equals(button[2][2], playerIcon)) {
			return true;
		} else if (safe_equals(button[0][0], playerIcon) && safe_equals(button[1][1], playerIcon)
				&& safe_equals(button[2][2], playerIcon)) {
			return true;
		} else if (safe_equals(button[0][2], playerIcon) && safe_equals(button[1][1], playerIcon)
				&& safe_equals(button[2][0], playerIcon)) {
			return true;
		}
		return false;
	}

	public void endTimer() {
		end = System.currentTimeMillis();
		time = (end - start) / 1000;
	}

	private boolean change_turn_and_check_end() {
		spaces--;
		if (checkWin()) {
			endTimer();
			JOptionPane.showMessageDialog(this, playerName + " won! Game Lasted: " + time);
			restart();
			return true;
		} else if (spaces == 0) {
			endTimer();
			JOptionPane.showMessageDialog(this, "It's a draw! Game Lasted: " + time);
			restart();
			return true;
		}

		ImageIcon tempIcon = playerIcon;
		playerIcon = opponentIcon;
		opponentIcon = tempIcon;

		String tempString = playerName;
		playerName = opponentName;
		opponentName = tempString;
		
		return false;
	}

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (e.getSource() == button[i][j]) {
						button[i][j].setIcon(playerIcon);
						button[i][j].removeActionListener(this);
						if (!change_turn_and_check_end()) {
							Computer_Mode();
						}
					}
				}
			}
		}
	}
}