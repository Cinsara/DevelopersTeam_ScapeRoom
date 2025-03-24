package escapeRoom.service.GameService;

import escapeRoom.connectionManager.ConnectionManager;
import escapeRoom.gameArea.GameBuilder.Game;
import escapeRoom.service.CrudeService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

public class GameService implements CrudeService<Game> {

    private final Connection connection = ConnectionManager.getConnection();

    public GameService() throws SQLException {
    }

    @Override
    public String getTableName() {
        return "game";
    }

    @Override
    public Game mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public Game create(Game entity) throws SQLException {
        String query = "INSERT INTO " + getTableName() + " (game_date, game_success, game_lengthInSec,room_room_id,captain_customer_id) VALUES (?,?,?,?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setObject(1,entity.getDate());
            preparedStatement.setBoolean(2,entity.isSuccess());
            preparedStatement.setInt(3,entity.getEllapsedTimeInSeconds());
            preparedStatement.setInt(4,entity.getRoom_id());
            if (entity.getCaptain_id() == null){
                preparedStatement.setNull(5,java.sql.Types.INTEGER);
            }else{
                preparedStatement.setInt(5,entity.getCaptain_id());
            }
            preparedStatement.executeUpdate();
            try {
                System.out.println(getGeneratedId(preparedStatement));
                entity.set_id(getGeneratedId(preparedStatement));
            }catch(SQLException e){}
            return entity;
        }
    }

    @Override
    public Optional<Game> read(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Game update(Game entity) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return false;
    }
}
