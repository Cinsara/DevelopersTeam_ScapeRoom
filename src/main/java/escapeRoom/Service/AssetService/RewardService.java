package escapeRoom.Service.AssetService;

import escapeRoom.AssetsArea.AssetBuilder.Asset;
import escapeRoom.AssetsArea.RewardBuilder.Reward;
import escapeRoom.AssetsArea.TicketBuilder.Ticket;
import escapeRoom.Notification.Notification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class RewardService extends AssetService<Reward> {
    protected RewardService() throws SQLException {
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
