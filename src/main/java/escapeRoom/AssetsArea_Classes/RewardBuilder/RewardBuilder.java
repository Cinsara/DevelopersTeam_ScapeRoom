package escapeRoom.AssetsArea_Classes.RewardBuilder;

import escapeRoom.AssetsArea_Classes.AssetBuilder.AssetBuilder;

public class RewardBuilder implements AssetBuilder<Reward> {
    private int user_id;
    private int game_id;

    @Override
    public AssetBuilder<Reward> setUserId(int user_id) {
        this.user_id = user_id;
        return this;
    }

    @Override
    public AssetBuilder<Reward> setGameId(int game_id) {
        this.game_id = game_id;
        return this;
    }

    @Override
    public Reward build() {
        return new Reward(user_id,game_id);
    }
}
