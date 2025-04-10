package escapeRoom.Service.ManyToManyService;

import java.sql.Connection;
import java.sql.SQLException;

public class GameUsesClueService extends MTMService {
    public GameUsesClueService(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    String getTableName() {
        return "game_uses_clue";
    }

    @Override
    String getOriginColumn() {
        return "game_game_id";
    }

    @Override
    String getTargetColumn() {
        return "clue_clue_id";
    }
}
