package escapeRoom.Controller.CertificateManager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.SetUp.EscapeRoomServices;
import escapeRoom.Service.InputService.InputCollector;
import escapeRoom.Service.InputService.InputServiceManager;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.Service.RoomService.RoomService;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

class CertificateManagerTest {


    @Test
    void inputsCertificationCreation() throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        RoomService roomService = new RoomService(connection);
        UserService userService = new UserService(connection);
        String simulateInput = "2024 03 16\n1\n1";
        InputStream originalIn = System.in;
        try{
            ByteArrayInputStream testIn = new ByteArrayInputStream(simulateInput.getBytes());
            System.setIn(testIn);
            InputService inputService = InputServiceManager.getInputService();
            CertificateManager certificateManager = new CertificateManager(new EscapeRoomServices(connection).getServicesForCertificateManager());
            CertificateController certificateController = new CertificateController(inputService,new InputCollector(inputService,roomService,userService),certificateManager);
            certificateController.inputsCertificationCreation();
        }finally{
            System.setIn(originalIn);
        }
    }
}