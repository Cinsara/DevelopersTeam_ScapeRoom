package escapeRoom.RoomService;

import escapeRoom.GameArea.RoomBuilder.Difficulty;
import escapeRoom.GameArea.RoomBuilder.Room;
import escapeRoom.GameArea.RoomBuilder.Theme;
import escapeRoom.Service.RoomService.RoomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RoomServiceTest {
    static RoomService roomService;
    static Room testRoom;

    @BeforeAll
    static void setUp() throws SQLException {
        roomService = new RoomService();
    }

    @Test
    void create() throws SQLException {
        Room testRoom = new Room(1,
                "First Room",
                Theme.FANTASTIC,
                Difficulty.EASY,
                List.of(1,2,3),
                List.of(1,2,3)
        );
        Room createdRoom = roomService.create(testRoom);
        Assertions.assertEquals("First Room", testRoom.getName());
    }

    @Test
    void read() throws SQLException {
        Room testRoom = new Room(1,
                "First Room",
                Theme.FANTASTIC,
                Difficulty.EASY,
                List.of(1,2,3),
                List.of(1,2,3)
        );
        Room createdRoom = roomService.create(testRoom);
        Optional<Room> foundRoom = roomService.read(createdRoom.getId());
        Assertions.assertTrue(foundRoom.isPresent());
        Assertions.assertEquals("First Room",foundRoom.get().getName());
    }

    @Test
    void update() throws SQLException {
        Room newRoom = new Room(1,
                "First Room",
                Theme.FANTASTIC,
                Difficulty.EASY,
                List.of(1,2,3),
                List.of(1,2,3)
        );
        Room updateRoom = new Room(newRoom.getId(), "Second Room",Theme.MYSTERY,Difficulty.HARD,
                List.of(4,5,6), List.of(7,4,8));
        roomService.update(updateRoom);
        Optional<Room> optional = roomService.read(newRoom.getId());
        Assertions.assertTrue(optional.isPresent());

        Room roomFromDb = optional.get();
        Assertions.assertEquals(updateRoom.getId(), roomFromDb.getId());
        Assertions.assertEquals(updateRoom.getName(), roomFromDb.getName());
        Assertions.assertEquals(updateRoom.getTheme(), roomFromDb.getTheme());
        Assertions.assertEquals(updateRoom.getDifficulty(), roomFromDb.getDifficulty());
    }

    @Test
    void delete() throws SQLException {
        Room newRoom = new Room(1,
                "First Room",
                Theme.FANTASTIC,
                Difficulty.EASY,
                List.of(1,2,3),
                List.of(1,2,3)
        );
        Room createdRoom = roomService.create(newRoom);
        int roomId = createdRoom.getId();

        Assertions.assertTrue(roomService.delete(roomId));

        Optional<Room> deletedRoom = roomService.read(roomId);
        Assertions.assertFalse(deletedRoom.isPresent());

        try(ResultSet rs = roomService.getAll(roomService.getConnection())){
            boolean roomExists = false;
            while(rs.next()){
                if(rs.getInt("room_id") == roomId) {
                    roomExists = true;
                    break;
                }
            }
            Assertions.assertFalse(roomExists,"The room should not exist after the delete");
        }
    }
}
