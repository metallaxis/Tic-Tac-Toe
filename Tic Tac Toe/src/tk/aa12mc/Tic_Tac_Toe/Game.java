package tk.aa12mc.Tic_Tac_Toe;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Game extends JFrame {
	private JPanel PlayerPanel = new JPanel();
	private JPanel GamePanel = new JPanel();
	private JButton[][] button = new JButton[3][3];
	private JLabel Player1 = new JLabel();
	private JLabel Player2 = new JLabel();
	private JLabel turn = new JLabel();
	private JLabel timer1 = new JLabel();
	private JLabel timer2 = new JLabel();
	private JLabel nothing = new JLabel();
    private Timer timers1;
    private Timer timers2;
	private long time1 = 120, time2 = 120;
	private int spaces = 9;
	private long time, start, end;
	private ButtonListener buttonListener = new ButtonListener();
	private Settings settings;
	private ImageIcon playerIcon, opponentIcon;
	private ImageIcon leftTurnDefaultSize = new ImageIcon(Main.class.getResource("/resources/leftTurn.png"));
	private ImageIcon rightTurnDefaultSize = new ImageIcon(Main.class.getResource("/resources/rightTurn.png"));
	private Image leftImage = leftTurnDefaultSize.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
	private Image rightImage = rightTurnDefaultSize.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
	private ImageIcon leftTurn = new ImageIcon(leftImage);
	private ImageIcon rightTurn = new ImageIcon(rightImage);
	private String playerName, opponentName;

	Game(Settings settings) {
		this.settings = settings;
		PlayerPanel.setLayout(new GridLayout(2, 3, 0, 0));
		GamePanel.setLayout(new GridLayout(3, 3, 0, 0));
		choose();
		this.setTitle("TIC TAC TOE"); // sets title of the JFrame
		this.setIconImage(Constants.GAMEFRAME_ICON.getImage()); // Set GUI icon
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit of application
		this.setResizable(false); // Disable or enable resizing the this

		turn.setHorizontalAlignment(JLabel.CENTER);
		Player2.setHorizontalAlignment(JLabel.RIGHT);
		timer2.setHorizontalAlignment(JLabel.RIGHT);

		PlayerPanel.add(Player1);
		PlayerPanel.add(turn);
		PlayerPanel.add(Player2);
		PlayerPanel.add(timer1);
		PlayerPanel.add(nothing);
		PlayerPanel.add(timer2);

		Player1.setText(settings.getName1());
		turn.setIcon(leftTurn);
		Player2.setText(settings.getName2());
		timers1 = new Timer(1000, new TimerListener(1));
        timers1.setRepeats(true);
		timer1.setText("Time:" + time1 + " seconds");
		if (!settings.getName2().equals("Computer")) {
	        timers2 = new Timer(1000, new TimerListener(2));
	        timers2.setRepeats(true);
			timer2.setText("Time:" + time2 + " seconds");
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				button[i][j] = new JButton();
				button[i][j].setHorizontalTextPosition(JButton.CENTER);
				button[i][j].setVerticalTextPosition(JButton.CENTER);
				button[i][j].addActionListener(buttonListener);
				button[i][j].setSize(300, 300);
				button[i][j].setFocusable(false);
				GamePanel.add(button[i][j]);
			}
		}
		timers1.start();
		this.setSize(900, 937); // sets x and y dimensions
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.setVisible(true); // make GUI visible
		start = System.currentTimeMillis();
		this.add(PlayerPanel, BorderLayout.NORTH);
		this.add(GamePanel, BorderLayout.CENTER);
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
	    Timer timer = new Timer(2000, new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            switch (settings.getLevel()) {
	                case 0:
	                	for (int i = 0; i < 3; i++) {
	                        for (int j = 0; j < 3; j++) {
	                            button[i][j].setEnabled(true);
	                        }
	                    }
	                	timer2.setText("");
	                    easy_move();
	                    break;
	                case 1:
	                	for (int i = 0; i < 3; i++) {
	                        for (int j = 0; j < 3; j++) {
	                            button[i][j].setEnabled(true);
	                        }
	                    }
	                	timer2.setText("");
	                    medium_move();
	                    break;
	                case 2:
	                	for (int i = 0; i < 3; i++) {
	                        for (int j = 0; j < 3; j++) {
	                            button[i][j].setEnabled(true);
	                        }
	                    }
	                	timer2.setText("");
	                    hard_move();
	                    break;
	            }
	        }
	    });
	    timer.setRepeats(false);
	    if (settings.getLevel() == 0 || settings.getLevel() == 1 || settings.getLevel() == 2) {
	    	for (int i = 0; i < 3; i++) {
		        for (int j = 0; j < 3; j++) {
		            button[i][j].setEnabled(false);
		        }
		    }
		    timer2.setText("Thinking...");
	    	timer.start();
	    } else {
	    	if (Player1.getText().equals(playerName)) {
                timers2.stop();
                timers1.start();
            } else {
                timers1.stop();
                timers2.start();
            }
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
			new Game(settings);
		} else if (restart == 1) {
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
		} else if (time1 == 0) {
			endTimer();
			timers1.stop();
			if (!settings.getName2().equals("Computer")) {
				timers2.stop();
			}
			JOptionPane.showMessageDialog(this, opponentName + " won! Game Lasted: " + time + " seconds");
			restart();
		} else if (time2 == 0) {
			endTimer();
			timers1.stop();
			if (!settings.getName2().equals("Computer")) {
				timers2.stop();
			}
			JOptionPane.showMessageDialog(this, opponentName + " won! Game Lasted: " + time + " seconds");
			restart();
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
			timers1.stop();
			if (!settings.getName2().equals("Computer")) {
				timers2.stop();
			}
			JOptionPane.showMessageDialog(this, playerName + " won! Game Lasted: " + time + " seconds");
			restart();
			return true;
		} else if (spaces == 0) {
			endTimer();
			timers1.stop();
			if (!settings.getName2().equals("Computer")) {
				timers2.stop();
			}
			JOptionPane.showMessageDialog(this, "It's a draw! Game Lasted: " + time + " seconds");
			restart();
			return true;
		}

		ImageIcon tempIcon = playerIcon;
		playerIcon = opponentIcon;
		opponentIcon = tempIcon;
		String tempString = playerName;
		playerName = opponentName;
		opponentName = tempString;

		if (turn.getIcon().equals(leftTurn)) {
			turn.setIcon(rightTurn);
		} else {
			turn.setIcon(leftTurn);
		}
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
	
	private class TimerListener implements ActionListener {
        private int player;
        public TimerListener(int player) {
            this.player = player;
        }

        public void actionPerformed(ActionEvent e) {
            if (player == 1) {
                if (time1 > 0) {
                    time1--;
                    timer1.setText("Time:" + time1 + " seconds");
                }
            } else {
                if (time2 > 0) {
                    time2--;
                    timer2.setText("Time:" + time2 + " seconds");
                }
            }
            checkWin();
        }
    }
}