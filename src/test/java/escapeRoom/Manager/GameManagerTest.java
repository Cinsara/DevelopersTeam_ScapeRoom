package escapeRoom.Manager;

import escapeRoom.Service.AssetService.TicketService;
import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.model.GameArea.GameBuilder.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {
    static private GameManager gameManager;

    @BeforeAll
    static void setUp() throws SQLException {
        InputService is = new InputService(new Scanner(System.in));
        GameService gs = new GameService();
        TicketService ts = new TicketService();

        gameManager = new GameManager();
    }

    @Test
    void createGame() {

    }

    @Test
    void bookGame() {
    }
}