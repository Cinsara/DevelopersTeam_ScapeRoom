package escapeRoom.Controller.GameController.GameManager;

import escapeRoom.Controller.GameController.Exceptions.GameNotAvailableException;
import escapeRoom.Service.AssetService.RewardService;
import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.ManyToManyService.GameHasUserService;
import escapeRoom.Service.ManyToManyService.GameUsesClueService;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.Service.PropAndClueService.ClueService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.Model.GameArea.GameBuilder.Game;
import escapeRoom.Model.GameArea.GameBuilder.GameBuilder;
import escapeRoom.Model.GameArea.RoomBuilder.Room;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

public class GameManagerInitializer {

    static void initialize(GameManager gameManager, GameService gameService, RoomService roomService, UserService userService, GameHasUserService gameHasUserService, GameUsesClueService gameUsesClueService, RewardService rewardService, ClueService clueService) throws SQLException {
        gameManager.setGameService(gameService);
        gameManager.setGames(new HashSet<>(gameService.getAllEntities(gameService.getConnection())));
        List<Room> rooms = roomService.getAllEntities(roomService.getConnection());
        scheduleNewGames(gameManager,rooms);
        for (Game game : gameManager.getGames()) {
            GameInitializer.setGamePlayers(game,userService,gameHasUserService);
            GameInitializer.addCaptainToPlayersIfNeeded(gameManager,game,userService);
            game.setUsedClues(GameInitializer.retrieveUsedClues(game, gameUsesClueService,clueService));
            game.setRewardsGiven(GameInitializer.retrieveRewardsGiven(game,rewardService));
        }
    }


    static public void scheduleNewGames(GameManager gameManager, List<Room> rooms) throws SQLException {
        for (int i = 1; i <= 14; i++) {
            LocalDate date = LocalDate.now().plusDays(i);
            for (Room room : rooms) {
                checkIfGamesExistWithinNextTwoWeeks(gameManager, room, date);
            }
        }
    }

    static private void checkIfGamesExistWithinNextTwoWeeks(GameManager gameManager, Room room, LocalDate date) throws SQLException {
        try {
            gameManager.selectGame(date, room.getId());
        } catch (GameNotAvailableException e) {
            createAndRegisterNewGame(gameManager, room, date);
        }
    }

    static private void createAndRegisterNewGame(GameManager gameManager, Room room, LocalDate date) throws SQLException {
        Game newGame = new GameBuilder(room.getId(), date).build();
        gameManager.getGameService().create(newGame); // may throw SQLException
        gameManager.getGames().add(newGame);
    }


}
