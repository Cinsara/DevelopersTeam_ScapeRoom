package escapeRoom.Controller;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Controller.GameController.GameManager;
import escapeRoom.Controller.GameController.Exceptions.GameNotAvailableException;
import escapeRoom.SetUp.EscapeRoomServices;
import escapeRoom.Service.AbsentEntityException;
import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.ManyToManyService.GameHasUserService;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.Model.GameArea.GameBuilder.Game;
import escapeRoom.Model.PeopleArea.User;
import escapeRoom.Model.PeopleArea.UserBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GameManagerTest {
    static private GameManager gameManager;
    static private User existUser;
    static private User nonexistUser;
    static private Connection connection;

    @BeforeAll
    static void setUp() throws SQLException, AbsentEntityException {
        connection = ConnectionManager.getConnection();
        gameManager = new GameManager(new EscapeRoomServices(connection).getServicesForGameManager());
        existUser = new UserBuilder("Bob","Smith").setDob(LocalDate.of(1985, 9,23)).setId(2).build();
        nonexistUser = new UserBuilder("Barbar","Lolipop").setId(20).build();

    }

    @Test
    void createGame() {
        gameManager.createGame(LocalDate.now(),2);
        gameManager.createGame(LocalDate.now(),3);
        gameManager.createGame(LocalDate.now(),4);
        gameManager.createGame(LocalDate.now(),1);
        gameManager.createGame(LocalDate.now(),2);
        gameManager.createGame(LocalDate.now(),5);
    }

    @Test
    void bookGame() {
     gameManager.bookGame(LocalDate.now().plusDays(1),3,existUser);
     gameManager.bookGame(LocalDate.now(),1,nonexistUser);
     gameManager.bookGame(LocalDate.now().plusDays(1),3,existUser);
    }

    @Test
    void addPlayerToGame() throws SQLException, GameNotAvailableException {
        UserService userService = new UserService(connection);
        List<User> users = userService.getAllEntities(ConnectionManager.getConnection());
        for (User user : users){
            gameManager.addPlayerToGame(LocalDate.now(),2,user);
            if (user.getId()%2 ==0) gameManager.addPlayerToGame(LocalDate.now(),3,user);
        }
        GameHasUserService gameHasUserService = new GameHasUserService(connection);
        List<Integer> usersFirstGame = gameHasUserService.getMatches(gameManager.selectGame(LocalDate.now(),2).getId());
        List<Integer> usersSecondGame = gameHasUserService.getMatches(gameManager.selectGame(LocalDate.now(),3).getId());
        System.out.println(usersFirstGame.toString());
        System.out.println(usersSecondGame.toString());
    }
    @Test
    void loadGames(){
        gameManager.getGames().forEach(game -> {
            System.out.println("players: "+game.getPlayers().stream().map(User::getName).toList().toString() +", clues: "+ game.getUsedClues()+", rewards: "+game.getRewardsGiven());
            assertTrue(game.getCaptainId()== null || game.getPlayers().stream().map(User::getId).toList().contains(game.getCaptainId()),"Game " + game.getId() + "has captain");
        });
    }

    @Test
    void removePlayerFromGame() {
        gameManager.removePlayerFromGame(LocalDate.now(),2,existUser);
        gameManager.removePlayerFromGame(LocalDate.now(),3,existUser);
    }

    @Test
    void playGame() throws SQLException {
        LocalDate dateGame = LocalDate.of(2026,1,1);
        Game newGame = gameManager.createGame(dateGame,1);
        UserService userService = new UserService(connection);
        List<User> users = userService.getAllEntities(ConnectionManager.getConnection());
        users.forEach(newGame::addPlayer);
        Game playedGame = gameManager.playGame(dateGame,1);
        System.out.println(playedGame.toString());
        GameService gameService = new GameService(connection);
        gameService.delete(newGame.getId());
    }
}