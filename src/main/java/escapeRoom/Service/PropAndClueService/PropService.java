package escapeRoom.Service.PropAndClueService;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.model.GameArea.CluePropFactory.Prop;
import escapeRoom.model.GameArea.CluePropFactory.PropType;
import escapeRoom.Service.CrudeService;
import escapeRoom.Service.GetAllService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PropService implements CrudeService<Prop>, GetAllService<Prop> {

    private final Connection CONNECTION = ConnectionManager.getConnection();

    public Connection getCONNECTION() {
        return CONNECTION;
    }

    public PropService(Connection CONNECTION) throws SQLException {
    }

    @Override
    public String getTableName() {
        return "prop";
    }

    @Override
    public Prop mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        String propTypeString = resultSet.getString("prop_type");
        PropType type = PropType.valueOf(propTypeString.toUpperCase());

        int id = resultSet.getInt("prop_id");
        int room_room_id = resultSet.getInt(("room_room_id"));
        int value = resultSet.getInt("prop_value");
        Prop newProp = new Prop(type, id, room_room_id, value);
        return newProp;
    }

    @Override
    public Prop create(Prop entity) throws SQLException {
        String query = "INSERT INTO " + getTableName() + " (prop_type, prop_value, room_room_id) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getType().toString());
            preparedStatement.setInt(2, entity.getValue());
            preparedStatement.setInt(3, entity.getRoomId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating prop failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    System.out.println("Generated ID: " + generatedId); // Confirma el ID
                    entity.setId(generatedId); // Asegúrate de estar asignando el ID al objeto
                } else {
                    throw new SQLException("Failed to obtain generated ID.");
                }

            }
        }
        return entity;
    }

    @Override
    public Optional<Prop> read(int id) throws SQLException {
        String query = "SELECT * FROM " + getTableName() + " WHERE prop_id = ?";
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    // Get actual values from the database
                    int retrievedId = rs.getInt("prop_id");  // Real ID from DB
                    String propTypeString = rs.getString("prop_type");
                    int roomId = rs.getInt("room_room_id");
                    int value = rs.getInt("prop_value");

                    // Convert String to Enum
                    PropType type;
                    try {
                        type = PropType.valueOf(propTypeString.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        throw new SQLException("Invalid PropType found in database: " + propTypeString);
                    }

                    // Use the correct constructor for reading
                    return Optional.of(new Prop(type, retrievedId, roomId, value));
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public Prop update(Prop entity) throws SQLException {
        String query = "UPDATE " + getTableName() + " SET prop_type = ?, room_room_id = ?, prop_value = ? WHERE prop_id = ?";
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getType().toString());
            preparedStatement.setInt(2, entity.getRoomId());
            preparedStatement.setDouble(3, entity.getValue());
            preparedStatement.setInt(4, entity.getId());

            preparedStatement.executeUpdate();

            return entity;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String query = "DELETE FROM " + getTableName() + " WHERE prop_id = ?";
        try(PreparedStatement preparedStatement = CONNECTION.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() == 1;
        }
    }

    @Override
    public ResultSet getAll(Connection connection) throws SQLException {
        return GetAllService.super.getAll(connection);
    }

    @Override
    public List<Prop> getAllEntities(Connection connection) throws SQLException {
        return GetAllService.super.getAllEntities(connection);
    }
}

