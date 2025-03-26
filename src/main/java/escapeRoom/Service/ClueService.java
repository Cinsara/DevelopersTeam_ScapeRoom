package escapeRoom.Service;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.GameArea.CluePropFactory.Clue;
import escapeRoom.GameArea.CluePropFactory.ClueType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ClueService implements CrudeService<Clue>, GetAllService<Clue>{

    private final Connection CONNECTION = ConnectionManager.getConnection();

    public Connection getCONNECTION() {
        return CONNECTION;
    }

    public ClueService(Connection CONNECTION) throws SQLException {
    }

    @Override
    public String getTableName() {
        return "clue";
    }

    @Override
    public Clue mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public Clue create(Clue entity) throws SQLException {
        String query = "INSERT INTO " + getTableName() + " (clue_type, room_room_id) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getType().toString());
            preparedStatement.setInt(2, entity.getRoomId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating clue failed, no rows affected.");
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
    public Optional<Clue> read(int id) throws SQLException {
        String query = "SELECT * FROM " + getTableName() + " WHERE clue_id = ?";
        try(PreparedStatement preparedStatement = CONNECTION.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            try(ResultSet rs = preparedStatement.executeQuery()){
                if (rs.next()) {
                    // 1. Obtener el tipo de clue como String
                    String clueTypeString = rs.getString("clue_type");

                    // 2. Convertir el String a ClueType
                    ClueType type;
                    try {
                        type = ClueType.valueOf(clueTypeString.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        throw new SQLException("Invalid ClueType found in database: " + clueTypeString);
                    }

                    // 3. Obtener room_id (por si lo necesitas más adelante)
                    int roomId = rs.getInt("room_room_id");

                    // 4. Retornar el Clue correctamente
                    return Optional.of(new Clue(type, id));
                } else {
                    return Optional.empty();

                }
            }
        }
    }

    @Override
    public Clue update(Clue entity) throws SQLException {
        String query = "UPDATE " + getTableName() + " SET clue_type = ?, room_room_id = ? WHERE clue_id = ?";
        try(PreparedStatement preparedStatement = CONNECTION.prepareStatement(query)){
            preparedStatement.setString(1,entity.getType().toString());
            preparedStatement.setInt(2,entity.getRoomId());
            preparedStatement.setInt(3,entity.getId());
            preparedStatement.executeUpdate();
            return entity;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String query = "DELETE FROM " + getTableName() + " WHERE clue_id = ?";
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
    public List<Clue> getAllEntities(Connection connection) throws SQLException {
        return GetAllService.super.getAllEntities(connection);
    }
}

