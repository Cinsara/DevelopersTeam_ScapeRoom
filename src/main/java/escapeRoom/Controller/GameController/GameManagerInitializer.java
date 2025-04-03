package escapeRoom.Controller.GameController;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Controller.GameController.Exceptions.GameNotAvailableException;
import escapeRoom.Service.AssetService.RewardService;
import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.ManyToManyService.GameHasUserService;
import escapeRoom.Service.ManyToManyService.GameUsesClueService;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.model.AssetsArea.RewardBuilder.Reward;
import escapeRoom.model.GameArea.GameBuilder.Game;
import escapeRoom.model.GameArea.GameBuilder.GameBuilder;
import escapeRoom.model.GameArea.RoomBuilder.Room;
import escapeRoom.model.PeopleArea.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

public class GameManagerInitializer {

    static void initialize(GameManager gameManager) throws SQLException {
        GameService gameService = new GameService();
        gameManager.setGameService(gameService);
        GameHasUserService gameHasUserService = new GameHasUserService();
        GameUsesClueService gameUsesClueService = new GameUsesClueService();
        UserService userService = new UserService();
        RewardService rewardService = new RewardService();
        gameManager.setGames(new HashSet<>(gameService.getAllEntities(ConnectionManager.getConnection())));
        RoomService roomService = new RoomService();
        List<Room> rooms = roomService.getAllEntities(ConnectionManager.getConnection());
        for (int i = 1; i<=14;i++){
            for (Room room: rooms){
                try {
                    gameManager.selectGame(LocalDate.now().plusDays(i), room.getId());
                } catch (GameNotAvailableException e) {
                    Game newGame = new GameBuilder(room.getId(),LocalDate.now().plusDays(i)).build();
                    gameService.create(newGame);
                    gameManager.getGames().add(newGame);
                }
            };
        }
        for (Game game : gameManager.getGames()) {
            game.setPlayers(gameHasUserService.getMatches(game.getId()).stream().map(playerId->{
                   User newPlayer = userService.read(playerId)
            }));
            game.setUsed_clues_id(gameUsesClueService.getMatches(game.getId()));
            game.setRewards_id(rewardService.getAllEntities(ConnectionManager.getConnection()).stream()
                    .filter(reward -> reward.getGame_id()== game.getId())
                    .map(Reward::getId).toList());
        }
    }
}
