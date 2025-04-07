package escapeRoom.Controller.RoomManager;

import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.InputService.InputServiceManager;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class RoomMenuTest {

    @Test
    void startRoomMenu() {
        String simulateInput = "2\n";
        InputStream originalIn = System.in;
        try{
            ByteArrayInputStream testIn = new ByteArrayInputStream(simulateInput.getBytes());
            System.setIn(testIn);
            RoomMenu roomMenu = new RoomMenu(InputServiceManager.getInputService());
            roomMenu.startRoomMenu();
        }finally{
            System.setIn(originalIn);
        }
    }
}