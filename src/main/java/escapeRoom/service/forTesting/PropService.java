package escapeRoom.service.forTesting;

import escapeRoom.connectionManager.ConnectionManager;
import escapeRoom.service.CrudeService;
import escapeRoom.service.GetAllService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PropService implements CrudeService<Prop>, GetAllService<Prop> {

    private final Connection connection = ConnectionManager.getConnection();

    public Connection getConnection() {
        return connection;
    }

    public PropService() throws SQLException {
    }

    @Override
    public String getTableName() {
        return "prop";
    }

    @Override
    public Prop mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public Prop create(Prop entity) throws SQLException {
        String query = "INSERT INTO " + getTableName() + " (prop_type, prop_value, room_room_id) VALUES (?,?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1,entity.getType());
            preparedStatement.setInt(2,entity.getValue());
            preparedStatement.setInt(3,entity.getRoom_id());
            preparedStatement.executeUpdate();
            try {
                entity.setId(getGeneratedId(preparedStatement));
            }catch(SQLException e){}
            return entity;
        }
    }

    @Override
    public Optional<Prop> read(int id) throws SQLException {
        String query = "SELECT * FROM " + getTableName() + " WHERE prop_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            try(ResultSet rs = preparedStatement.executeQuery()){
                if (rs.next()){
                    String type = rs.getString("prop_type");
                    int value = rs.getInt("prop_value");
                    int room = rs.getInt("room_room_id");
                    return Optional.of(new Prop(id,type,value,room));
                }else{
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public Prop update(Prop entity) throws SQLException {
        String query = "UPDATE " + getTableName() + " SET prop_type = ?, prop_value = ?, room_room_id = ? WHERE prop_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1,entity.getType());
            preparedStatement.setInt(2,entity.getValue());
            preparedStatement.setInt(3,entity.getRoom_id());
            preparedStatement.setInt(4,entity.getId());
            preparedStatement.executeUpdate();
            return entity;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String query = "DELETE FROM " + getTableName() + " WHERE prop_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,id);
            return preparedStatement.executeUpdate() == 1;
        }
    }

}
