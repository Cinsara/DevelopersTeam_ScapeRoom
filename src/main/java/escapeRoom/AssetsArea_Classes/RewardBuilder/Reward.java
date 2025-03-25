package escapeRoom.AssetsArea_Classes.RewardBuilder;

import escapeRoom.AssetsArea_Classes.AssetBuilder.Asset;
import escapeRoom.AssetsArea_Classes.Classes_testing.Game;
import escapeRoom.AssetsArea_Classes.Classes_testing.PersonClasses.User;
import java.time.LocalDate;

public class Reward implements Asset {
    private final int user_id;
    private final int game_id;

    public Reward(int user_id, int game_id){
        this.user_id = user_id;
        this.game_id = game_id;
    }

    @Override
    public int getUser_id() {
        return this.user_id;
    }

    @Override
    public int getGame_id() {
        return this.game_id;
    }

    @Override
    public void expressPrize(User user, Game game, LocalDate date) {
        System.out.println("Reward given to " + user + ", of the game " +
                game + ", with the date of " + date);
    }

}
