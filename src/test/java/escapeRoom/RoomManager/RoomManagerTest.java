package escapeRoom.RoomManager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Controller.RoomManager.ClueManager;
import escapeRoom.Controller.RoomManager.PropManager;
import escapeRoom.Controller.RoomManager.RoomManager;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.InputService.InputServiceManager;
import escapeRoom.Service.PropAndClueService.ClueService;
import escapeRoom.Service.PropAndClueService.PropService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.Model.GameArea.CluePropFactory.*;
import escapeRoom.Model.GameArea.RoomBuilder.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;


class RoomManagerTest {

    public static RoomService roomService;
    public static RoomManager roomManager;
    private ClueManager clueManager;
    private PropManager propManager;

    String simulatedInput = """
        Alien House
        SCI-FI
        HARD
        2
        1
        2
        1
        1
        """;
    Scanner mockScanner = new Scanner(simulatedInput);
    InputService testInputService = InputServiceManager.getInputService();

    @BeforeEach
    void setup() throws SQLException {

       GameElementFactory clueFactory = new ClueFactory();
        GameElementFactory propFactory = new PropFactory();
        Connection connection = ConnectionManager.getConnection();
        InputService inputService = InputServiceManager.getInputService();
        RoomService roomService = new RoomService(connection);
        RoomManager roomManager = new RoomManager(inputService,roomService,new ClueManager(inputService,new ClueService(connection),roomService),new PropManager(inputService,new PropService(connection),roomService));


    }

    static {
        try {
            roomService = new RoomService(ConnectionManager.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createRoom() throws SQLException {

        roomManager.createRoom();

        try{
            List<Room> rooms = roomService.getAllEntities(roomService.getConnection());
            Optional<Room> createdRoom = rooms.stream()
                    .filter(r -> r.getName().equals("Alien House"))
                    .findFirst();

            assertTrue(createdRoom.isPresent(),"The room should exist");
            assertEquals("Sci-Fi",createdRoom.get().getTheme());
            assertEquals(3, createdRoom.get().getClues_id().size());
            assertEquals(4, createdRoom.get().getProps_id().size());

        } catch (SQLException e) {
            System.out.println("SQLException during test: " + e.getMessage());
        }

//        roomManager.deleteRoom(roomManager.getNextRoomId()-1);

    }

    @Test
    void readRoom() throws SQLException {
        Optional<Room> foundRoom = roomService.read(16);
        System.out.println(foundRoom);

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
    void updateRoom() {

    }

    @Test
    void deleteRoom() {
    }
}