package tk.aa12mc.Tic_Tac_Toe;

import javax.swing.ImageIcon;

public class Constants {
	public static final ImageIcon IMAGE_X = new ImageIcon(Main.class.getResource("/resources/X.png"));
	public static final ImageIcon IMAGE_O = new ImageIcon(Main.class.getResource("/resources/O.png"));
	public static final ImageIcon GAMEFRAME_ICON = new ImageIcon(Main.class.getResource("/resources/GameFrame.jpg"));
	public static final ImageIcon SETTINGSFRAME_ICON = new ImageIcon(Main.class.getResource("/resources/Settings.png"));
	public static String[] SETTINGS_OPTIONS = { "Player VS Player", "Player VS Computer" };
	public static String[] SELECT_RESPONSES = { "X", "O" };
	public static String[] RESTART_RESPONSES = { "Restart with the same settings", "Restart with new settings",
			"Quit" };
}