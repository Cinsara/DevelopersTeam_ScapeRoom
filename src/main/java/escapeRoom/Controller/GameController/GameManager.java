package escapeRoom.Controller.GameController;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Controller.GameController.Exceptions.GameAlreadyPlayed;
import escapeRoom.Controller.GameController.Exceptions.GameNotAvailableException;
import escapeRoom.Controller.GameController.Exceptions.GameNotBookedException;
import escapeRoom.Controller.GameController.Exceptions.NoTicketException;
import escapeRoom.Service.AbsentEntityException;
import escapeRoom.Service.AssetService.RewardService;
import escapeRoom.Service.AssetService.TicketService;
import escapeRoom.Service.PropAndClueService.ClueService;
import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.ManyToManyService.GameHasUserService;
import escapeRoom.Service.ManyToManyService.GameUsesClueService;
import escapeRoom.model.AssetsArea.AssetBuilder.AssetFactory;
import escapeRoom.model.AssetsArea.AssetBuilder.AssetType;
import escapeRoom.model.AssetsArea.RewardBuilder.Reward;
import escapeRoom.model.AssetsArea.TicketBuilder.Ticket;
import escapeRoom.model.GameArea.CluePropFactory.Clue;
import escapeRoom.model.GameArea.GameBuilder.Game;
import escapeRoom.model.GameArea.GameBuilder.GameBuilder;
import escapeRoom.model.PeopleArea.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class GameManager {
    private GameService gameService;

    private Set<Game> games;

    public Set<Game> getGames() {
        return games;
    }

    public GameManager() throws SQLException, AbsentEntityException {
        GameManagerInitializer.initialize(this);
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
        return this.games.stream().filter(game->game.getCaptainId()!=0).toList();
    }
    public List<Game> showBookedGames(LocalDate dateGame){
        return this.games.stream()
                .filter(game->game.getCaptainId()!=0)
                .filter(game -> game.getDate().equals(dateGame))
                .toList();

    }
    public List<Game> showBookedGames(int roomId){
        return this.games.stream()
                .filter(game->game.getCaptainId()!=0)
                .filter(game -> game.getRoom_id() == roomId)
                .toList();
    }
    public List<Game> showAvailableGames(){
        return this.games.stream().filter(game -> game.getCaptainId()==0).toList();
    }
    public List<Game> showAvailableGames(LocalDate dateGame){
        return this.games.stream()
                .filter(game->game.getCaptainId()==0)
                .filter(game -> game.getDate().equals(dateGame))
                .toList();

    }
    public List<Game> showAvailableGames(int roomId){
        return this.games.stream()
                .filter(game->game.getCaptainId()==0)
                .filter(game -> game.getRoom_id() == roomId)
                .toList();
    }

    public boolean bookGame(LocalDate dateGame,int roomId,User captain){

        try {
            TicketService ticketService = new TicketService();
            Game targetGame = selectGame(dateGame,roomId);
            Game bookableGame = checkGameAvailable(targetGame,dateGame,roomId);
            AssetFactory ticketFactory = new AssetFactory();
            Ticket newTicket = (Ticket) ticketFactory.createAsset(AssetType.TICKET,captain.getId(),bookableGame.getId(),20.0F);
            ticketService.create(newTicket);
            bookableGame.setCaptain(captain);
            gameService.update(bookableGame);
            return true;
        } catch (GameNotAvailableException | SQLException e ) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean cancelBooking(LocalDate dateGame,int roomId){
        try{
            TicketService ticketService = new TicketService();
            Game targetGame = selectGame(dateGame,roomId);
            if (targetGame.getCaptainId()==0) {
                throw new GameNotBookedException(targetGame);
            }
            if (targetGame.getEllapsedTimeInSeconds()>0){
                throw new GameAlreadyPlayed(targetGame.getDate(),targetGame.getRoom_id());
            }
            List<Ticket> allTickets = ticketService.getAllEntities(ConnectionManager.getConnection());
            Ticket targetTicket = allTickets.stream().filter(ticket -> ticket.getGame_id() == targetGame.getId()).findFirst().orElse(null);
            if (targetTicket == null){
                throw new NoTicketException(targetGame);
            }
            ticketService.delete(targetTicket.getId());
            targetGame.setCaptain(null);
            targetGame.getPlayers().clear();
            return true;
        } catch (SQLException | GameNotAvailableException | GameNotBookedException | NoTicketException | GameAlreadyPlayed e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean addPlayerToGame(LocalDate dateGame,int roomId, User player){
       try {
           Game targetGame = selectGame(dateGame,roomId);
           GameHasUserService gameHasUserService = new GameHasUserService();
           gameHasUserService.createMatch(targetGame.getId(),player.getId());
           targetGame.addPlayer(player);
           return true;
       } catch (GameNotAvailableException | SQLException e) {
           System.out.println("Error: " + e.getMessage());
           return false;
       }
    }

    public boolean removePlayerFromGame(LocalDate dateGame,int roomId, User player){
        try {
            Game targetGame = selectGame(dateGame,roomId);
            GameHasUserService gameHasUserService = new GameHasUserService();
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

    public Game playGame(LocalDate dateGame,int roomId){
        try{
            Game targetGame = selectGame(dateGame,roomId);
            if (targetGame.getEllapsedTimeInSeconds()>0){
                throw new GameAlreadyPlayed(dateGame,roomId);
            }
            List<Clue> availableClues = getAvailableClues(targetGame);
            targetGame.calculateResult(availableClues);
            GameUsesClueService gameUsesClueService = new GameUsesClueService();
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
        if (game.getCaptainId()!=0){
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
        RewardService rewardService = new RewardService();
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
