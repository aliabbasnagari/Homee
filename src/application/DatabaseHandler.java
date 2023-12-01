package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
				System.out.println("addNewUser: database is connected successfully");
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

	public ArrayList<Room> getRooms(int dashID) {
		Connection con = null;
		try {
			ArrayList<Room> rooms = new ArrayList<Room>();
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
			if (con != null) {
				System.out.println("getRooms: database is connected successfully");
				String query = "select r.* from DashboardRoom dr " + " join room r on r.id = dr.roomid "
						+ " where dr.dashboardid = ?;";
				PreparedStatement preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, dashID);
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
				System.out.println("getUser: database is connected successfully");
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
					System.out.println("EXCEPTION >>> " + e);
				}
			}
		} catch (Exception e) {
			System.out.println("EXCEPTION >>> " + e);
		}
		return null;
	}

	public ArrayList<Pair<Integer, String>> getUserHomees(int userID) {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
			if (con != null) {
				System.out.println("getHomeeID: database is connected successfully");
				String query = "select hom.id, hom.title from userhomee uhom join users usr on usr.id = uhom.userid "
						+ " join homee hom on hom.id = uhom.homeeid where uhom.userid = ?;";
				PreparedStatement preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, userID);
				try {
					ArrayList<Pair<Integer, String>> homId = new ArrayList<>();
					ResultSet qResult = preparedStatement.executeQuery();
					while (qResult.next()) {
						homId.add(new Pair<Integer, String>(qResult.getInt("id"), qResult.getString("title")));
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

	public boolean populateCollectiveStats(int dashboardID) {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
			if (con != null) {
				System.out.println("populateCollectiveStats: database is connected successfully");
				String query = "select * from CollectiveStatistics where id = ?;";
				PreparedStatement preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, dashboardID);
				try {
					ResultSet qResult = preparedStatement.executeQuery();
					if (qResult.next()) {
						CollectiveStatistics c = CollectiveStatistics.getInstance();
						c.setId(qResult.getInt("id"));
						c.setPowerUsage(qResult.getDouble("powerUsage"));
						c.setPowerSaved(qResult.getDouble("powerSaved"));
						c.setTemperature(qResult.getDouble("temperature"));
						c.setHumidity(qResult.getDouble("humidity"));
					}
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

	public boolean populateDashboard(int homeeID) {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
			if (con != null) {
				System.out.println("populateDashboard: database is connected successfully");
				String query = "select dash.* from homee hom " + " join dashboard dash on dash.id = hom.dashboardid "
						+ " where hom.id = ?";
				PreparedStatement preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, homeeID);
				try {
					ResultSet qResult = preparedStatement.executeQuery();
					if (qResult.next()) {
						Dashboard dboard = Dashboard.getInstance();
						dboard.setId(qResult.getInt("id"));
						dboard.setPowerMode(qResult.getString("powerMode"));
						dboard.setRooms(getRooms(dboard.getId()));
						populateCollectiveStats(dboard.getId());
						return true;
					}
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	private int insertIntoCollectiveStats() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
			if (con != null) {
				System.out.println("insertIntoCollectiveStats: database is connected successfully");
				String query = "insert into collectivestatistics(powerUsage, powerSaved) values (?, ?);";
				PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setDouble(1, 0);
				preparedStatement.setDouble(2, 0);
				try {
					int affected = preparedStatement.executeUpdate();
					if (affected > 0) {
						try (ResultSet statsKeys = preparedStatement.getGeneratedKeys()) {
							if (statsKeys.next()) {
								return statsKeys.getInt(1);
							}
						}
					}
				} catch (Exception e) {
					System.out.println("EXCEPTION >>> " + e);
				}
			}
		} catch (Exception e) {
			System.out.println("EXCEPTION >>> " + e);
		}
		return -1;
	};

	private int insertIntoDashboard(int statsID) {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
			if (con != null) {
				System.out.println("insertIntoDashboard: database is connected successfully");
				String query = "insert into dashboard(powermode, fullstatsid) values (?, ?);";
				PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, "GRID");
				preparedStatement.setDouble(2, statsID);
				try {
					int affected = preparedStatement.executeUpdate();
					if (affected > 0) {
						try (ResultSet statsKeys = preparedStatement.getGeneratedKeys()) {
							if (statsKeys.next()) {
								return statsKeys.getInt(1);
							}
						}
					}
				} catch (Exception e) {
					System.out.println("EXCEPTION >>> " + e);
				}
			}
		} catch (Exception e) {
			System.out.println("EXCEPTION >>> " + e);
		}
		return -1;
	};

	public boolean createNewHomee(int userID, String title) {
		int statid = insertIntoCollectiveStats();
		if (statid == -1) {
			return false;
		}
		int dashid = insertIntoDashboard(statid);
		if (dashid == -1) {
			return false;
		}
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
			if (con != null) {
				System.out.println("createNewHomee: database is connected successfully");
				String query = "insert into homee(title, dashboardId) values (?, ?);";
				PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, title);
				preparedStatement.setInt(2, dashid);
				int affected = preparedStatement.executeUpdate();
				if (affected > 0) {
					try (ResultSet homeeKeys = preparedStatement.getGeneratedKeys()) {
						if (homeeKeys.next()) {
							int homeeID = homeeKeys.getInt(1);
							query = "insert into userhomee(userid, homeeid) values (?, ?);";
							preparedStatement = con.prepareStatement(query);
							preparedStatement.setInt(1, userID);
							preparedStatement.setInt(2, homeeID);
							affected = preparedStatement.executeUpdate();
							if (affected > 0) {
								return true;
							}
						}
					}
				}
				return false;
			}
		} catch (Exception e) {
			System.out.println("EXCEPTION >>> " + e);
		}
		return false;
	}

	public boolean createNewRoom(int dashId, String roomtitle) {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
			if (con != null) {
				System.out.println("createNewRoom: database is connected successfully");
				String query = "insert into room(title, powerStatus, notificationStatus) values (?, 0, 1);";
				PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, roomtitle);
				int affected = preparedStatement.executeUpdate();
				if (affected > 0) {
					try (ResultSet roomKeys = preparedStatement.getGeneratedKeys()) {
						if (roomKeys.next()) {
							int roomID = roomKeys.getInt(1);
							query = "insert into DashboardRoom(roomid, dashboardid) values (?, ?);";
							preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
							preparedStatement.setInt(1, roomID);
							preparedStatement.setInt(2, dashId);
							affected = preparedStatement.executeUpdate();
							if (affected > 0) {
								Dashboard dboard = Dashboard.getInstance();
								Room newRoom = new Room();
								newRoom.setId(roomID);
								newRoom.setTitle(roomtitle);
								newRoom.setPowerStatus(false);
								newRoom.setNotificationStatus(true);
								dboard.getRooms().add(newRoom);
								return true;
							}
						}
					}
				}
				return false;
			}
		} catch (Exception e) {
			System.out.println("EXCEPTION >>> " + e);
		}
		return false;
	}

	public boolean updateRoom(Room room) {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
			if (con != null) {
				System.out.println("updateRoom: database is connected successfully");
				String query = "UPDATE room SET title = ?, powerStatus = ?, notificationStatus = ? WHERE id = ?;";
				PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, room.getTitle());
				preparedStatement.setBoolean(2, room.isPowerStatus());
				preparedStatement.setBoolean(3, room.isNotificationStatus());
				preparedStatement.setInt(4, room.getId());
				int affected = preparedStatement.executeUpdate();
				if (affected > 0) {
					return true;
				}
				return false;
			}
		} catch (Exception e) {
			System.out.println("EXCEPTION >>> " + e);
		}
		return false;
	}

	public boolean deleteRoom(int roomID) {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
			if (con != null) {
				System.out.println("deleteRoom: database is connected successfully");
				String query = "DELETE FROM Room WHERE id = ?;";
				PreparedStatement preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, roomID);
				int affected = preparedStatement.executeUpdate();
				if (affected > 0) {
					return true;
				}
				return false;
			}
		} catch (Exception e) {
			System.out.println("EXCEPTION >>> " + e);
		}
		return false;
	}
}
