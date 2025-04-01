package escapeRoom.model.GameArea.GameBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {
    private int id;
    private final int room_id;
    private final LocalDate date;
    private List<Integer> players_id = new ArrayList<>();
    private Integer captain_id;
    private boolean success;
    private List<Integer> used_clues_id = new ArrayList<>();;
    private int ellapsedTimeInSeconds;
    private List<Integer> rewards_id = new ArrayList<>();;


    public Game(int room_id, LocalDate date) {
        this.room_id = room_id;
        this.date = date;
    }

    public Game(int id, int room_id, LocalDate date, List<Integer> players_id, Integer captain_id, boolean success, List<Integer> used_clues_id, int ellapsedTimeInSeconds, List<Integer> rewards_id) {
        this.id = id;
        this.room_id = room_id;
        this.date = date;
        this.players_id = new ArrayList<>(players_id);
        this.captain_id = captain_id;
        this.success = success;
        this.used_clues_id = new ArrayList<>(used_clues_id);
        this.ellapsedTimeInSeconds = ellapsedTimeInSeconds;
        this.rewards_id = new ArrayList<>(rewards_id);
    }

    public void addPlayer(int player_id){
        // Does the player exist?
        this.players_id.add(player_id);
    }
    public void removePlayer(int player_id){
        this.players_id.remove((Object) player_id);
    }

    public void calculateResult(List<Integer> available_clues){
        this.success = Math.random() > 0.5;
        this.ellapsedTimeInSeconds = (int) Math.floor(Math.random()*7200);
        available_clues.forEach(clue-> {
            if (Math.random()>0.66) {
                this.used_clues_id.add(clue);
            }
        });
        this.players_id.forEach((player)-> {
            if (Math.random()>0.85) {
                this.rewards_id.add(player);
            }
        });
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlayers(List<Integer> players_id) {
        this.players_id = new ArrayList<>(players_id);
    }

    public void setCaptain(int captain_id) {
        this.captain_id = captain_id;
        if (!this.players_id.contains(captain_id)){
            this.addPlayer(captain_id);
        }
    }

    public int getId() {
        return id;
    }

    public List<Integer> getPlayers_id() {
        return players_id;
    }

    public Integer getCaptain_id() {
        return captain_id;
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
                ", players_id=" + players_id +
                ", captain_id=" + captain_id +
                ", success=" + success +
                ", used_clues_id=" + used_clues_id +
                ", ellapsedTimeInSeconds=" + ellapsedTimeInSeconds +
                ", rewards_id=" + rewards_id +
                '}';
    }
}
