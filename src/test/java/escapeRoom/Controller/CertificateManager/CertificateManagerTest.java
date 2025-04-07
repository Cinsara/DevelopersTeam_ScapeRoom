package escapeRoom.Controller.CertificateManager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.AssetService.CertificateService;
import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.InputService.InputCollector;
import escapeRoom.Service.InputService.InputServiceManager;
import escapeRoom.Service.ManyToManyService.GameHasUserService;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.Service.RoomService.RoomService;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CertificateManagerTest {

    @Test
    void inputsCertificationCreation() throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        RoomService roomService = new RoomService(connection);
        GameService gameService= new GameService(connection);
        UserService userService = new UserService(connection);
        GameHasUserService gameHasUserService = new GameHasUserService(connection);
        CertificateValidation certificateValidation = new CertificateValidation(userService,gameService,roomService,
                gameHasUserService);
        String simulateInput = "2024 03 16\n1\n1";
        InputStream originalIn = System.in;
        try{
            ByteArrayInputStream testIn = new ByteArrayInputStream(simulateInput.getBytes());
            System.setIn(testIn);
            CertificateManager certificateManager = new CertificateManager(
                    InputServiceManager.getInputService(),
                    new CertificateService(connection),
                    userService,
                    new GameService(connection),
                    roomService,
                    new GameHasUserService(connection),
                    new InputCollector(InputServiceManager.getInputService(),roomService,userService),
                    new CertificateValidation(userService,gameService,roomService,gameHasUserService));
            certificateManager.inputsCertificationCreation();
        }finally{
            System.setIn(originalIn);
        }
    }
}