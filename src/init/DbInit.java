package init;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import init.DbConnect;

public class DbInit {

	static String dbName = DbConnect.dbName;
	ResultSet rs = null;
	Connection connection = null;
	Statement statement = null;

	DbInit() {
		try {

			connection = DbConnect.getConnection();
			ResultSet resultSet = connection.createStatement().executeQuery("SHOW DATABASES LIKE '" + dbName + "';");

			if (!resultSet.next()) {

				Statement s = connection.createStatement();
				s.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
				s.execute("use " + dbName);
				s.executeUpdate("CREATE TABLE NOTES" 
					+ "(ID int NOT NULL AUTO_INCREMENT," 
					+ "DATA text NOT NULL,"
					+ "CT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
					+ "MT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," 
					+ "PRIMARY KEY (ID) )");
				s.executeUpdate("CREATE TABLE USER_NOTES" 
						+ "(ID int NOT NULL AUTO_INCREMENT PRIMARY KEY," 
						+ "USER_ID int,"
						+ "NOTES_ID int)");
				s.executeUpdate("CREATE TABLE USERS (UID int NOT NULL AUTO_INCREMENT PRIMARY KEY , NAME varchar(63) , PASS varchar(63) , EMAIL varchar(63) UNIQUE )");
				s.close();
				System.out.println("database created");

			} else {
				System.out.println("database connected");
			}

			resultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error in SQL");
			
			try {
				connection.createStatement().executeUpdate("DROP DATABASE `notepad_kandarp`");
				System.out.println("Database Dropped");
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println("Database could'nt be dropped/created");
			}

			
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.print("error while closing init");
				}
			}
		}
	}

	void run() {
		System.out.println("DbInit Terminated");
	}

}
