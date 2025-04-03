package escapeRoom.model.GameArea.GameBuilder;

import escapeRoom.model.PeopleArea.User;

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
    private List<Integer> used_clues_id = new ArrayList<>();;
    private int ellapsedTimeInSeconds;
    private List<Integer> rewards_id = new ArrayList<>();;


    public Game(int room_id, LocalDate date) {
        this.room_id = room_id;
        this.date = date;
    }

    public Game(int id, int room_id, LocalDate date, List<User> players, Integer captainId, boolean success, List<Integer> used_clues_id, int ellapsedTimeInSeconds, List<Integer> rewards_id) {
        this.id = id;
        this.room_id = room_id;
        this.date = date;
        this.players = new ArrayList<>(players);
        this.captainId = captainId;
        this.success = success;
        this.used_clues_id = new ArrayList<>(used_clues_id);
        this.ellapsedTimeInSeconds = ellapsedTimeInSeconds;
        this.rewards_id = new ArrayList<>(rewards_id);
    }

    public void addPlayer(User player){
        // Does the player exist?
        this.players.add(player);
    }
    public void removePlayer(User player){
        this.players.remove(player);
    }

    public void calculateResult(List<Integer> available_clues){
        this.success = Math.random() > 0.5;
        this.ellapsedTimeInSeconds = (int) Math.floor(Math.random()*7200);
        available_clues.forEach(clue-> {
            if (Math.random()>0.66) {
                this.used_clues_id.add(clue);
            }
        });
        this.players.forEach((player)-> {
            if (Math.random()>0.85) {
                this.rewards_id.add(player.getId());
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
        else this.captainId = 0;
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

    public List<Integer> getUsed_clues_id() {
        return used_clues_id;
    }

    public int getEllapsedTimeInSeconds() {
        return ellapsedTimeInSeconds;
    }

    public List<Integer> getRewards_id() {
        return rewards_id;
    }

    public void setEllapsedTimeInSeconds(int ellapsedTimeInSeconds) {
        this.ellapsedTimeInSeconds = ellapsedTimeInSeconds;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setUsed_clues_id(List<Integer> used_clues_id) {
        this.used_clues_id = new ArrayList<>(used_clues_id);
    }

    public void setRewards_id(List<Integer> rewards_id) {
        this.rewards_id = new ArrayList<>(rewards_id);
    }

    public void selfDescribe(){
        StringBuilder description = new StringBuilder();
        description.append("Game number ").append(this.id)
                .append(" //Date: ").append(this.date)
                .append(" //Room: ").append(this.room_id)
                .append(" // Booking Status: ").append(this.captainId ==0? "available":"booked")
                .append(" // Result: ").append(this.isSuccess()? "success":"failure")
                .append(" // Clues used: ").append(this.used_clues_id.size())
                .append(" // Rewards given: ").append(this.rewards_id.size())
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
                ", used_clues_id=" + used_clues_id +
                ", ellapsedTimeInSeconds=" + ellapsedTimeInSeconds +
                ", rewards_id=" + rewards_id +
                '}';
    }
}
