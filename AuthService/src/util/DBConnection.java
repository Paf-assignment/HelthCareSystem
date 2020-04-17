package util;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.ws.rs.core.UriBuilder;

public class DBConnection {

	private static Connection connection;

	private DBConnection() {
	}

	public static Connection connect() throws SQLException, ClassNotFoundException {

		if (connection == null || connection.isClosed()) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Provide the correct details: DBServer/DBName, username, password
			connection = DriverManager
					.getConnection("jdbc:mysql://127.0.0.1:3306/helthcaresystem?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		}
		System.out.println("connected...");
		return connection;
  
		
	}

}
