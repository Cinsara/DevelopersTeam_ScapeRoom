package escapeRoom.Controller.GameController;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.AbsentEntityException;
import escapeRoom.Service.AssetService.RewardService;
import escapeRoom.Service.ManyToManyService.GameHasUserService;
import escapeRoom.Service.ManyToManyService.GameUsesClueService;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.Service.PropAndClueService.ClueService;
import escapeRoom.model.AssetsArea.RewardBuilder.Reward;
import escapeRoom.model.GameArea.CluePropFactory.Clue;
import escapeRoom.model.GameArea.GameBuilder.Game;
import escapeRoom.model.PeopleArea.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GameInitializer {

    static protected void setGamePlayers(Game game, UserService userService,GameHasUserService gameHasUserService) throws SQLException {

        game.setPlayers(gameHasUserService.getMatches(game.getId()).stream().map(playerId->{
            try{
                Optional<User> potentialPlayer = userService.read(playerId);
                if(potentialPlayer.isPresent()) return potentialPlayer.get();
                else {throw new SQLException("No user return by service");}
            } catch (SQLException e) {
                System.out.println("No user found corresponding to id number "+  playerId + ". Charging null player instead");
                return null;
            }
        }).collect(Collectors.toList()));
    }

    static protected void addCaptainToPlayersIfNeeded(GameManager gameManager, Game game,UserService userService) {
        try{
            if (isCaptainMissingFromPlayerList(game)){
                Optional<User> potentialCaptain = userService.read(game.getCaptainId());
                if (potentialCaptain.isPresent()){
                    gameManager.addPlayerToGame(game.getDate(), game.getRoom_id(), potentialCaptain.get());
                    return;
                }else{
                    throw new AbsentEntityException(game.getCaptainId(),User.class);
                }
            }
        }catch (SQLException | AbsentEntityException e){
            System.out.println("Error : " + e.getMessage());
        }

    }
    static protected boolean isCaptainMissingFromPlayerList(Game game){
        return game.getCaptainId()!=null && !game.getPlayers().stream().map(User::getId).toList().contains(game.getCaptainId()) ;
    }
    static protected List<Clue> retrieveUsedClues(Game game,GameUsesClueService gameUsesClueService) throws SQLException {

        return gameUsesClueService.getMatches(game.getId()).stream().map(GameInitializer::getClueFromId).collect(Collectors.toList());
    }

    static protected List<Reward> retrieveRewardsGiven(Game game, RewardService rewardService) throws SQLException {
        return rewardService.getAllEntities(ConnectionManager.getConnection()).stream()
                .filter(reward -> reward.getGame_id()== game.getId())
                .toList();
    }
    static protected Clue getClueFromId(int clueId){
        try{
            ClueService clueService = new ClueService(ConnectionManager.getConnection());
            Optional<Clue> potentialClue = clueService.read(clueId);
            if (potentialClue.isPresent()) return potentialClue.get();
            else {
                System.out.println("Error: no clue matching id " + clueId);
                return null;
            }
        }catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
