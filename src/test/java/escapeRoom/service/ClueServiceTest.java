package escapeRoom.service;

import escapeRoom.connectionManager.ConnectionManager;
import escapeRoom.gameArea.CluePropFactory.Clue;
import escapeRoom.gameArea.CluePropFactory.ClueType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static com.mysql.cj.conf.PropertyKey.PASSWORD;
import static org.junit.Assert.fail;

class ClueServiceTest {

    @Test
    void read() throws SQLException {

            int testId = 5; // ID existente en la base de datos

            try (Connection CONNECTION = ConnectionManager.getConnection()) {
                ClueService clueService = new ClueService(CONNECTION);
                Optional<Clue> result = clueService.read(testId);

                Assertions.assertTrue(result.isPresent(), "Clue should be present");
                Assertions.assertEquals(ClueType.ENIGMA, result.get().getType(), "Clue type should be ENIGMA");

            } catch (SQLException e) {
                fail("SQLException occurred: " + e.getMessage());
            }
        }
}