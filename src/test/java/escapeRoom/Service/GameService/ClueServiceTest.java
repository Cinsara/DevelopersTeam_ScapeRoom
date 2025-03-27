package escapeRoom.Service.GameService;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.model.GameArea.CluePropFactory.Clue;
import escapeRoom.model.GameArea.CluePropFactory.ClueType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

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

        int testId = 23; // ID existente en la base de datos

        try (CONNECTION) {
            Optional<Clue> result = clueService.read(testId);

            Clue clue = result.get();
            System.out.println("ID: " + result.get().getId() + ", TYPE: " + result.get().getType() + ", ROOM_ID: " + result.get().getRoomId());

            assertTrue(result.isPresent(), "Clue should be present");
            assertEquals(ClueType.ENIGMA, result.get().getType(), "Clue type should be INDICATION");
            assertEquals(1, result.get().getRoomId(), "room_room_id should be 1");

        } catch (SQLException e) {
            fail("SQLException occurred: " + e.getMessage());
        }
    }

    @Test
    void create() throws SQLException {

        Clue newClue = new Clue(ClueType.ENIGMA, 1);
        Clue createdClue = clueService.create(newClue);

        System.out.println("sent to create clue -> ID: " + createdClue.getId() + ", TYPE: " + createdClue.getType() +
                ", ROOM_ID: " + createdClue.getRoomId());


        assertNotNull(createdClue.getId(), "The generated ID shouldn't be null");
        assertTrue(createdClue.getId() > 0, "The generated ID should be greater than 0");
        System.out.println("Generated ID from test: " + createdClue.getId());

        String query = "SELECT * FROM " + clueService.getTableName() + " WHERE clue_id = ?";
            try (PreparedStatement stmt = CONNECTION.prepareStatement(query)) {
                stmt.setInt(1, createdClue.getId());
                try (ResultSet rs = stmt.executeQuery()) {
                    assertTrue(rs.next(), "Inserted clue not found in database");

                    System.out.println("read prop from DB -> ID: " + rs.getString("clue_id") + ", TYPE: " + rs.getString("clue_type") +
                            ", ROOM_ID: " + rs.getString("room_room_id"));

                    // Verificar los valores de los campos
                    assertEquals("Enigma", rs.getString("clue_type"), "Clue type mismatch");
                    assertEquals(1, rs.getInt("room_room_id"), "Room ID mismatch");
                }
            }


        // Eliminar el dato después de la prueba para no ensuciar la base de datos
        String deleteQuery = "DELETE FROM " + clueService.getTableName() + " WHERE clue_id = ?";
        try (PreparedStatement deleteStatement = CONNECTION.prepareStatement(deleteQuery)) {
            deleteStatement.setInt(1, createdClue.getId());
            int rowsDeleted = deleteStatement.executeUpdate();
            assertTrue(rowsDeleted == 1, "The inserted clue should be deleted");
        }
    }

    @Test
    void update() throws SQLException {

        Optional<Clue> result = clueService.read(8);

        System.out.println("Clue before update -> ID: " + result.get().getId() + ", TYPE: " + result.get().getType() +
                ", ROOM_ID: " + result.get().getRoomId());

        Clue newClue = new Clue(ClueType.ENIGMA,8,2);
        clueService.update(newClue);

        try (CONNECTION) {
            Optional<Clue> clueUpdated = clueService.read(8);

            System.out.println("Clue after update -> ID: " + clueUpdated.get().getId() + ", TYPE: " + clueUpdated.get().getType() +
                    ", ROOM_ID: " + clueUpdated.get().getRoomId());

            assertTrue(clueUpdated.isPresent(), "Clue should be present");
            assertEquals(ClueType.ENIGMA, clueUpdated.get().getType(), "Clue type should be INDICATION");
            assertEquals(2, clueUpdated.get().getRoomId(), "room id should be 4");

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
            assertTrue(rowsDeleted == 1, "The inserted clue should be deleted");
        }
    }

    @Test
    void getAll() throws SQLException {
        ResultSet clues = clueService.getAll(clueService.getCONNECTION());
        while(clues.next()){
            System.out.println("ID: " + clues.getInt("clue_id") + ", TYPE: " + clues.getString("clue_type") +
                    ", ROOM_ID: " + clues.getInt("room_room_id"));
        }
    }

    @Test
    void getAllEntities() throws SQLException {
        List<Clue> clues = clueService.getAllEntities(clueService.getCONNECTION());
        clues.forEach(prop -> System.out.println(prop.toString()));
    }
}