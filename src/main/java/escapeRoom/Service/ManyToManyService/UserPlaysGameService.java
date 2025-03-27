package escapeRoom.Service.ManyToManyService;

import java.sql.SQLException;

public class UserPlaysGameService extends MTMService {
    protected UserPlaysGameService() throws SQLException {
    }

    @Override
    String getTableName() {
        return "game_has_customer";
    }

    @Override
    String getOriginColumn() {
        return "customer_customer_id";
    }

    @Override
    String getTargetColumn() {
        return "game_game_id";
    }
}
