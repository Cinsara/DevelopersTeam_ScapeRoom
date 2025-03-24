package escapeRoom.service;

import escapeRoom.connectionManager.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface GetAllService<T> extends CrudeService<T> {
    default ResultSet getAll(Connection connection) throws SQLException {
        String query = "SELECT * FROM " + getTableName();
        PreparedStatement prepStatement = connection.prepareStatement(query);
        return prepStatement.executeQuery();
    }
}
