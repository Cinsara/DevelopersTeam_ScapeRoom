package escapeRoom.Controller.GameController.GameManager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Controller.GameController.Exceptions.GameAlreadyPlayed;
import escapeRoom.Controller.GameController.Exceptions.GameNotAvailableException;
import escapeRoom.Controller.GameController.Exceptions.GameNotBookedException;
import escapeRoom.Controller.GameController.Exceptions.NoTicketException;
import escapeRoom.Controller.GameController.GameCoordinates;
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

public class GameManager {
    private GameService gameService;
    private TicketService ticketService;
    private RewardService rewardService;
    private GameHasUserService gameHasUserService;
    private GameUsesClueService gameUsesClueService;
    private UserService userService;
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

        GameManagerInitializer.initialize(this,gameService,services.roomService,userService,gameHasUserService,gameUsesClueService,rewardService,services.clueService);
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
        LocalDate dateGame = coordinates.gameDate;
        int roomId = coordinates.gameRoom.getId();
        User captain = coordinates.captain;
        try {
            Game targetGame = selectGame(dateGame,roomId);
            Game bookableGame = checkGameAvailable(targetGame,dateGame,roomId);
            Ticket newTicket = (Ticket) new AssetFactory().createAsset(AssetType.TICKET,captain.getId(),bookableGame.getId(),20.0F);
            ticketService.create(newTicket);
            bookableGame.setCaptain(captain);
            gameService.update(bookableGame);
            return true;
        } catch (GameNotAvailableException | SQLException e ) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean cancelBooking(GameCoordinates coordinates){
        LocalDate dateGame = coordinates.gameDate;
        int roomId = coordinates.gameRoom.getId();
        try{
            Game targetGame = selectGame(dateGame,roomId);
            validateGameCancellable(targetGame);
            targetGame.setCaptain(null);
            targetGame.getPlayers().clear();
            gameService.update(targetGame);
            Ticket targetTicket = getGameTicket(targetGame,ticketService);
            ticketService.delete(targetTicket.getId());
            return true;
        } catch (SQLException | GameNotAvailableException | GameNotBookedException | NoTicketException | GameAlreadyPlayed e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    private void validateGameCancellable(Game game) throws GameNotBookedException, GameAlreadyPlayed {
        if (game.getCaptainId()==null) {
            throw new GameNotBookedException(game);
        }
        if (game.getEllapsedTimeInSeconds()>0){
            throw new GameAlreadyPlayed(game.getDate(),game.getRoom_id());
        }
    }
    private Ticket getGameTicket(Game game, TicketService ticketService) throws SQLException, NoTicketException {
        List<Ticket> allTickets = ticketService.getAllEntities(ConnectionManager.getConnection());
        Ticket targetTicket = allTickets.stream().filter(ticket -> ticket.getGame_id() == game.getId()).findFirst().orElse(null);
        if (targetTicket == null){
            throw new NoTicketException(game);
        }
        return targetTicket;
    }
    public boolean addPlayerToGame(GameCoordinates coordinates){
        LocalDate dateGame = coordinates.gameDate;
        int roomId = coordinates.gameRoom.getId();
        User player = coordinates.captain;
        return addPlayerToGame(dateGame,roomId,player);
    }
    public boolean addPlayerToGame(LocalDate dateGame,int roomId,User player){
       try {
           Game targetGame = selectGame(dateGame,roomId);
           gameHasUserService.createMatch(targetGame.getId(),player.getId());
           targetGame.addPlayer(player);
           return true;
       } catch (GameNotAvailableException | SQLException e) {
           System.out.println("Error: " + e.getMessage());
           return false;
       }
    }

    public boolean removePlayerFromGame(GameCoordinates coordinates){
        LocalDate dateGame = coordinates.gameDate;
        int roomId = coordinates.gameRoom.getId();
        User player = coordinates.captain;
        try {
            Game targetGame = selectGame(dateGame,roomId);
            if(gameHasUserService.deleteMatch(targetGame.getId(),player.getId())){
                targetGame.removePlayer(player);
                return true;
            }else{
                System.out.println("Error: player not in game");
                return false;
            }

        } catch (GameNotAvailableException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public Game playGame(GameCoordinates coordinates){
        LocalDate dateGame = coordinates.gameDate;
        int roomId = coordinates.gameRoom.getId();
        return playGame(dateGame,roomId);
    }

    public Game playGame(LocalDate dateGame,int roomId){
        try{
            Game targetGame = selectGame(dateGame,roomId);
            if (targetGame.getEllapsedTimeInSeconds()>0){
                throw new GameAlreadyPlayed(dateGame,roomId);
            }
            List<Clue> availableClues = getAvailableClues(targetGame);
            targetGame.calculateResult(availableClues);

            for (Clue clue: targetGame.getUsedClues()){
                gameUsesClueService.createMatch(targetGame.getId(),clue.getId());
            }
            targetGame.setRewardsGiven(saveRewards(targetGame));
            gameService.update(targetGame);
            return targetGame;

        } catch (GameNotAvailableException | SQLException | GameAlreadyPlayed e) {
            System.out.println("Error: "+ e.getMessage());
            return null;
        }
    }

    public Game selectGame(LocalDate dateGame, int roomId) throws GameNotAvailableException {
        Game selectedGame;
        try {
            return this.games.stream()
                    .filter(game -> game.getDate().equals(dateGame))
                    .filter(game -> game.getRoom_id() == roomId)
                    .toList().getFirst();
        } catch (RuntimeException e) {
            throw new GameNotAvailableException(dateGame);
        }
    }

    private Game checkGameAvailable(Game game, LocalDate dateGame, int roomId) throws GameNotAvailableException {
        if (game.getCaptainId()!=null){
            throw new GameNotAvailableException(dateGame,roomId);
        }
        return game;
    }

    private List<Clue> getAvailableClues(Game game) throws SQLException {
        ClueService clueService = new ClueService(ConnectionManager.getConnection());
        return clueService.getAllEntities(ConnectionManager.getConnection()).stream()
                .filter(clue -> clue.getRoomId() == game.getRoom_id())
                .toList();
    }
    private List<Reward> saveRewards(Game game) throws SQLException {
        List<Reward> rewardsWithId = new ArrayList<>();
        for (Reward reward : game.getRewardsGiven()) {
            Reward createdReward = rewardService.create(reward);  // may throw SQLException
            rewardsWithId.add(createdReward);
        }
        return rewardsWithId;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }
    public GameService getGameService(){return this.gameService;}
}
