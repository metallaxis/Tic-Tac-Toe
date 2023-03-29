package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

	private static final String userName = "Guest";
	private static final String password = "";
	private static final String serverName = "database.aa12mc.tk";
	private static final int portNumber = 3306;
	private static final String dbName = "tic-tac-toe";
	private static Connection conn = null;

	private static void connect() {
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);

		try {
			conn = DriverManager.getConnection("jdbc:mariadb://" + serverName + ":" + portNumber + "/" + dbName,
					connectionProps);
		} catch (SQLException e) {
			System.out.println("Cannot connect to database " + dbName + " !");
			e.printStackTrace();
			System.exit(0);
		}
	}

	private static void disconnect() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Cannot disconnect from database " + dbName + " !");
				e.printStackTrace();
				System.exit(0);
			}
		}
	}

	public static User addUser(String username, String email, String password) {
		connect();
		PreparedStatement stmt = null;
		String query = "INSERT INTO `users` (`username`, `email`, `password`) VALUES (?, ?, ?)";
		try {
			stmt = conn.prepareStatement(query);
			stmt.setString(1, username);
			stmt.setString(2, email);
			stmt.setString(3, password);
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Cannot execute " + stmt);
			e.printStackTrace();
			System.exit(0);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			}
		}
		disconnect();

		return null;

	}

	public static User findUser(String username, String password) {
		connect();
		PreparedStatement stmt = null;
		String query = "SELECT * FROM users WHERE username=? AND password=?;";
		ResultSet rs = null;
		User user = null;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setString(1, username);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String email = rs.getString("email");
				user = new User(id, username, email, password);
			}
		} catch (SQLException e) {
			System.out.println("Cannot execute " + stmt);
			e.printStackTrace();
			System.exit(0);
		} finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
		}
		disconnect();

		return user;
	}
	
	public static User locateUser(String username) {
		connect();
		PreparedStatement stmt = null;
		String query = "SELECT * FROM users WHERE username=?;";
		ResultSet rs = null;
		User user = null;
		try {
			stmt = conn.prepareStatement(query);
			stmt.setString(1, username);
			rs = stmt.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String email = rs.getString("email");
				String password = rs.getString("password");
				user = new User(id, username, email, password);
			}
		} catch (SQLException e) {
			System.out.println("Cannot execute " + stmt);
			e.printStackTrace();
			System.exit(0);
		} finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
		}
		disconnect();

		return user;
	}

}