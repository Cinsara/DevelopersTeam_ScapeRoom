package escapeRoom.service;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public interface CrudeService<T> {

    default int getGeneratedId(PreparedStatement preparedStatement) throws SQLException {
        try( ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
            if (generatedKeys.next()){
                return generatedKeys.getInt(1);
            }else {
                throw new SQLException("No key was generated");
            }
        }
    };
    String getTableName();
    T mapResultSetToEntity(ResultSet resultSet) throws SQLException;
    T create(T entity) throws SQLException;
    Optional<T> read (int id) throws SQLException;
    T update(T entity) throws SQLException;
    boolean delete(int id) throws SQLException;

}
