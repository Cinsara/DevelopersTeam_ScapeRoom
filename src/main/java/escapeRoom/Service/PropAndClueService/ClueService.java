package escapeRoom.Service.PropAndClueService;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Model.GameArea.CluePropFactory.Clue;
import escapeRoom.Model.GameArea.CluePropFactory.ClueType;
import escapeRoom.Service.CrudeService;
import escapeRoom.Service.GetAllService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ClueService implements CrudeService<Clue>, GetAllService<Clue> {
    private final Connection connection;

    public ClueService(Connection connection) throws SQLException {
        this.connection = connection;
    }

    @Override
    public String getTableName() {
        return "clue";
    }

    @Override
    public Clue mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        String clueTypeString = resultSet.getString("clue_type");
        ClueType type = ClueType.valueOf(clueTypeString.toUpperCase());

        int id = resultSet.getInt("clue_id");
        int room_room_id = resultSet.getInt(("room_room_id"));
        Clue newClue = new Clue(type, id, room_room_id);
        return newClue;
    }

    @Override
    public Clue create(Clue entity) throws SQLException {
        String query = "INSERT INTO " + getTableName() + " (clue_type, room_room_id) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getType().toString());
            preparedStatement.setInt(2, entity.getRoomId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating clue failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    entity.setId(generatedId);
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
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            try(ResultSet rs = preparedStatement.executeQuery()){
                if (rs.next()) {

                    String clueTypeString = rs.getString("clue_type");

                    ClueType type;
                    try {
                        type = ClueType.valueOf(clueTypeString.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        throw new SQLException("Invalid ClueType found in database: " + clueTypeString);
                    }

                    int roomId = rs.getInt("room_room_id");

                    return Optional.of(new Clue(type, id, roomId));
                } else {
                    return Optional.empty();

                }
            }
        }
    }

    @Override
    public Clue update(Clue entity) throws SQLException {
        String query = "UPDATE " + getTableName() + " SET clue_type = ?, room_room_id = ? WHERE clue_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
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
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() == 1;
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
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

