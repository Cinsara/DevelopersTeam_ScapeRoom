package escapeRoom.connectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    final static String URL = "jdbc:mysql://localhost:3306/your_database";
    final static String USER = "root";
    final static String PASSWORD = "mySqlRoot1948";
    static Connection connection;

    static public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
        }
        return connection;
    }
}
