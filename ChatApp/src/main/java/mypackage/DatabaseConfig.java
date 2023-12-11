package mypackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DatabaseConfig {

	public static String driverPath = "com.mysql.cj.jdbc.Driver";
	public static String host = "localhost";
	public static String username = "root";
	public static String password = "";
	public static String dbname = "chat";
	public static int portNo = 3306;
	public String url = "jdbc:mysql://" + host + ":" + portNo + "/" + dbname;
	static Connection conn = null;

	public DatabaseConfig() throws ClassNotFoundException, SQLException {
		Class.forName(driverPath);
		conn = DriverManager.getConnection(url, username, password);
	}

	public void closeConnection() throws SQLException {
		conn.close();
	}

	public Connection getConnection() {
		return conn;
	}


}
