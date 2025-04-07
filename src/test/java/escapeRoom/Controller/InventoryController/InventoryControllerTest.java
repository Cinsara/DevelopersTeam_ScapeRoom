package escapeRoom.Controller.InventoryController;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.PropAndClueService.ClueService;
import escapeRoom.Service.PropAndClueService.PropService;
import escapeRoom.Service.RoomService.RoomService;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class InventoryControllerTest {

    @Test
    void showInventory() throws SQLException {
        new InventoryController(new RoomService(ConnectionManager.getConnection()),new PropService(ConnectionManager.getConnection()),new ClueService(ConnectionManager.getConnection())).showInventory();
    }
}