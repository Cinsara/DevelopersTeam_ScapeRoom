package escapeRoom.Controller;

import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.model.GameArea.GameBuilder.Game;
import escapeRoom.model.GameArea.GameBuilder.GameBuilder;

import java.sql.SQLException;
import java.time.LocalDate;

public class GameController {
    private InputService inputService;
    private GameService gameService;

    public GameController(InputService inputService, GameService gameService) {
        this.inputService = inputService;
        this.gameService = gameService;
    }

    public Game createGame(LocalDate dateGame, int roomId){
        Game newGame = new GameBuilder(roomId,dateGame).build();
        try {
            return gameService.create(newGame);
        } catch (SQLException e) {
           System.out.println("Error creating game in " + roomId + " on " + dateGame + ": "+ e.getMessage());
           return null;
        }
    }
}
