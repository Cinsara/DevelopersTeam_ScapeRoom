package escapeRoom.Manager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.AssetService.TicketService;
import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.InputService.InputService;
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
    private List<Game> games;

    public GameManager(InputService inputService, GameService gameService, TicketService ticketService, UserService userService) throws SQLException {
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

    private Game selectGame(LocalDate dateGame, int roomId) throws GameNotAvailableException {
        Game selectedGame;
         try {
             return this.games.stream()
                .filter(game->game.getDate() == dateGame)
                .filter(game -> game.getRoom_id()==roomId)
                .toList().getFirst();
         } catch (RuntimeException e) {
             throw new GameNotAvailableException(dateGame);
         }
    }

    private Game checkGameAvailable(Game game, LocalDate dateGame, int roomId) throws GameNotAvailableException {
        if (game.getCaptain_id()!=null){
            throw new GameNotAvailableException(dateGame,roomId);
        }
        return game;
    }

    private boolean existUser(int id) throws SQLException, AbsentUserException {
        Optional<User> potentialUser = userService.read(id);
        if (potentialUser.isPresent()){
            return true;
        } else {
            throw new AbsentUserException(id);
        }

    }
    public boolean bookGame(LocalDate dateGame,int roomId,int captainId){
        Game targetGame, bookableGame;
        try {
            targetGame = selectGame(dateGame,roomId);
            bookableGame = checkGameAvailable(targetGame,dateGame,roomId);
            if (existUser(captainId)){
                bookableGame.setCaptain(captainId);
            }
            AssetFactory ticketFactory = new AssetFactory();
            Ticket newTicket = (Ticket) ticketFactory.createAsset(AssetType.TICKET,captainId,bookableGame.getId(),20);
            ticketService.create(newTicket);
            return true;
        } catch (GameNotAvailableException | SQLException | AbsentUserException e ) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }




}
