package escapeRoom.Service.AssetService;

import escapeRoom.Model.AssetsArea.RewardBuilder.Reward;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RewardService extends AssetService<Reward> {
    public RewardService(Connection connection) throws SQLException {
        super(connection);
    }
    @Override
    public Reward mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(getTableName()+"_id");
        int userId = resultSet.getInt("customer_customer_id");
        int gameId = resultSet.getInt("game_game_id");
        Reward newReward = new Reward(userId,gameId);
        newReward.setId(id);;
        return newReward;
    }

    @Override
    public String getTableName() {
        return "reward";
    }

}
