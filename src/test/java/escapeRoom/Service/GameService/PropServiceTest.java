package escapeRoom.Service.GameService;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.GameArea.CluePropFactory.Clue;
import escapeRoom.GameArea.CluePropFactory.ClueType;
import escapeRoom.GameArea.CluePropFactory.Prop;
import escapeRoom.GameArea.CluePropFactory.PropType;
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

class PropServiceTest {

    static PropService propService;
    private final Connection CONNECTION = ConnectionManager.getConnection();

    PropServiceTest() throws SQLException {
    }

    @BeforeAll
    static void setUp() throws SQLException {
        propService = new PropService(ConnectionManager.getConnection());

    }

    @Test
    void getCONNECTION() {
    }

    @Test
    void getTableName() {
    }

    @Test
    void mapResultSetToEntity() {



    }

    @Test
    void create() throws SQLException {

        Prop newProp = new Prop(PropType.SPADE, 3);
        Prop createdProp = propService.create(newProp);

        System.out.println("sent to create prop -> ID: " + createdProp.getId() + ", TYPE: " + createdProp.getType() +
                ", ROOM_ID: " + createdProp.getRoomId() + ", VALUE: " + createdProp.getENUMValue());

        assertNotNull(createdProp.getId(), "The generated ID shouldn't be null");
        assertTrue(createdProp.getId() > 0, "The generated ID should be greater than 0");
        System.out.println("Generated ID from test: " + createdProp.getId());

        String query = "SELECT * FROM " + propService.getTableName() + " WHERE prop_id = ?";
        try (PreparedStatement stmt = CONNECTION.prepareStatement(query)) {
            stmt.setInt(1, createdProp.getId());
            try (ResultSet rs = stmt.executeQuery()) {

                assertTrue(rs.next(), "Inserted Prop not found in database");

                System.out.println("read prop from DB -> ID: " + rs.getString("prop_id") + ", TYPE: " + rs.getString("prop_type") +
                        ", ROOM_ID: " + rs.getString("room_room_id") + ", VALUE: " + rs.getString("prop_value"));

                // Verificar los valores de los campos
                assertEquals("Spade", rs.getString("prop_type"), "Prop type mismatch");
                assertEquals(3, rs.getInt("room_room_id"), "Room ID mismatch");
                assertEquals(17, rs.getInt("prop_id"), "Prop ID mismatch");
                assertEquals(20, rs.getInt("prop_value"), "Prop Value mismatch");
            }
        }

        // Eliminar el dato después de la prueba para no ensuciar la base de datos
        String deleteQuery = "DELETE FROM " + propService.getTableName() + " WHERE prop_id = ?";
        try (PreparedStatement deleteStatement = CONNECTION.prepareStatement(deleteQuery)) {
            deleteStatement.setInt(1, createdProp.getId());
            int rowsDeleted = deleteStatement.executeUpdate();
            assertTrue(rowsDeleted > 0, "The inserted prop should be deleted");
        }
    }

    @Test
    void read() throws SQLException {

        int testId = 3; // ID existente en la base de datos

        try (CONNECTION) {
            Optional<Prop> result = propService.read(testId);

            assertTrue(result.isPresent(), "Prop should be present");

            Prop prop = result.get();
            System.out.println("ID: " + prop.getId() + ", TYPE: " + prop.getType() +
                    ", ROOM_ID: " + prop.getRoomId() + ", VALUE: " + prop.getDBValue());

            assertEquals(PropType.MOUNTAIN, prop.getType(), "Prop type should be SPADE");
            assertEquals(70, prop.getDBValue(), "Value should be 21");
            assertEquals(4, prop.getRoomId(), "room_room_id should be 3");
            assertEquals(3, prop.getId(), "Prop ID should be 3");
        } catch (SQLException e) {
            fail("SQLException occurred: " + e.getMessage());
        }
    }

    @Test
    void update() throws SQLException {

        try (CONNECTION) {
            Optional<Prop> result = propService.read(3);

            System.out.println("Prop before update -> ID: " + result.get().getId() + ", TYPE: " + result.get().getType() +
                    ", ROOM_ID: " + result.get().getRoomId() + ", VALUE: " + result.get().getDBValue());

            Prop newProp = new Prop(PropType.MOUNTAIN, 3, 4,PropType.MOUNTAIN.getValue());
            propService.update(newProp);

            try (CONNECTION) {
                Optional<Prop> propUpdated = propService.read(3);

                System.out.println("Prop after update -> ID: " + propUpdated.get().getId() + ", TYPE: " + propUpdated.get().getType() +
                        ", ROOM_ID: " + propUpdated.get().getRoomId() + ", VALUE: " + propUpdated.get().getDBValue());

                assertTrue(propUpdated.isPresent(), "Prop should be present");
                assertEquals(4, propUpdated.get().getRoomId(), "Prop room_room_id should be 4");

            } catch (SQLException e) {
                fail("SQLException occurred: " + e.getMessage());
            }
        }


    }

    @Test
    void delete() throws SQLException {

        Prop newProp = new Prop(PropType.MOUNTAIN, 1);
        Prop createdProp = propService.create(newProp);

        String deleteQuery = "DELETE FROM " + propService.getTableName() + " WHERE prop_id = ?";
        try (PreparedStatement deleteStatement = CONNECTION.prepareStatement(deleteQuery)) {
            deleteStatement.setInt(1, createdProp.getId());
            int rowsDeleted = deleteStatement.executeUpdate();
            assertTrue(rowsDeleted == 1, "The inserted prop should be deleted");
        }
    }


    @Test
    void getAll() throws SQLException {
        ResultSet props = propService.getAll(propService.getCONNECTION());
        while(props.next()){
            System.out.println("ID: " + props.getInt("prop_id") + ", TYPE: " + props.getString("prop_type") +
                    ", VALUE: " + props.getInt("prop_value") + ", ROOM_ID: " + props.getInt("room_room_id"));
        }
    }

    @Test
    void getAllEntities() throws SQLException {
        List<Prop> props = propService.getAllEntities(propService.getCONNECTION());
        props.forEach(prop -> System.out.println(prop.toString()));
    }
}