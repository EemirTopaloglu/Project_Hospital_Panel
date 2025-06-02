package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/hospital";
    private static final String USER = "root";
    private static final String PASSWORD = "mert456";
    
    //The method throws SQLException, which allows DAOs to handle exceptions themselves.
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
	//For this to work at runtime, your project must have the MySQL 
//JDBC driver in its classpath (Maven mysql:mysql-connector-java.jar )