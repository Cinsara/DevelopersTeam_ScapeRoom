package escapeRoom.service;

import escapeRoom.connectionManager.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface GetAllService extends CrudeService {
    default ResultSet getAll() throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        String query = "SELECT * FROM " + getTableName();
        PreparedStatement prepStatement = connection.prepareStatement(query);
        return prepStatement.executeQuery();
    }
}
