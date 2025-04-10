package escapeRoom.Controller.InventoryController;


import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Controller.RoomManager.ClueManager;
import escapeRoom.Controller.RoomManager.PropManager;
import escapeRoom.Controller.RoomManager.RoomManager;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.InputService.InputServiceManager;
import escapeRoom.Service.PropAndClueService.ClueService;
import escapeRoom.Service.PropAndClueService.PropService;
import escapeRoom.Service.RoomService.RoomService;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

class InventoryControllerTest {

    @Test
    void showInventory() throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        InputService inputService = InputServiceManager.getInputService();
        RoomService roomService = new RoomService(connection);
        new InventoryController(new RoomManager(inputService,roomService,new ClueManager(inputService,new ClueService(connection),roomService),new PropManager(inputService,new PropService(connection),roomService))).showInventory();
    }
}