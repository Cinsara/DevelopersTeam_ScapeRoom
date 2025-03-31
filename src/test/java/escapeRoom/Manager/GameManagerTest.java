package escapeRoom.Manager;

import escapeRoom.Service.AssetService.TicketService;
import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.ManyToManyService.GameHasUserService;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.model.GameArea.GameBuilder.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {
    static private GameManager gameManager;

    @BeforeAll
    static void setUp() throws SQLException {
        InputService is = new InputService(new Scanner(System.in));
        GameService gs = new GameService();
        TicketService ts = new TicketService();
        UserService us = new UserService();
        GameHasUserService ghus = new GameHasUserService();
        gameManager = new GameManager(is,gs,ts,us,ghus);

    }

    @Test
    void createGame() {
        gameManager.createGame(LocalDate.now(),2);
    }

    @Test
    void bookGame() {
     gameManager.bookGame(LocalDate.now(),3,1);
     gameManager.bookGame(LocalDate.now(),2,10);
     gameManager.bookGame(LocalDate.now(),2,1);
    }
}