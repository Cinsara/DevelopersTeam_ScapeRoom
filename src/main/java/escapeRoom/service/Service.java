package escapeRoom.service;


import escapeRoom.connectionManager.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public interface Service {

    String getDbName();

    default void getAll() throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        String query = "SELECT * FROM ?";
        PreparedStatement prepStatement = connection.prepareStatement(query);
        prepStatement.setString(1,getDbName());
    }
    void create();
    void modify(int id);

}
