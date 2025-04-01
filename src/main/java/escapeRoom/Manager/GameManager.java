package escapeRoom.Manager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.AssetService.RewardService;
import escapeRoom.Service.AssetService.TicketService;
import escapeRoom.Service.GameService.ClueService;
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

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class GameManager {
    private GameService gameService;

    private Set<Game> games;

    public Set<Game> getGames() {
        return games;
    }

    public GameManager() throws SQLException {
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

    public boolean bookGame(LocalDate dateGame,int roomId,int captainId){
        Game targetGame, bookableGame;
        try {
            TicketService ticketService = new TicketService();
            targetGame = selectGame(dateGame,roomId);
            bookableGame = checkGameAvailable(targetGame,dateGame,roomId);
            AssetFactory ticketFactory = new AssetFactory();
            Ticket newTicket = (Ticket) ticketFactory.createAsset(AssetType.TICKET,captainId,bookableGame.getId(),20.0F);
            ticketService.create(newTicket);
            bookableGame.setCaptain(captainId);
            gameService.update(bookableGame);
            return true;
        } catch (GameNotAvailableException | SQLException e ) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean addPlayerToGame(LocalDate dateGame,int roomId, int userId){
       try {
           Game targetGame = selectGame(dateGame,roomId);
           GameHasUserService gameHasUserService = new GameHasUserService();
           gameHasUserService.createMatch(targetGame.getId(),userId);
           targetGame.addPlayer(userId);
           return true;
       } catch (GameNotAvailableException | SQLException e) {
           System.out.println("Error: " + e.getMessage());
           return false;
       }
    }

    public boolean removePlayerFromGame(LocalDate dateGame,int roomId, int userId){
        try {
            Game targetGame = selectGame(dateGame,roomId);
            GameHasUserService gameHasUserService = new GameHasUserService();
            gameHasUserService.deleteMatch(targetGame.getId(),userId);
            targetGame.removePlayer(userId);
            return true;
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
            List<Integer> availableClues = getAvailableClues(targetGame);
            targetGame.calculateResult(availableClues);
            GameUsesClueService gameUsesClueService = new GameUsesClueService();
            for (int clue: targetGame.getUsed_clues_id()){
                gameUsesClueService.createMatch(targetGame.getId(),clue);
            }
            targetGame.setRewards_id(getRewardsId(targetGame));
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
        if (game.getCaptain_id()!=0){
            throw new GameNotAvailableException(dateGame,roomId);
        }
        return game;
    }

    private List<Integer> getAvailableClues(Game game) throws SQLException {
        ClueService clueService = new ClueService(ConnectionManager.getConnection());
        return clueService.getAllEntities(ConnectionManager.getConnection()).stream()
                .filter(clue -> clue.getRoomId() == game.getRoom_id())
                .map(Clue::getId)
                .toList();
    }
    private List<Integer> getRewardsId(Game game) throws SQLException {
        List<Integer> ids = new ArrayList<>();
        RewardService rewardService = new RewardService();
        for (Integer reward : game.getRewards_id()) {
            AssetFactory factory = new AssetFactory();
            Reward newReward = (Reward) factory.createAsset(AssetType.REWARD, reward, game.getId());
            Reward createdReward = rewardService.create(newReward);  // may throw SQLException
            ids.add(createdReward.getId());
        }
        return ids;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }


    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }
}
