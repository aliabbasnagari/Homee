package HomeeUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Homee.CollectiveStatistics;
import Homee.Dashboard;
import Homee.Device;
import Homee.Room;
import Homee.User;

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
						newUser.setLastName(qResult.getString("lastname"));
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

	public User getUser(String cnic) {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
			if (con != null) {
				System.out.println("getUser by Cnic: database is connected successfully");
				String query = "select * from users where cnic = ?;";
				PreparedStatement preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, cnic);
				try {
					ResultSet qResult = preparedStatement.executeQuery();
					if (qResult.next()) {
						User newUser = new User();
						newUser.setId(qResult.getInt("id"));
						newUser.setFirstName(qResult.getString("firstname"));
						newUser.setLastName(qResult.getString("lastname"));
						newUser.setCnic(cnic);
						newUser.setPassword("******");
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

	public boolean updateUser(User user) {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
			if (con != null) {
				System.out.println("updateUser: database is connected successfully");
				String query = "update users set firstname = ?, lastname = ?, birthdate = ?, password = ? where id = ? ;";
				PreparedStatement preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, user.getFirstName());
				preparedStatement.setString(2, user.getLastName());
				preparedStatement.setString(3, user.getBirthDate());
				preparedStatement.setString(4, user.getPassword());
				preparedStatement.setInt(5, user.getId());
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

	public ArrayList<User> getHomeeUsers(int homeeID) {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
			if (con != null) {
				System.out.println("getHomeeUsers: database is connected successfully");
				String query = "select usr.* from userhomee uhom "
						+ " join users usr on usr.id = uhom.userid where uhom.homeeid = ?;";
				DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				PreparedStatement preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, homeeID);
				try {
					ArrayList<User> users = new ArrayList<>();
					ResultSet qResult = preparedStatement.executeQuery();
					while (qResult.next()) {
						User newuser = new User();
						newuser.setId(qResult.getInt("id"));
						newuser.setFirstName(qResult.getString("firstname"));
						newuser.setLastName(qResult.getString("lastname"));
						newuser.setBirthDate(qResult.getDate("birthDate").toLocalDate().format(dateFormat));
						newuser.setCnic(qResult.getString("cnic"));
						newuser.setPassword(qResult.getString("password"));
						users.add(newuser);
					}
					return users;
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

	public boolean addUserToHomee(int userID, int homeeID) {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
			if (con != null) {
				System.out.println("addUserToHomee: database is connected successfully");
				String query = "insert into userhomee(userid, homeeid) values (?, ?);";
				PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setInt(1, userID);
				preparedStatement.setInt(2, homeeID);
				int affected = preparedStatement.executeUpdate();
				if (affected > 0) {
					return true;
				}
			}
		} catch (Exception e) {
			System.out.println("EXCEPTION >>> " + e);
		}
		return false;
	}

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
							if (addUserToHomee(userID, homeeID)) {
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
						currRoom.setDevices(getDevices(currRoom.getId()));
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

	private int insertIntoStatistics() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
			if (con != null) {
				System.out.println("insertIntoStatistics: database is connected successfully");
				String query = "insert into statistics(powerUsage, temperature, humidity) values (?, ?, ?);";
				PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setDouble(1, 0);
				preparedStatement.setDouble(2, 0);
				preparedStatement.setDouble(3, 0);
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

	public Device[] getDevices(int roomID) {
		Device[] devices = new Device[9];
		for (int i = 0; i < devices.length; i++) {
			devices[i] = new Device(i);
		}
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
			if (con != null) {
				System.out.println("getDevices: database is connected successfully");
				String query = "select d.* from RoomDevice rd join device d on d.id = rd.deviceid "
						+ " where rd.roomid = ?;";
				PreparedStatement preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, roomID);
				try {
					ResultSet qResult = preparedStatement.executeQuery();
					int i = 0;
					while (qResult.next() && i < 10) {
						int dNo = qResult.getInt("deviceNo");
						devices[dNo].setId(qResult.getInt("id"));
						devices[dNo].setName(qResult.getString("devicename"));
						devices[dNo].setPower(qResult.getBoolean("powerStatus"));
						devices[dNo].setNotification(qResult.getBoolean("notificationStatus"));
						query = "select * from statistics where id = ?;";
						preparedStatement = con.prepareStatement(query);
						preparedStatement.setInt(1, qResult.getInt("deviceStatsId"));
						ResultSet qResult2 = preparedStatement.executeQuery();
						if (qResult2.next()) {
							devices[i].getDeviceStats().setId(qResult2.getInt("id"));
							devices[i].getDeviceStats().setPowerUsage(qResult2.getDouble("powerUsage"));
							devices[i].getDeviceStats().setTemperature(qResult2.getDouble("temperature"));
							devices[i].getDeviceStats().setHumidity(qResult2.getDouble("humidity"));
						}
						i++;
					}
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return devices;
	}

	public boolean createNewDevice(int roomId, Device device) {
		int statsid = insertIntoStatistics();
		device.getDeviceStats().setId(statsid);
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
			if (con != null) {
				System.out.println("createNewDevice: database is connected successfully");
				String query = "insert into device(deviceNo, devicename, powerStatus, notificationStatus, deviceStatsId) values (?, ?, ?, ?, ?);";
				PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setInt(1, device.getDeviceNo());
				preparedStatement.setString(2, device.getName());
				preparedStatement.setBoolean(3, device.getPower());
				preparedStatement.setBoolean(4, device.getNotification());
				preparedStatement.setInt(5, statsid);
				int affected = preparedStatement.executeUpdate();
				if (affected > 0) {
					try (ResultSet deviceKeys = preparedStatement.getGeneratedKeys()) {
						if (deviceKeys.next()) {
							device.setId(deviceKeys.getInt(1));
							query = "insert into RoomDevice(roomid, deviceid) values (?, ?);";
							preparedStatement = con.prepareStatement(query);
							preparedStatement.setInt(1, roomId);
							preparedStatement.setInt(2, device.getId());
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

	public boolean updateDevice(Device device) {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
			if (con != null) {
				System.out.println("updateDevice: database is connected successfully");
				String query = "UPDATE device SET devicename = ?, powerStatus = ?, notificationStatus = ? WHERE id = ?;";
				PreparedStatement preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, device.getName());
				preparedStatement.setBoolean(2, device.getPower());
				preparedStatement.setBoolean(3, device.getNotification());
				preparedStatement.setInt(4, device.getId());
				int affected = preparedStatement.executeUpdate();
				if (affected > 0) {
					query = "update statistics set powerUsage = ?, temperature = ?, humidity = ? where id = ?;";
					preparedStatement = con.prepareStatement(query);
					preparedStatement.setDouble(1, device.getDeviceStats().getPowerUsage());
					preparedStatement.setDouble(2, device.getDeviceStats().getTemperature());
					preparedStatement.setDouble(3, device.getDeviceStats().getHumidity());
					preparedStatement.setInt(4, device.getDeviceStats().getId());
					affected = preparedStatement.executeUpdate();
					if (affected > 0) {
						return true;
					}
				}
				return false;
			}
		} catch (Exception e) {
			System.out.println("EXCEPTION >>> " + e);
		}
		return false;
	}

	public boolean deleteDevice(int deviceID) {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
			if (con != null) {
				System.out.println("deleteRoom: database is connected successfully");
				String query = "DELETE FROM device WHERE id = ?;";
				PreparedStatement preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, deviceID);
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
