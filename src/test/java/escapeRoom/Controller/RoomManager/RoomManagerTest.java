package escapeRoom.Controller.RoomManager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.InputService.InputServiceManager;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.model.GameArea.RoomBuilder.Room;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class RoomManagerTest {

    @Test
    void getAllRooms() throws SQLException {
        RoomManager roomManager = new RoomManager(InputServiceManager.getInputService(),new RoomService(ConnectionManager.getConnection()));
        List<Room> rooms = roomManager.getAllRooms();
        rooms.forEach(room->{
            System.out.println(room.getClues_id());
        });
    }
}