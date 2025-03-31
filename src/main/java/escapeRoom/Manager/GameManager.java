package escapeRoom.Manager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.AssetService.TicketService;
import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.model.AssetsArea.AssetBuilder.AssetFactory;
import escapeRoom.model.AssetsArea.AssetBuilder.AssetType;
import escapeRoom.model.AssetsArea.TicketBuilder.Ticket;
import escapeRoom.model.GameArea.GameBuilder.Game;
import escapeRoom.model.GameArea.GameBuilder.GameBuilder;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class GameManager {
    private InputService inputService;
    private GameService gameService;
    private TicketService ticketService;
    private List<Game> games;

    public GameManager(InputService inputService, GameService gameService, TicketService ticketService) throws SQLException {
        this.inputService = inputService;
        this.gameService = gameService;
        this.ticketService = ticketService;
        this.games = gameService.getAllEntities(ConnectionManager.getConnection());
    }

    public Game createGame(LocalDate dateGame, int roomId){
        Game newGame = new GameBuilder(roomId,dateGame).build();
        try {
            Game createdGame = gameService.create(newGame);
            this.games.add(newGame);
            return createdGame;
        } catch (SQLException e) {
           System.out.println("Error creating game in " + roomId + " on " + dateGame + ": "+ e.getMessage());
           return null;
        }
    }

    private Game selectGame(LocalDate dateGame, int roomId){
        return this.games.stream()
                .filter(game->game.getDate() == dateGame)
                .filter(game -> game.getRoom_id()==roomId)
                .toList().getFirst();
    }

    private Game checkGameExist(Game game,LocalDate dateGame) throws GameNotAvailableException {
        if (game == null) {
            throw new GameNotAvailableException(dateGame);
        }
        return game;
    }
    private Game checkGameAvailable(Game game, LocalDate dateGame, int roomId) throws GameNotAvailableException {
        if (game.getCaptain_id()!=null){
            throw new GameNotAvailableException(dateGame,roomId);
        }
        return game;
    }

    public boolean bookGame(LocalDate dateGame,int roomId,int captainId){
        Game targetGame = selectGame(dateGame, roomId);
        Game existingGame,bookableGame;
        try {
            existingGame = checkGameExist(targetGame,dateGame);
            bookableGame = checkGameAvailable(existingGame,dateGame,roomId);
            bookableGame.setCaptain(captainId);
            AssetFactory ticketFactory = new AssetFactory();
            Ticket newTicket = (Ticket) ticketFactory.createAsset(AssetType.TICKET,captainId,bookableGame.getId(),20);
            ticketService.create(newTicket);
            return true;
        } catch (GameNotAvailableException | SQLException e ) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }




}
