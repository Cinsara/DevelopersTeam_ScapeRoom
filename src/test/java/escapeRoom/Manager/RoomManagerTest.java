package escapeRoom.Manager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.PropAndClueService.ClueService;
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
        Lovely House
        MYSTERY
        MEDIUM
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
        GameElementFactory clueFactory = new ClueFactory();
        GameElementFactory propFactory = new PropFactory();

        roomManager = new RoomManager(roomService, clueManager, propManager, testInputService,
                clueFactory, propFactory);

        ClueManager clueManager = new ClueManager(clueService, testInputService,clueFactory,roomManager);
        PropManager propManager = new PropManager();




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
        Optional<Room> foundRoom = roomService.read(3);
        System.out.println(foundRoom);

    }

    @Test
    void getAllRooms() throws SQLException {
        List<Room> rooms = roomService.getAllEntities(ConnectionManager.getConnection());
        rooms.forEach(room -> System.out.println(room.toString()));

    }

    @Test
    void updateRoom() {

    }

    @Test
    void deleteRoom() {
    }
}