package escapeRoom.Manager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.PropAndClueService.ClueService;
import escapeRoom.Service.PropAndClueService.PropService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.model.GameArea.CluePropFactory.*;
import escapeRoom.model.GameArea.RoomBuilder.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

class RoomManagerTest {

    public static RoomService roomService;
    public static RoomManager roomManager;

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
    InputService testInputService = new InputService(mockScanner);

    @BeforeEach
    void setup() throws SQLException {

        ClueService clueService = new ClueService(ConnectionManager.getConnection());
        PropService propService = new PropService(ConnectionManager.getConnection());
        GameElementFactory clueFactory = new ClueFactory();
        GameElementFactory propFactory = new PropFactory();

        ClueManager clueManager = new ClueManager(clueService, testInputService,clueFactory);
        PropManager propManager = new PropManager(propService, testInputService, propFactory);
        roomManager = new RoomManager(roomService, clueManager, propManager, testInputService,
                clueFactory, propFactory);



    }

    static {
        try {
            roomService = new RoomService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createRoom() throws SQLException {

        roomManager.createRoom();


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