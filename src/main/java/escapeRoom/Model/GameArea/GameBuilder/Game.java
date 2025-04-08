package escapeRoom.Model.GameArea.GameBuilder;

import escapeRoom.Model.AssetsArea.AssetBuilder.AssetFactory;
import escapeRoom.Model.AssetsArea.AssetBuilder.AssetType;
import escapeRoom.Model.AssetsArea.RewardBuilder.Reward;
import escapeRoom.Model.GameArea.CluePropFactory.Clue;
import escapeRoom.Model.PeopleArea.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {
    private int id;
    private final int room_id;
    private final LocalDate date;
    private List<User> players = new ArrayList<>();
    private Integer captainId;
    private boolean success;
    private List<Clue> usedClues = new ArrayList<>();;
    private int ellapsedTimeInSeconds;
    private List<Reward> rewardsGiven = new ArrayList<>();;


    public Game(int room_id, LocalDate date) {
        this.room_id = room_id;
        this.date = date;
    }

    public Game(int id, int room_id, LocalDate date, List<User> players, Integer captainId, boolean success, List<Clue> usedClues, int ellapsedTimeInSeconds, List<Reward> rewardsGiven) {
        this.id = id;
        this.room_id = room_id;
        this.date = date;
        this.players = new ArrayList<>(players);
        this.captainId = captainId;
        this.success = success;
        this.usedClues = new ArrayList<>(usedClues);
        this.ellapsedTimeInSeconds = ellapsedTimeInSeconds;
        this.rewardsGiven = new ArrayList<>(rewardsGiven);
    }

    public void addPlayer(User player){
        // Does the player exist?
        this.players.add(player);
    }
    public void removePlayer(User player){
        this.players.remove(player);
    }

    public void calculateResult(List<Clue> availableClues){
        AssetFactory factory = new AssetFactory();
        this.success = Math.random() > 0.5;
        this.ellapsedTimeInSeconds = (int) Math.floor(Math.random()*7200);
        availableClues.forEach(clue-> {
            if (Math.random()>0.66) {
                this.usedClues.add(clue);
            }
        });
        this.players.forEach((player)-> {
            if (Math.random()>0.85) {
                this.rewardsGiven.add((Reward) factory.createAsset(AssetType.REWARD,player.getId(),this.id));
            }
        });
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlayers(List<User> players) {
        this.players = new ArrayList<>(players);
    }

    public void setCaptain(User captain) {
        if (captain != null) this.captainId = captain.getId();
        else this.captainId = null;
        if (!this.players.contains(captain)){
            this.addPlayer(captain);
        }
    }

    public int getId() {
        return id;
    }

    public List<User> getPlayers() {
        return players;
    }

    public Integer getCaptainId() {
        return captainId;
    }

    public int getRoom_id() {
        return room_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<Clue> getUsedClues() {
        return usedClues;
    }

    public int getEllapsedTimeInSeconds() {
        return ellapsedTimeInSeconds;
    }

    public List<Reward> getRewardsGiven() {
        return rewardsGiven;
    }

    public void setEllapsedTimeInSeconds(int ellapsedTimeInSeconds) {
        this.ellapsedTimeInSeconds = ellapsedTimeInSeconds;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setUsedClues(List<Clue> usedClues) {
        this.usedClues = new ArrayList<>(usedClues);
    }

    public void setRewardsGiven(List<Reward> rewardsGiven) {
        this.rewardsGiven = new ArrayList<>(rewardsGiven);
    }

    public void selfDescribe(){
        StringBuilder description = new StringBuilder();
        description.append("Game number ").append(this.id)
                .append(" //Date: ").append(this.date)
                .append(" //Room: ").append(this.room_id)
                .append(" // Booking Status: ").append(this.captainId ==0? "available":"booked")
                .append(" // Result: ").append(this.isSuccess()? "success":"failure")
                .append(" // Clues used: ").append(this.usedClues.size())
                .append(" // Rewards given: ").append(this.rewardsGiven.size())
                .append(" // Size team: ").append(this.players.size());
        System.out.println(description);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return room_id == game.room_id && Objects.equals(date, game.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room_id, date);
    }

    @Override
    public String toString() {
        return "Game{" +
                "_id=" + id +
                ", room_id=" + room_id +
                ", date=" + date +
                ", players=" + players+
                ", captainId=" + captainId +
                ", success=" + success +
                ", used_clues_id=" + usedClues +
                ", ellapsedTimeInSeconds=" + ellapsedTimeInSeconds +
                ", rewards_id=" + rewardsGiven +
                '}';
    }
}
