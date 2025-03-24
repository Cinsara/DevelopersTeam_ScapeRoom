package escapeRoom.gameArea.GameBuilder;

import escapeRoom.gameArea.RoomBuilder.Room;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {
    private int _id;
    private final int room_id;
    private final LocalDate date;
    private List<Integer> players_id = new ArrayList<>();
    private int captain_id;
    private boolean success;
    private List<Integer> used_clues_id = new ArrayList<>();;
    private int ellapsedTimeInSeconds;
    private List<Integer> rewards_id = new ArrayList<>();;


    public Game(int room_id, LocalDate date) {
        this.room_id = room_id;
        this.date = date;
    }
    public void addPlayer(int player_id){
        // Does the player exist?
        this.players_id.add(player_id);
    }

    public void calculateResult(){
        this.success = Math.random() > 0.5;
        this.ellapsedTimeInSeconds = (int) Math.floor(Math.random()*7200);

        //Hardcode --later to be recovered from DB
        List<Integer> available_clues = List.of(1,5,6);
        available_clues.forEach(clue-> {
            if (Math.random()>0.66) {
                this.used_clues_id.add(clue);
            }
        });
        //Instead of simply adding rewards -- here we need to create rewards
        this.players_id.forEach((player)-> {
            if (Math.random()>0.85) {
                this.rewards_id.add(player);
            }
        });
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setPlayers(List<Integer> players_id) {
        this.players_id = players_id;
    }

    public void setCaptain(int captain_id) {
        this.captain_id = captain_id;
        if (!this.players_id.contains(captain_id)){
            this.addPlayer(captain_id);
        }
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
                "_id=" + _id +
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
