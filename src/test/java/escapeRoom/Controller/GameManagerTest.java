package escapeRoom.Controller;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Controller.GameController.GameManager;
import escapeRoom.Controller.GameController.Exceptions.GameNotAvailableException;
import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.ManyToManyService.GameHasUserService;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.model.GameArea.GameBuilder.Game;
import escapeRoom.model.PeopleArea.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

class GameManagerTest {
    static private GameManager gameManager;

    @BeforeAll
    static void setUp() throws SQLException {
        gameManager = new GameManager();

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
     gameManager.bookGame(LocalDate.now().plusDays(1),3,1);
     gameManager.bookGame(LocalDate.now(),1,10);
     gameManager.bookGame(LocalDate.now(),1,1);
    }

    @Test
    void addPlayerToGame() throws SQLException, GameNotAvailableException {
        UserService userService = new UserService();
        List<User> users = userService.getAllEntities(ConnectionManager.getConnection());
        for (User user : users){
            gameManager.addPlayerToGame(LocalDate.now(),2,user.getId());
            if (user.getId()%2 ==0) gameManager.addPlayerToGame(LocalDate.now(),3,user.getId());
        }
        GameHasUserService gameHasUserService = new GameHasUserService();
        List<Integer> usersFirstGame = gameHasUserService.getMatches(gameManager.selectGame(LocalDate.now(),2).getId());
        List<Integer> usersSecondGame = gameHasUserService.getMatches(gameManager.selectGame(LocalDate.now(),3).getId());
        System.out.println(usersFirstGame.toString());
        System.out.println(usersSecondGame.toString());
    }
    @Test

    void loadGames(){
        gameManager.getGames().forEach(game -> {
            System.out.println("players: "+game.getPlayers_id().toString()+", clues: "+ game.getUsed_clues_id()+", rewards: "+game.getRewards_id());
        });
    }

    @Test
    void removePlayerFromGame() {
        gameManager.removePlayerFromGame(LocalDate.of(2024,3,19),4,2);
    }

    @Test
    void playGame() throws SQLException {
        LocalDate dateGame = LocalDate.now().plusDays(1);
        Game newGame = gameManager.createGame(dateGame,1);
        UserService userService = new UserService();
        List<User> users = userService.getAllEntities(ConnectionManager.getConnection());
        users.forEach(user -> newGame.addPlayer(user.getId()));
        Game playedGame = gameManager.playGame(dateGame,1);
        System.out.println(playedGame.toString());
        GameService gameService = new GameService();
        gameService.delete(newGame.getId());
    }
}