package escapeRoom.Controller.GameController;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Controller.GameController.Exceptions.GameNotAvailableException;
import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.model.GameArea.GameBuilder.Game;
import escapeRoom.model.GameArea.GameBuilder.GameBuilder;
import escapeRoom.model.GameArea.RoomBuilder.Room;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

public class GameManagerInitializer {

    static void initialize(GameManager gameManager) throws SQLException {
        GameService gameService = new GameService();
        gameManager.setGameService(gameService);
        gameManager.setGames(new HashSet<>(gameService.getAllEntities(ConnectionManager.getConnection())));
        RoomService roomService = new RoomService();
        List<Room> rooms = roomService.getAllEntities(ConnectionManager.getConnection());
        scheduleNewGames(gameManager,rooms);
        for (Game game : gameManager.getGames()) {
            GameInitializer.setGamePlayers(game);
            GameInitializer.addCaptainToPlayersIfNeeded(gameManager,game);
            game.setUsedClues(GameInitializer.retrieveUsedClues(game));
            game.setRewardsGiven(GameInitializer.retrieveRewardsGiven(game));
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
