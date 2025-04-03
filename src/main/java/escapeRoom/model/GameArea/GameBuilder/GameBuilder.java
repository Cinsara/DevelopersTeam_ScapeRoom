package escapeRoom.model.GameArea.GameBuilder;

import escapeRoom.model.PeopleArea.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GameBuilder {

    private int _id;
    private final int room_id;
    private final LocalDate date;
    private List<User> players = new ArrayList<>();
    private Integer captain_id;
    private boolean success;
    private List<Integer> used_clues_id = new ArrayList<>();;
    private int ellapsedTimeInSeconds;
    private List<Integer> rewards_id = new ArrayList<>();;

    public GameBuilder(int room_id, LocalDate date) {
        this.room_id = room_id;
        this.date = date;
    }

    public GameBuilder set_id(int _id) {
        this._id = _id;
        return this;
    }

    public GameBuilder setPlayers(List<User> players) {
        this.players = players;
        return this;
    }

    public GameBuilder addPlayer(User player) {
        this.players.add(player);
        return this;
    }

    public GameBuilder setRewards_id(List<Integer> rewards_id) {
        this.rewards_id = rewards_id;
        return this;
    }

    public GameBuilder addReward(Integer reward_id){
        this.rewards_id.add(reward_id);
        return this;
    }

    public GameBuilder setCaptain_id(Integer captain_id) {
        this.captain_id = captain_id;
        return this;
    }

    public GameBuilder setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public GameBuilder setUsed_clues_id(List<Integer> used_clues_id) {
        this.used_clues_id = used_clues_id;
        return this;
    }

    public GameBuilder setEllapsedTimeInSeconds(int ellapsedTimeInSeconds) {
        this.ellapsedTimeInSeconds = ellapsedTimeInSeconds;
        return this;
    }
    public Game build(){
        return new Game(this._id,this.room_id,this.date,this.players,this.captain_id,this.success,this.used_clues_id,this.ellapsedTimeInSeconds,this.rewards_id);
    }

}
