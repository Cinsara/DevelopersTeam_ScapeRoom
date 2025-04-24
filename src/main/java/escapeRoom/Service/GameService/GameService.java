package escapeRoom.Service.GameService;

import escapeRoom.Model.GameArea.GameBuilder.Game;
import escapeRoom.Model.GameArea.GameBuilder.GameBuilder;
import escapeRoom.Service.GetAllService;


import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class GameService implements GetAllService<Game> {

    private final Connection connection;

    public GameService(Connection connection) throws SQLException {
        this.connection = connection;
    }

    @Override
    public String getTableName() {
        return "game";
    }

    @Override
    public Game mapResultSetToEntity(ResultSet resultSet) throws SQLException {
            int id = resultSet.getInt("game_id");
            LocalDate date =  resultSet.getObject("game_date", LocalDate.class);
            int lengthInSec = resultSet.getInt("game_lengthInSec");
            int room_id = resultSet.getInt("room_room_id");
            Integer captainId = null;
            int raw = resultSet.getInt("captain_customer_id");
            if (!resultSet.wasNull()) {
                captainId = raw;
            }
            boolean success = resultSet.getBoolean("game_success");
            return new GameBuilder(room_id,date)
                    .setCaptainId(captainId)
                    .setEllapsedTimeInSeconds(lengthInSec)
                    .setSuccess(success)
                    .setId(id)
                    .build();
    }

    @Override
    public Game create(Game entity) throws SQLException {
        String query = "INSERT INTO " + getTableName() + " (game_date, game_success, game_lengthInSec,room_room_id,captain_customer_id) VALUES (?,?,?,?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setObject(1,entity.getDate());
            preparedStatement.setBoolean(2,entity.isSuccess());
            preparedStatement.setInt(3,entity.getEllapsedTimeInSeconds());
            preparedStatement.setInt(4,entity.getRoom_id());
            if (entity.getCaptainId() == null){
                preparedStatement.setNull(5,java.sql.Types.INTEGER);
            }else{
                preparedStatement.setInt(5,entity.getCaptainId());
            }
            preparedStatement.executeUpdate();
            try {
                entity.setId(getGeneratedId(preparedStatement));
            }catch(SQLException e){}
            return entity;
        }
    }

    @Override
    public Optional<Game> read(int id) throws SQLException {
        String query = "SELECT * FROM " + getTableName() + " WHERE game_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            try(ResultSet rs = preparedStatement.executeQuery()){
                if (rs.next()){
                    return Optional.of(mapResultSetToEntity(rs));
                }else{
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public Game update(Game entity) throws SQLException {
        String query = "UPDATE " + getTableName() + " SET game_date = ?, game_success = ?,game_lengthInSec = ?,room_room_id = ?,captain_customer_id = ? WHERE game_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, entity.getDate());
            preparedStatement.setBoolean(2, entity.isSuccess());
            preparedStatement.setInt(3, entity.getEllapsedTimeInSeconds());
            preparedStatement.setInt(4, entity.getRoom_id());
            if (entity.getCaptainId() == null) {
                preparedStatement.setNull(5, java.sql.Types.INTEGER);
            } else {
                preparedStatement.setInt(5, entity.getCaptainId());
            }
            preparedStatement.setInt(6,entity.getId());
            preparedStatement.executeUpdate();
        }
        return entity;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String query = "DELETE FROM " + getTableName() + " WHERE game_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,id);
            return preparedStatement.executeUpdate() == 1;
        }
    }
    public Connection getConnection() {
        return connection;
    }
}
