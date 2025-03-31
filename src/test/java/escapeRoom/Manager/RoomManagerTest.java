package escapeRoom.Manager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.model.GameArea.RoomBuilder.Room;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

class RoomManagerTest {

    static RoomService roomService;

    static {
        try {
            roomService = new RoomService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createRoom() {
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