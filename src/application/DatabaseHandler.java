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
		Connection conn = null;
		try {
			conn = (Connection) DriverManager.getConnection(mysql_url, mysql_username, mysql_password);
			if (conn != null) {
				System.out.println("MSG: COMMECTED!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				System.out.println("database is connected successfully");
				String query = "select * from user where cnic = ? and password = ?";
				PreparedStatement preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, cnic);
				preparedStatement.setString(2, password);
				try {
					ResultSet qResult = preparedStatement.executeQuery();
					while (qResult.next()) {
						User newUser = new User();
						newUser.setFirstName(qResult.getString("firstname"));
						newUser.setFirstName(qResult.getString("lastname"));
					}
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
