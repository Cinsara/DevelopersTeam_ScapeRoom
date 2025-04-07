package escapeRoom.Model.GameArea.GameBuilder;

import escapeRoom.Model.AssetsArea.RewardBuilder.Reward;
import escapeRoom.Model.GameArea.CluePropFactory.Clue;
import escapeRoom.Model.PeopleArea.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GameBuilder {

    private int id;
    private final int roomId;
    private final LocalDate date;
    private List<User> players = new ArrayList<>();
    private Integer captainId;
    private boolean success;
    private List<Clue> usedClues = new ArrayList<>();;
    private int ellapsedTimeInSeconds;
    private List<Reward> rewards = new ArrayList<>();;

    public GameBuilder(int roomId, LocalDate date) {
        this.roomId = roomId;
        this.date = date;
    }

    public GameBuilder setId(int id) {
        this.id = id;
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

    public GameBuilder setRewards(List<Reward> rewards) {
        this.rewards = rewards;
        return this;
    }

    public GameBuilder addReward(Reward reward){
        this.rewards.add(reward);
        return this;
    }

    public GameBuilder setCaptainId(Integer captainId) {
        this.captainId = captainId;
        return this;
    }

    public GameBuilder setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public GameBuilder setUsedClues(List<Clue> usedClues) {
        this.usedClues = usedClues;
        return this;
    }

    public GameBuilder setEllapsedTimeInSeconds(int ellapsedTimeInSeconds) {
        this.ellapsedTimeInSeconds = ellapsedTimeInSeconds;
        return this;
    }
    public Game build(){
        return new Game(this.id,this.roomId,this.date,this.players,this.captainId,this.success,this.usedClues,this.ellapsedTimeInSeconds,this.rewards);
    }

}
