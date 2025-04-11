package escapeRoom.RoomManager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Controller.RoomManager.ClueManager;
import escapeRoom.Controller.RoomManager.PropManager;
import escapeRoom.Controller.RoomManager.RoomManager;
import escapeRoom.Service.InputService.BackToSecondaryMenuException;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.InputService.InputServiceManager;
import escapeRoom.Service.PropAndClueService.ClueService;
import escapeRoom.Service.PropAndClueService.PropService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.Model.GameArea.CluePropFactory.*;
import escapeRoom.Model.GameArea.RoomBuilder.Room;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RoomManagerTest {
    static RoomService roomService;
//    static RoomManager roomManager;
    static Connection connection;
//    static InputService inputService;

    @BeforeAll
    static void setUp() throws SQLException {

        connection = ConnectionManager.getConnection();
//        inputService = InputServiceManager.getInputService();
        roomService = new RoomService(connection);
//        roomManager = new RoomManager(inputService,roomService,new ClueManager(inputService,new ClueService(connection),roomService),new PropManager(inputService,new PropService(connection),roomService));

    }

    @Test
    void createRoom() throws SQLException, BackToSecondaryMenuException {

        String simulateInput = "TestRoom\nscifi\nhard\n2\n2\n3\n2\n1\n";
        System.setIn(new ByteArrayInputStream(simulateInput.getBytes()));
        InputStream originalIn = System.in;

        InputService inputService = InputServiceManager.getInputService();

        RoomManager testRoomManager = new RoomManager(inputService, roomService,
                new ClueManager(inputService, new ClueService(connection), roomService),
                new PropManager(inputService, new PropService(connection), roomService));

        testRoomManager.createRoom();

        try{
            List<Room> rooms = roomService.getAllEntities(connection);
            Optional<Room> createdRoom = rooms.stream()
                    .filter(r -> r.getName().equals("TestRoom"))
                    .findFirst();

            assertTrue(createdRoom.isPresent(),"The room should exist");
            assertEquals("Sci-Fi",createdRoom.get().getTheme());
            assertEquals("HARD",createdRoom.get().getDifficulty().toString());
            assertEquals(4, roomService.read(createdRoom.get().getId()).get().getClues_id().size());
            assertEquals(6, roomService.read(createdRoom.get().getId()).get().getProps_id().size());

        } catch (SQLException e) {
            System.out.println("SQLException during test: " + e.getMessage());
        } finally {
            System.setIn(originalIn);
        }

        roomService.delete(testRoomManager.getNextRoomId()-1);

    }

    @Test
    void readRoom() throws SQLException {
        Optional<Room> foundRoom = roomService.read(1);
        try{
            assertTrue(foundRoom.isPresent(),"The room should exist");
            assertEquals("In mood for Love",foundRoom.get().getName());
            assertEquals("Fantastic",foundRoom.get().getTheme());
            assertEquals("MEDIUM",foundRoom.get().getDifficulty().toString());
            assertEquals(3, roomService.read(foundRoom.get().getId()).get().getClues_id().size());
            assertEquals(2, roomService.read(foundRoom.get().getId()).get().getProps_id().size());

        } catch (SQLException e) {
            System.out.println("SQLException during test: " + e.getMessage());
        }

    }

    @Test
    void getAllRooms() throws SQLException {
        List<Room> rooms = roomService.getAllEntities(ConnectionManager.getConnection());
        rooms.forEach(room -> {
            try {
                System.out.println(roomService.read(room.getId()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

    @Test
    void updateRoom() throws SQLException, BackToSecondaryMenuException {

        String simulateInput = "TestRoom\nscifi\nhard\n2\n2\n3\n2\n1";
        System.setIn(new ByteArrayInputStream(simulateInput.getBytes()));
        InputStream originalIn = System.in;

        InputService inputService = InputServiceManager.getInputService();

        RoomManager testRoomManager = new RoomManager(inputService, roomService,
                new ClueManager(inputService, new ClueService(connection), roomService),
                new PropManager(inputService, new PropService(connection), roomService));

        testRoomManager.createRoom();

        System.setIn(System.in);
        System.setOut(System.out);

        simulateInput = testRoomManager.getNextRoomId()-1 + "\nTestRoomUpdated\nmystery\nmedium\nyes\nenigma\nno\nyes\nmountain\nno\nno";
        System.setIn(new ByteArrayInputStream(simulateInput.getBytes()));
//        originalIn = System.in;
//
//        inputService = InputServiceManager.getInputService();

        testRoomManager.updateRoom();

        try{
            List<Room> rooms = roomService.getAllEntities(connection);
            Optional<Room> createdRoom = rooms.stream()
                    .filter(r -> r.getName().equals("TestRoomUpdate"))
                    .findFirst();

            assertTrue(createdRoom.isPresent(),"The room should exist");
            assertEquals("Mystery",createdRoom.get().getTheme());
            assertEquals("MEDIUM",createdRoom.get().getDifficulty().toString());
            assertEquals(5, roomService.read(createdRoom.get().getId()).get().getClues_id().size());
            assertEquals(7, roomService.read(createdRoom.get().getId()).get().getProps_id().size());

        } catch (SQLException e) {
            System.out.println("SQLException during test: " + e.getMessage());
        } finally {
            System.setIn(originalIn);
        }

        roomService.delete(testRoomManager.getNextRoomId()-1);

    }

    @Test
    void deleteRoom() {
    }
}