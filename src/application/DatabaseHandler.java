package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseHandler {
	private static DatabaseHandler dbHandler;
	private String mysql_url = "jdbc:mysql://localhost:3306/homee";
	private String mysql_username = "root";
	private String mysql_password = "1234";

	private DatabaseHandler() {
		// TODO: if required
	}

	public static DatabaseHandler getInstance() {
		if (dbHandler == null) {
			dbHandler = new DatabaseHandler();
		}
		return dbHandler;
	}

	public boolean addNewUser(User user) {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
			if (con != null) {
				System.out.println("database is connected successfully");
				String query = "insert into users(firstname, lastname, birthdate, cnic, password) values (?, ?, ?, ?, ?);";
				PreparedStatement preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, user.getFirstName());
				preparedStatement.setString(2, user.getLastName());
				preparedStatement.setString(3, user.getBirthDate());
				preparedStatement.setString(4, user.getCnic());
				preparedStatement.setString(5, user.getPassword());
				try {
					int affected = preparedStatement.executeUpdate();
					if (affected > 0)
						return true;
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	public ArrayList<Room> getRooms(final int homeeID) {
		Connection con = null;
		try {
			ArrayList<Room> rooms = new ArrayList<Room>();
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
			if (con != null) {
				System.out.println("database is connected successfully");
				String query = "select * from room";
				PreparedStatement preparedStatement = con.prepareStatement(query);
				// preparedStatement.setString(1, cnic);
				// preparedStatement.setString(2, password);
				try {
					ResultSet qResult = preparedStatement.executeQuery();
					while (qResult.next()) {
						Room currRoom = new Room();
						currRoom.setId(qResult.getInt("id"));
						currRoom.setTitle(qResult.getString("title"));
						currRoom.setPowerStatus(qResult.getInt("powerStatus") == 1);
						currRoom.setNotificationStatus(qResult.getInt("notificationStatus") == 1);
						rooms.add(currRoom);
					}
					return rooms;
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public User getUser(String cnic, String password) {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
			if (con != null) {
				System.out.println("database is connected successfully");
				String query = "select * from users where cnic = ? and password = ?";
				PreparedStatement preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, cnic);
				preparedStatement.setString(2, password);
				try {
					ResultSet qResult = preparedStatement.executeQuery();
					if (qResult.next()) {
						User newUser = new User();
						newUser.setId(qResult.getInt("id"));
						newUser.setFirstName(qResult.getString("firstname"));
						newUser.setFirstName(qResult.getString("lastname"));
						newUser.setCnic(cnic);
						newUser.setPassword(password);
						newUser.setBirthDate(qResult.getString("birthdate"));
						return newUser;
					}
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public ArrayList<Integer> getHomeeID(int userID) {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
			if (con != null) {
				System.out.println("database is connected successfully");
				String query = "select hom.id from userhomee uhom join users usr on usr.id = uhom.userid "
						+ " join homee hom on hom.id = uhom.homeeid where uhom.userid = ?;";
				PreparedStatement preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, userID);
				try {
					ArrayList<Integer> homId = new ArrayList<>();
					ResultSet qResult = preparedStatement.executeQuery();
					while (qResult.next()) {
						homId.add(qResult.getInt("id"));
						System.out.println(qResult.getInt("id"));
					}
					return homId;
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public void createNewHomee(int userID) {
		
		
	}
}
