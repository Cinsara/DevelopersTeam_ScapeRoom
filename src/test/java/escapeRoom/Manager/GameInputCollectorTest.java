package escapeRoom.Manager;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class GameInputCollectorTest {

    @Test
    void getGameCoordinates() throws SQLException {
        GameInputCollector.getGameCoordinates();
    }
}