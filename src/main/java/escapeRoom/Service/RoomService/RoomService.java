package escapeRoom.Service.RoomService;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.CheckExistenceService;
import escapeRoom.model.GameArea.RoomBuilder.Difficulty;
import escapeRoom.model.GameArea.RoomBuilder.Room;
import escapeRoom.model.GameArea.RoomBuilder.Theme;
import escapeRoom.Service.GetAllService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class RoomService implements GetAllService<Room>, CheckExistenceService<Room> {
    private final Connection connection = ConnectionManager.getConnection();

    public Connection getConnection() {
        return connection;
    }

    public RoomService() throws SQLException {
    }

    @Override
    public String getTableName() {
        return "room";
    }

    private String mapStringToTheme(String dbValue) {
        if (dbValue == null) {
            throw new IllegalArgumentException("Theme can't be null.");
        }
        return switch (dbValue.trim()) {
            case "Love Affair" -> Theme.LOVEAFFAIR.getDisplayName();
            case "Fantastic" -> Theme.FANTASTIC.getDisplayName();
            case "Mystery" -> Theme.MYSTERY.getDisplayName();
            case "Sci-Fi" -> Theme.SCIFI.getDisplayName();
            default -> throw new IllegalArgumentException("Valor not supported for Theme: " + dbValue);
        };
    }

    private Difficulty mapStringToDifficulty(String dbValue) {
        if (dbValue == null) {
            throw new IllegalArgumentException("Difficulty can't be null.");
        }
        return switch (dbValue.trim().toUpperCase()) {
            case "EASY" -> Difficulty.EASY;
            case "MEDIUM" -> Difficulty.MEDIUM;
            case "HARD" -> Difficulty.HARD;
            default -> throw new IllegalArgumentException("Valor not supported for Difficulty: " + dbValue);
        };
    }

    @Override
    public Room mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("room_id");
        String name = resultSet.getString("room_name");
        String theme = mapStringToTheme(resultSet.getString("room_theme"));
        Difficulty difficulty = mapStringToDifficulty(resultSet.getString("room_difficulty"));
        Room room = new Room(id,name,theme,difficulty,null,null);
        room.setId(id);
        return room;
    }

    @Override
    public Room create(Room entity) throws SQLException {
        String query = "INSERT INTO " + getTableName() + " (room_name, room_theme, room_difficulty) " +
                "VALUES (?, ?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1,entity.getName());
            preparedStatement.setString(2,entity.getTheme());
            preparedStatement.setString(3,entity.getDifficulty().name());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                }
            }
            return entity;
        }
    }

    @Override
    public Optional<Room> read(int id) throws SQLException {
        String query = "SELECT * FROM " + getTableName() + " WHERE room_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return Optional.of(mapResultSetToEntity(resultSet));
                } else{
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public Room update(Room entity) throws SQLException {
        String query = "UPDATE " + getTableName() + " SET room_name = ?, room_theme = ?, room_difficulty = ? WHERE room_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1,entity.getName());
            preparedStatement.setString(2,entity.getTheme());
            preparedStatement.setString(3,entity.getDifficulty().name());
            preparedStatement.setInt(4, entity.getId());
            preparedStatement.executeUpdate();
        }
        return entity;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String query = "DELETE FROM " + getTableName() + " WHERE room_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() == 1;
        }
    }
}
