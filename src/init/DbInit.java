package init;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import init.DbConnect;

public class DbInit {

	static String dbName = "NOTEPAD_KANDARP";
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
				s.executeUpdate("CREATE TABLE NOTES" + "(ID int NOT NULL AUTO_INCREMENT," + "DATA text NOT NULL,"
						+ "CT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
						+ "MT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," + "PRIMARY KEY (ID) )");
				s.executeUpdate("CREATE TABLE USER" + "(ID int NOT NULL AUTO_INCREMENT," + "NAME varchar(255) NOT NULL,"
						+ "EMAIL varchar(255))");
				s.executeUpdate("CREATE TABLE USER_NOTES" + "(ID int NOT NULL AUTO_INCREMENT," + "USER_ID int,"
						+ "NOTES_ID int)");
				s.close();
				System.out.println("database created");

			} else {
				System.out.println("database connected");
			}

			resultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error in dbinit");
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
		System.out.println("OKOKOKOKOKOKOKOK_____________________");
	}

}
