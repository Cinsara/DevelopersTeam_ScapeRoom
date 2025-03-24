package escapeRoom.service;

import escapeRoom.connectionManager.ConnectionManager;
import escapeRoom.gameArea.CluePropFactory.Clue;
import escapeRoom.gameArea.CluePropFactory.ClueType;

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
        return null;    }

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
        return null;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
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

