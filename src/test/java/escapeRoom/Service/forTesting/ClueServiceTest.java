package escapeRoom.Service.forTesting;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.GameArea.CluePropFactory.Clue;
import escapeRoom.GameArea.CluePropFactory.ClueType;
import escapeRoom.Service.ClueService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Optional;

import static com.mysql.cj.conf.PropertyKey.PASSWORD;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClueServiceTest {

    static ClueService clueService;
    private final Connection CONNECTION = ConnectionManager.getConnection();

    ClueServiceTest() throws SQLException {
    }

    @BeforeAll
    static void setUp() throws SQLException {
        clueService = new ClueService(ConnectionManager.getConnection());

    }

    @Test
    void read() throws SQLException {

        int testId = 5; // ID existente en la base de datos

        try (CONNECTION) {
            Optional<Clue> result = clueService.read(testId);

            Assertions.assertTrue(result.isPresent(), "Clue should be present");
            Assertions.assertEquals(ClueType.ENIGMA, result.get().getType(), "Clue type should be ENIGMA");

        } catch (SQLException e) {
            fail("SQLException occurred: " + e.getMessage());
        }
    }

    @Test
    void create() throws SQLException {

        Clue newClue = new Clue(ClueType.ENIGMA, 1);
        Clue createdClue = clueService.create(newClue);

        Assertions.assertNotNull(createdClue.getId(), "The generated ID shouldn't be null");
        Assertions.assertTrue(createdClue.getId() > 0, "The generated ID should be greater than 0");
        System.out.println("Generated ID from test: " + createdClue.getId());

        String query = "SELECT * FROM " + clueService.getTableName() + " WHERE clue_id = ?";
            try (PreparedStatement stmt = CONNECTION.prepareStatement(query)) {
                stmt.setInt(1, createdClue.getId());
                try (ResultSet rs = stmt.executeQuery()) {
                    Assertions.assertTrue(rs.next(), "Inserted clue not found in database");

                    // Verificar los valores de los campos
                    Assertions.assertEquals("Enigma", rs.getString("clue_type"), "Clue type mismatch");
                    Assertions.assertEquals(1, rs.getInt("room_room_id"), "Room ID mismatch");
                }
            }


        // Eliminar el dato después de la prueba para no ensuciar la base de datos
        String deleteQuery = "DELETE FROM " + clueService.getTableName() + " WHERE clue_id = ?";
        try (PreparedStatement deleteStatement = CONNECTION.prepareStatement(deleteQuery)) {
            deleteStatement.setInt(1, createdClue.getId());
            int rowsDeleted = deleteStatement.executeUpdate();
            Assertions.assertTrue(rowsDeleted > 0, "The inserted clue should be deleted");
        }
    }

    @Test
    void update() throws SQLException {

        Clue newClue = new Clue(ClueType.INDICATION,1,5);
        clueService.update(newClue);

        try (CONNECTION) {
            Optional<Clue> clueUpdated = clueService.read(5);

            Assertions.assertTrue(clueUpdated.isPresent(), "Clue should be present");
            Assertions.assertEquals(ClueType.INDICATION, clueUpdated.get().getType(), "Clue type should be ENIGMA");

        } catch (SQLException e) {
            fail("SQLException occurred: " + e.getMessage());
        }
    }

    @Test
    void delete() throws SQLException {

        Clue newClue = new Clue(ClueType.ENIGMA, 1);
        Clue createdClue = clueService.create(newClue);

        String deleteQuery = "DELETE FROM " + clueService.getTableName() + " WHERE clue_id = ?";
        try (PreparedStatement deleteStatement = CONNECTION.prepareStatement(deleteQuery)) {
            deleteStatement.setInt(1, createdClue.getId());
            int rowsDeleted = deleteStatement.executeUpdate();
            Assertions.assertTrue(rowsDeleted == 1, "The inserted clue should be deleted");
        }
    }

}