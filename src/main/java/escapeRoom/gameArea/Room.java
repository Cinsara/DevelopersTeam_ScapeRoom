package escapeRoom.gameArea;

import java.util.List;

public class Room {

    private static int counter = 0; // Shared counter for all instances
    private final int _id;
    private Theme theme;
    private Difficulty difficulty;
    private List<Integer> clues_id;
    private List<Integer> props_id;

    public Room(Theme theme, Difficulty difficulty, List<Integer> clues_id, List<Integer> props_id) {
        this._id = ++counter;
        this.theme = theme;
        this.difficulty = difficulty;
        this.clues_id = clues_id;
        this.props_id = props_id;
    }

    public int get_id() {
        return _id;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public List<Integer> getClues_id() {
        return clues_id;
    }

    public void setClues_id(List<Integer> clues_id) {
        this.clues_id = clues_id;
    }

    public List<Integer> getProps_id() {
        return props_id;
    }

    public void setProps_id(List<Integer> props_id) {
        this.props_id = props_id;
    }

    @Override
    public String toString() {
        return "Room{" +
                "_id=" + _id +
                ", theme=" + theme +
                ", difficulty=" + difficulty +
                ", clues_id=" + clues_id +
                ", props_id=" + props_id +
                '}';
    }
}
