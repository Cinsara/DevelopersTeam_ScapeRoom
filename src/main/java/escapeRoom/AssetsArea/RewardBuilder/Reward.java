package escapeRoom.AssetsArea.RewardBuilder;
import escapeRoom.PeopleArea.User;
import escapeRoom.GameArea.GameBuilder.Game;
import escapeRoom.AssetsArea.AssetBuilder.Asset;


import java.time.LocalDate;

public class Reward implements Asset {
    private final int user_id;
    private final int game_id;
    private int id;

    public Reward(int user_id, int game_id){
        this.user_id = user_id;
        this.game_id = game_id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public int getId() {
        return id;
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
    public <T> void expressAsset(User user, T entity, LocalDate date) {
        if (entity instanceof Game game) {
            System.out.println("Reward given to " + user.getName() + ", for the game who took place on the " +
                    game.getDate() + ", in the room number " + game.getRoom_id());
        } else {
            throw new IllegalArgumentException("Entity must be of type Game.");
        }
    }
}
