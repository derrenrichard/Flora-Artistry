package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Connect {
	private final String DATABASE = "floraartistry";
	private final String HOST = "localhost:3306";
	private final String USER = "root";
	private final String PASSWORD = "";
	private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
	
	private Connection con;
	
	private static Connect instance = null;
	
	private Connect() {
		try {
			con = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connect getInstance() {
		if (instance == null) {
			instance = new Connect();
		}
		return instance;
	}
	
	public PreparedStatement prepareStatement(String query) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}
	
}
