package escapeRoom.Service.ManyToManyService;

import java.sql.SQLException;

public class GameHasUserService extends MTMService {
    protected GameHasUserService() throws SQLException {
    }

    @Override
    String getTableName() {
        return "game_has_customer";
    }

    @Override
    String getOriginColumn() {
        return "game_game_id";
    }

    @Override
    String getTargetColumn() {
        return "customer_customer_id";
    }
}
