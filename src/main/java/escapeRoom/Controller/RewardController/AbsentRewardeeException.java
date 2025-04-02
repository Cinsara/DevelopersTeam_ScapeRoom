package escapeRoom.Controller.RewardController;

import escapeRoom.model.AssetsArea.RewardBuilder.Reward;

public class AbsentRewardeeException extends RuntimeException {
    public AbsentRewardeeException(Reward reward) {
        super("Alleged holder of reward " + reward.getId() + "not found in database.");
    }
}
