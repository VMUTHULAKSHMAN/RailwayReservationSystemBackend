package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		
	      Class.forName("com.mysql.cj.jdbc.Driver");

	      // variables
	      final String url = "jdbc:mysql:///railwayreservation";
	      final String user = "root";
	      final String password = "root";

	      // establish the connection
	      Connection con = DriverManager.getConnection(url, user, password);

	      // display status message
	      if (con == null) {
	         System.out.println("JDBC connection is not established");
	      } else
	         System.out.println("Congratulations," + 
	              " JDBC connection is established successfully.\n");

		return con;
	}
}
