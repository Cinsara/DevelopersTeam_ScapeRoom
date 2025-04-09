package escapeRoom.Controller.GameController.GameManager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Controller.GameController.Exceptions.GameAlreadyPlayed;
import escapeRoom.Controller.GameController.Exceptions.GameNotAvailableException;
import escapeRoom.Controller.GameController.Exceptions.GameNotBookedException;
import escapeRoom.Controller.GameController.Exceptions.NoTicketException;
import escapeRoom.Model.AssetsArea.RewardBuilder.Reward;
import escapeRoom.Model.AssetsArea.TicketBuilder.Ticket;
import escapeRoom.Model.GameArea.CluePropFactory.Clue;
import escapeRoom.Model.GameArea.GameBuilder.Game;
import escapeRoom.Service.AssetService.RewardService;
import escapeRoom.Service.AssetService.TicketService;
import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.ManyToManyService.GameUsesClueService;
import escapeRoom.Service.PropAndClueService.ClueService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GameManagerHelpers {
    static void validateGameCancellable(Game game) throws GameNotBookedException, GameAlreadyPlayed {
        if (game.getCaptainId()==null) {
            throw new GameNotBookedException(game);
        }
        if (game.getEllapsedTimeInSeconds()>0){
            throw new GameAlreadyPlayed(game.getDate(),game.getRoom_id());
        }
    }
    static Ticket getGameTicket(Game game, TicketService ticketService) throws SQLException, NoTicketException {
        List<Ticket> allTickets = ticketService.getAllEntities(ConnectionManager.getConnection());
        Ticket targetTicket = allTickets.stream().filter(ticket -> ticket.getGame_id() == game.getId()).findFirst().orElse(null);
        if (targetTicket == null){
            throw new NoTicketException(game);
        }
        return targetTicket;
    }
    public static Game selectGameFromSet(Set<Game> games, LocalDate dateGame, int roomId) throws GameNotAvailableException {
        try {
            return games.stream()
                    .filter(game -> game.getDate().equals(dateGame))
                    .filter(game -> game.getRoom_id() == roomId)
                    .toList().getFirst();
        } catch (RuntimeException e) {
            throw new GameNotAvailableException(dateGame);
        }
    }
    static Game checkGameAvailable(Game game) throws GameNotAvailableException {
        if (game.getCaptainId()!=null){
            throw new GameNotAvailableException(game.getDate(),game.getRoom_id());
        }
        return game;
    }
    static List<Clue> getAvailableClues(Game game, ClueService clueService) throws SQLException {
        return clueService.getAllEntities(ConnectionManager.getConnection()).stream()
                .filter(clue -> clue.getRoomId() == game.getRoom_id())
                .toList();
    }
    static List<Reward> saveRewards(Game game, RewardService rewardService) throws SQLException {
        List<Reward> rewardsWithId = new ArrayList<>();
        for (Reward reward : game.getRewardsGiven()) {
            Reward createdReward = rewardService.create(reward);  // may throw SQLException
            rewardsWithId.add(createdReward);
        }
        return rewardsWithId;
    }
    static void checkAlreadyPlayer(Game game) throws GameAlreadyPlayed {
        if (game.getEllapsedTimeInSeconds()>0){
            throw new GameAlreadyPlayed(game.getDate(), game.getId());
        }
    }
    static Game saveResult(Game playedGame, GameService gameService, RewardService rewardService, GameUsesClueService gameUsesClueService) throws SQLException {
        for (Clue clue: playedGame.getUsedClues()){
            gameUsesClueService.createMatch(playedGame.getId(),clue.getId());
        }
        playedGame.setRewardsGiven(saveRewards(playedGame,rewardService));
        gameService.update(playedGame);
        return playedGame;
    }
    static Object printExceptionAndReturn(Exception e, Object returnedValue){
        System.out.println("Error: "+ e.getMessage());
        return returnedValue;
    }
}
