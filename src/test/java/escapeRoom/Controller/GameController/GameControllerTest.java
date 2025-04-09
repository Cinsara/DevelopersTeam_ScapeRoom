package escapeRoom.Controller.GameController;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Controller.GameController.GameManager.GameManager;
import escapeRoom.Service.InputService.BackToSecondaryMenuException;
import escapeRoom.SetUp.EscapeRoomServices;
import escapeRoom.Service.InputService.InputCollector;
import escapeRoom.Service.InputService.InputServiceManager;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.Service.RoomService.RoomService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

class GameControllerTest {
    static RoomService roomService;
    static GameManager gameManager;
    static Connection connection;

    @BeforeAll
    static void setUp() throws SQLException {
         connection = ConnectionManager.getConnection();
         roomService = new RoomService(connection);
         gameManager = new GameManager(new EscapeRoomServices(connection).getServicesForGameManager());
    }

    @Test
    void bookGame() throws SQLException, BackToSecondaryMenuException {
        String simulateInput = "2025 04 11\n3\n3";
        InputStream originalIn = System.in;
        try{
            ByteArrayInputStream testIn = new ByteArrayInputStream(simulateInput.getBytes());
            System.setIn(testIn);
            InputCollector inputCollector = new InputCollector(InputServiceManager.getInputService(),roomService,new UserService(connection));
            GameController gameController = new GameController(gameManager,inputCollector);
            gameController.bookGame();

        }finally{
            System.setIn(originalIn);
        }
    }
    @Test
    void cancelBooking() throws BackToSecondaryMenuException{
        String simulateInput = "2025 04 11\n3";
        InputStream originalIn = System.in;
        try{
            ByteArrayInputStream testIn = new ByteArrayInputStream(simulateInput.getBytes());
            System.setIn(testIn);
            InputCollector inputCollector = new InputCollector(InputServiceManager.getInputService(),roomService,new UserService(connection));
            GameController gameController = new GameController(gameManager,inputCollector);
            gameController.cancelBooking();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            System.setIn(originalIn);
        }
    }
    @Test
    void playGame() throws BackToSecondaryMenuException{
        String simulateInput = "2025 04 10\n3\n3";
        InputStream originalIn = System.in;
        try{
            ByteArrayInputStream testIn = new ByteArrayInputStream(simulateInput.getBytes());
            System.setIn(testIn);
            InputCollector inputCollector = new InputCollector(InputServiceManager.getInputService(),roomService,new UserService(connection));
            GameController gameController = new GameController(gameManager,inputCollector);
            gameController.playGame();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            System.setIn(originalIn);
        }
    }

    @Test
    void showBookedGames() throws BackToSecondaryMenuException {
        String simulateInput = "3\n2";
        InputStream originalIn = System.in;
        try{
            ByteArrayInputStream testIn = new ByteArrayInputStream(simulateInput.getBytes());
            System.setIn(testIn);
            InputCollector inputCollector = new InputCollector(InputServiceManager.getInputService(),roomService,new UserService(connection));
            GameController gameController = new GameController(gameManager,inputCollector);
            gameController.showBookedGames();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            System.setIn(originalIn);
        }
    }

    @Test
    void showAvailabeGames() throws BackToSecondaryMenuException {
        String simulateInput = "2\n2025 04 06";
        InputStream originalIn = System.in;
        try{
            ByteArrayInputStream testIn = new ByteArrayInputStream(simulateInput.getBytes());
            System.setIn(testIn);
            InputCollector inputCollector = new InputCollector(InputServiceManager.getInputService(),roomService,new UserService(connection));
            GameController gameController = new GameController(gameManager,inputCollector);
            gameController.showAvailableGames();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            System.setIn(originalIn);
        }
    }

    @Test
    void testAddPlayer() throws BackToSecondaryMenuException{
        String simulateInput = "2025 04 06\n3\n1";
        InputStream originalIn = System.in;
        try{
            ByteArrayInputStream testIn = new ByteArrayInputStream(simulateInput.getBytes());
            System.setIn(testIn);
            InputCollector inputCollector = new InputCollector(InputServiceManager.getInputService(),roomService,new UserService(connection));
            GameController gameController = new GameController(gameManager,inputCollector);
            gameController.addPlayerToGame();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            System.setIn(originalIn);
        }
    }

    @Test
    void testRemovePlayer() throws BackToSecondaryMenuException{

        InputStream originalIn = System.in;
        try{
            String simulateInput1 = "2025 04 06\n3\n1\n1";
            System.setIn(new ByteArrayInputStream(simulateInput1.getBytes()));
            InputCollector inputCollector = new InputCollector(InputServiceManager.getInputService(),roomService,new UserService(connection));
            GameController gameController = new GameController(gameManager,inputCollector);
            gameController.removePlayerFromGame();
            String simulInput2 = "1";
            System.setIn(new ByteArrayInputStream(simulInput2.getBytes()));
            gameController.showBookedGames();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally{
            System.setIn(originalIn);
        }
    }
}