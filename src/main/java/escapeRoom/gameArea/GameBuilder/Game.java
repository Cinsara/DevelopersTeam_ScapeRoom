package escapeRoom.gameArea.GameBuilder;

import escapeRoom.gameArea.RoomBuilder.Room;

import java.time.LocalDate;
import java.util.List;

public class Game {
    private int _id;
    private final String room_id;
    private final LocalDate date;
    private List<Integer> players_id;
    private int captain_id;
    private boolean success;
    private List<String> used_cues_id;
    private int ellapsedTimeInSeconds;
    private List<String> rewards_id;

    public Game(String room_id, LocalDate date) {
        this.room_id = room_id;
        this.date = date;
    }
    public void addPlayer(int player_id){
        this.players_id.add(player_id);
    }
    public void calculateResult(){
        this.success = Math.random() > 0.5;
        this.ellapsedTimeInSeconds = (int) Math.floor(Math.random()*7200);

    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setPlayers(List<Integer> players_id) {
        this.players_id = players_id;
    }

    public void setCaptain(int captain_id) {
        this.captain_id = captain_id;
    }

    public int get_id() {
        return _id;
    }

    public List<Integer> getPlayers_id() {
        return players_id;
    }

    public int getCaptain_id() {
        return captain_id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<String> getUsed_cues_id() {
        return used_cues_id;
    }

    public int getEllapsedTimeInSeconds() {
        return ellapsedTimeInSeconds;
    }

    public List<String> getRewards_id() {
        return rewards_id;
    }
}
