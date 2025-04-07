package escapeRoom.Controller.RewardController;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.AbsentEntityException;
import escapeRoom.Service.AssetService.RewardService;
import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.model.AssetsArea.RewardBuilder.Reward;
import escapeRoom.model.GameArea.GameBuilder.Game;
import escapeRoom.model.PeopleArea.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RewardController {
    private RewardService rewardService;
    private GameService gameService;
    private UserService userService;

    public RewardController(RewardService rewardService, GameService gameService, UserService userService){
        this.rewardService = rewardService;
        this.gameService = gameService;
        this.userService = userService;
    }

    public void showAllRewards(){
        try{
            List<Reward> rewardList = rewardService.getAllEntities(ConnectionManager.getConnection());
            for (Reward reward : rewardList){
                Optional<User> potentialUser = userService.read(reward.getUser_id());
                if (potentialUser.isEmpty()) throw new AbsentEntityException(reward.getUser_id(),User.class);
                User user = potentialUser.get();
                Optional<Game> potentialGame = gameService.read(reward.getGame_id());
                if(potentialGame.isEmpty()) throw new AbsentEntityException(reward.getGame_id(),Game.class);
                Game game = potentialGame.get();
                reward.expressAsset(user,game,game.getDate());
            }
        } catch (SQLException | AbsentEntityException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
