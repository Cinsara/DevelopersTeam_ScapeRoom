package escapeRoom.Controller.RoomManager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.InputService.InputServiceManager;
import escapeRoom.Service.PropAndClueService.ClueService;
import escapeRoom.Service.PropAndClueService.PropService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.model.GameArea.RoomBuilder.Room;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class RoomManagerTest {

    @Test
    void getAllRooms() throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        InputService inputService = InputServiceManager.getInputService();
        RoomService roomService = new RoomService(connection);
        RoomManager roomManager = new RoomManager(inputService,roomService,new ClueManager(inputService,new ClueService(connection),roomService),new PropManager(inputService,new PropService(connection),roomService));
        List<Room> rooms = roomManager.getAllRooms();
        rooms.forEach(room->{
            System.out.println(room.getClues_id());
        });
    }
}