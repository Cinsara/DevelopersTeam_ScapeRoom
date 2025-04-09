package escapeRoom.Controller.RoomManager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Menus.RoomMenu;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.InputService.InputServiceManager;
import escapeRoom.Service.PropAndClueService.ClueService;
import escapeRoom.Service.PropAndClueService.PropService;
import escapeRoom.Service.RoomService.RoomService;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

class RoomMenuTest {

    @Test
    void startRoomMenu() throws SQLException {
        String simulateInput = "2\n0";
        InputStream originalIn = System.in;
        try{
            ByteArrayInputStream testIn = new ByteArrayInputStream(simulateInput.getBytes());
            System.setIn(testIn);
            InputService inputService = InputServiceManager.getInputService();
            Connection connection = ConnectionManager.getConnection();
            RoomService roomService = new RoomService(connection);
            ClueManager clueManager = new ClueManager(inputService,new ClueService(connection),roomService);
            PropManager propManager = new PropManager(inputService,new PropService(connection),roomService);
            RoomMenu roomMenu = new RoomMenu(InputServiceManager.getInputService(),new RoomManager(inputService,roomService,clueManager,propManager));
            roomMenu.startRoomMenu();
        }finally{
            System.setIn(originalIn);
        }
    }
}