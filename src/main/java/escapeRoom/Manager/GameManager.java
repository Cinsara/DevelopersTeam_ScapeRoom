package escapeRoom.Manager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.AbsentEntityException;
import escapeRoom.Service.AssetService.TicketService;
import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.ManyToManyService.GameHasUserService;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.model.AssetsArea.AssetBuilder.AssetFactory;
import escapeRoom.model.AssetsArea.AssetBuilder.AssetType;
import escapeRoom.model.AssetsArea.TicketBuilder.Ticket;
import escapeRoom.model.GameArea.GameBuilder.Game;
import escapeRoom.model.GameArea.GameBuilder.GameBuilder;
import escapeRoom.model.PeopleArea.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class GameManager {
    private InputService inputService;
    private GameService gameService;
    private TicketService ticketService;
    private UserService userService;
    private GameHasUserService gameHasUserService;
    private List<Game> games;

    public List<Game> getGames() {
        return games;
    }

    public GameManager(InputService inputService, GameService gameService, TicketService ticketService, UserService userService, GameHasUserService gameHasUserService) throws SQLException {
        this.inputService = inputService;
        this.gameService = gameService;
        this.ticketService = ticketService;
        this.userService = userService;
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

    public boolean bookGame(LocalDate dateGame,int roomId,int captainId){
        Game targetGame, bookableGame;
        try {
            targetGame = selectGame(dateGame,roomId);
            bookableGame = checkGameAvailable(targetGame,dateGame,roomId);
            if (userService.existEntity(captainId, User.class)){
                bookableGame.setCaptain(captainId);
            }
            AssetFactory ticketFactory = new AssetFactory();

            Ticket newTicket = (Ticket) ticketFactory.createAsset(AssetType.TICKET,captainId,bookableGame.getId(),20.0F);
            ticketService.create(newTicket);
            gameService.update(bookableGame);
            return true;
        } catch (GameNotAvailableException | SQLException | AbsentEntityException e ) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public void addPlayerToGame(LocalDate dateGame,int roomId, int userId){
       try {
           Game targetGame = selectGame(dateGame,roomId);
           targetGame.getPlayers_id().add(userId);

           System.out.println("Player correctly added to game");
       } catch (GameNotAvailableException e) {
           System.out.println("Error: " + e.getMessage());
           return false;
       }

    }

    private Game selectGame(LocalDate dateGame, int roomId) throws GameNotAvailableException {
        Game selectedGame;
         try {
             return this.games.stream()
                .filter(game-> game.getDate().equals(dateGame))
                .filter(game -> game.getRoom_id() == (roomId))
                .toList().getFirst();
         } catch (RuntimeException e) {
             throw new GameNotAvailableException(dateGame);
         }
    }

    private Game checkGameAvailable(Game game, LocalDate dateGame, int roomId) throws GameNotAvailableException {
        if (game.getCaptain_id()!=0){
            throw new GameNotAvailableException(dateGame,roomId);
        }
        return game;
    }

}
