package escapeRoom.connectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    static private final String URL = "jdbc:mysql://localhost:3306/escape_room";
    static private final String USER = "root";
    static private final String PASSWORD = "mySqlRoot1948";
    static Connection connection;

    static public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
        }
        return connection;
    }
}
