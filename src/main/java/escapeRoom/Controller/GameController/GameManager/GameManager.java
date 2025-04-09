package escapeRoom.Controller.GameController.GameManager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Controller.GameController.Exceptions.GameAlreadyPlayed;
import escapeRoom.Controller.GameController.Exceptions.GameNotAvailableException;
import escapeRoom.Controller.GameController.Exceptions.GameNotBookedException;
import escapeRoom.Controller.GameController.Exceptions.NoTicketException;
import escapeRoom.Controller.GameController.ControllerHelpers.GameCoordinates;
import escapeRoom.SetUp.EscapeRoomServices.ServicesForGameManager;
import escapeRoom.Service.AssetService.RewardService;
import escapeRoom.Service.AssetService.TicketService;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.Service.PropAndClueService.ClueService;
import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.ManyToManyService.GameHasUserService;
import escapeRoom.Service.ManyToManyService.GameUsesClueService;
import escapeRoom.Model.AssetsArea.AssetBuilder.AssetFactory;
import escapeRoom.Model.AssetsArea.AssetBuilder.AssetType;
import escapeRoom.Model.AssetsArea.RewardBuilder.Reward;
import escapeRoom.Model.AssetsArea.TicketBuilder.Ticket;
import escapeRoom.Model.GameArea.CluePropFactory.Clue;
import escapeRoom.Model.GameArea.GameBuilder.Game;
import escapeRoom.Model.GameArea.GameBuilder.GameBuilder;
import escapeRoom.Model.PeopleArea.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static escapeRoom.Controller.GameController.GameManager.GameManagerHelpers.*;

public class GameManager {
    private GameService gameService;
    private TicketService ticketService;
    private RewardService rewardService;
    private GameHasUserService gameHasUserService;
    private GameUsesClueService gameUsesClueService;
    private UserService userService;
    private ClueService clueService;
    private Set<Game> games;

    public Set<Game> getGames() {
        return games;
    }

    public GameManager(ServicesForGameManager services) throws SQLException {
        this.gameService = services.gameService;
        this.userService = services.userService;
        this.ticketService = services.ticketService;
        this.rewardService = services.rewardService;
        this.gameHasUserService = services.gameHasUserService;
        this.gameUsesClueService = services.gameUsesClueService;
        this.clueService = services.clueService;

        GameManagerInitializer.initialize(this,gameService,services.roomService,userService,gameHasUserService,gameUsesClueService,rewardService,clueService);
    }

    public Game createGame(LocalDate dateGame, int roomId){
        Game newGame = new GameBuilder(roomId,dateGame).build();
        try {
            Game createdGame = gameService.create(newGame);
            this.games.add(newGame);
            return createdGame;
        } catch (SQLException e) {
            return (Game) printExceptionAndReturn(e,null);
        }
    }

    public List<Game> showBookedGames(){
        return this.games.stream().filter(game->game.getCaptainId()!=null).toList();
    }
    public List<Game> showBookedGames(LocalDate dateGame){
        return this.games.stream()
                .filter(game->game.getCaptainId()!=null)
                .filter(game -> game.getDate().equals(dateGame))
                .toList();
    }
    public List<Game> showBookedGames(int roomId){
        return this.games.stream()
                .filter(game->game.getCaptainId()!=null)
                .filter(game -> game.getRoom_id() == roomId)
                .toList();
    }
    public List<Game> showAvailableGames(){
        return this.games.stream().filter(game -> game.getCaptainId()==null).toList();
    }
    public List<Game> showAvailableGames(LocalDate dateGame){
        return this.games.stream()
                .filter(game->game.getCaptainId()==null)
                .filter(game -> game.getDate().equals(dateGame))
                .toList();
    }
    public List<Game> showAvailableGames(int roomId){
        return this.games.stream()
                .filter(game->game.getCaptainId()==null)
                .filter(game -> game.getRoom_id() == roomId)
                .toList();
    }

    public boolean bookGame(GameCoordinates coordinates){
        try {
            Game targetGame = selectGameFromSet(this.getGames(),coordinates.gameDate,coordinates.gameRoom.getId());
            Game bookableGame = checkGameAvailable(targetGame);
            Ticket newTicket = (Ticket) new AssetFactory().createAsset(AssetType.TICKET,coordinates.captain.getId(),bookableGame.getId(),20.0F);
            ticketService.create(newTicket);
            bookableGame.setCaptain(coordinates.captain);
            gameService.update(bookableGame);
            return true;
        } catch (GameNotAvailableException | SQLException e ) {
            return (Boolean) printExceptionAndReturn(e,false);
        }
    }

    public boolean cancelBooking(GameCoordinates coordinates){
        try{
            Game targetGame = selectGameFromSet(this.getGames(),coordinates.gameDate,coordinates.gameRoom.getId());
            validateGameCancellable(targetGame);
            targetGame.setCaptain(null);
            targetGame.getPlayers().clear();
            gameService.update(targetGame);
            Ticket targetTicket = getGameTicket(targetGame,ticketService);
            ticketService.delete(targetTicket.getId());
            return true;
        } catch (SQLException | GameNotAvailableException | GameNotBookedException | NoTicketException | GameAlreadyPlayed e) {
            return (Boolean) printExceptionAndReturn(e,false);
        }
    }

    public boolean addPlayerToGame(GameCoordinates coordinates){
        return addPlayerToGame(coordinates.gameDate,coordinates.gameRoom.getId(),coordinates.captain);
    }
    public boolean addPlayerToGame(LocalDate dateGame,int roomId,User player){
       try {
           Game targetGame = selectGameFromSet(this.getGames(),dateGame,roomId);
           gameHasUserService.createMatch(targetGame.getId(),player.getId());
           targetGame.addPlayer(player);
           return true;
       } catch (GameNotAvailableException | SQLException e) {
           return (Boolean) printExceptionAndReturn(e,false);
       }
    }

    public boolean removePlayerFromGame(GameCoordinates coordinates){
        try {
            Game targetGame = selectGameFromSet(this.getGames(),coordinates.gameDate,coordinates.gameRoom.getId());
            if(gameHasUserService.deleteMatch(targetGame.getId(),coordinates.captain.getId())){
                targetGame.removePlayer(coordinates.captain);
                return true;
            }else{
                System.out.println("Error: player not in game");
                return false;
            }
        } catch (GameNotAvailableException | SQLException e) {
            return (Boolean) printExceptionAndReturn(e,false);
        }
    }

    public Game playGame(GameCoordinates coordinates){
        return playGame(coordinates.gameDate,coordinates.gameRoom.getId());
    }

    public Game playGame(LocalDate dateGame,int roomId){
        try{
            Game targetGame = selectGameFromSet(this.getGames(),dateGame,roomId);
            checkAlreadyPlayer(targetGame);
            targetGame.calculateResult(getAvailableClues(targetGame,clueService));
            return saveResult(targetGame,gameService,rewardService,gameUsesClueService);

        } catch (GameNotAvailableException | SQLException | GameAlreadyPlayed e) {
            return (Game) printExceptionAndReturn(e, null);
        }
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }
    public GameService getGameService(){return this.gameService;}
}
