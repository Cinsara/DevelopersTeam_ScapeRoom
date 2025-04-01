package escapeRoom.RoomService;

import escapeRoom.model.GameArea.RoomBuilder.Difficulty;
import escapeRoom.model.GameArea.RoomBuilder.Room;
import escapeRoom.model.GameArea.RoomBuilder.Theme;
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
                "Immediate Room",
                Theme.FANTASTIC.getDisplayName(),
                Difficulty.EASY,
                List.of(1,2,3),
                List.of(1,2,3)
        );
        Room createdRoom = roomService.create(testRoom);
        Assertions.assertEquals("Immediate Room", testRoom.getName());
    }

    @Test
    void read() throws SQLException {
        //Room createdRoom = roomService.create(testRoom);
        Optional<Room> foundRoom = roomService.read(5);
        Assertions.assertTrue(foundRoom.isPresent());
        Assertions.assertEquals("First Room",foundRoom.get().getName());
    }

    @Test
    void update() throws SQLException {

        Room updateRoom = new Room(5, "Second Room",Theme.MYSTERY.getDisplayName(),Difficulty.HARD,
                null, null);
        roomService.update(updateRoom);
        Optional<Room> optional = roomService.read(5);
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
                Theme.FANTASTIC.getDisplayName(),
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
