package escapeRoom.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface GetAllService<T> extends CrudeService<T> {

    default ResultSet getAll(Connection connection) throws SQLException {
        String query = "SELECT * FROM " + getTableName();
        PreparedStatement prepStatement = connection.prepareStatement(query);
        return prepStatement.executeQuery();
    }

    default List<T> getAllEntities(Connection connection) throws SQLException {
        List<T> entities = new ArrayList<>();
        try (ResultSet resultSet = getAll(connection)){
          while(resultSet.next()){
              entities.add(mapResultSetToEntity(resultSet));
          }
        }
        return entities;
    }
}
