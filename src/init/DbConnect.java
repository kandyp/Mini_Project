package init;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {
	
	public static String dbName = "NOTEPAD_KANDARP";
	public static final String URL = "jdbc:mysql://localhost:3306/";
	public static final String USER = "root";
	public static final String PASSWORD = "kandarp";
	private static DbConnect instance = new DbConnect();
	public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	
	private DbConnect() {
		try {
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.toString());
			System.out.print("Error while connecting");
		}
	}
	
	private Connection createConnection() {

		Connection connection = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			System.out.println("ERROR: Unable to Connect to Database.");
			System.out.println(e.toString());
		}
		return connection;
	}	
	
	public static Connection getConnection() {
		return instance.createConnection();
	}

}
