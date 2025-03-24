package escapeRoom.gameArea.RoomBuilder;

import escapeRoom.gameArea.CluePropFactory.Prop;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private String name;
    private int _id;
    private final Theme theme;
    private Difficulty difficulty;
    private List<Integer> clues_id;
    private List<Integer> props_id;

    public Room(String name, Theme theme, Difficulty difficulty, List<Integer> clues_id, List<Integer> props_id) {
        this.name = name;
        this.theme = theme;
        this.difficulty = difficulty;
        this.clues_id = new ArrayList<>(clues_id);
        this.props_id = props_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int get_id() {
        return _id;
    }

    public Theme getTheme() {
        return theme;
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

    public void addClue(int clue_id){
        this.clues_id.add(clue_id);
    }
    public void removeClue(int clue_id){
        this.clues_id.remove((Object) clue_id);
    }

    public List<Integer> getProps_id() {
        return props_id;
    }

    public void setProps_id(List<Integer> props_id) {
        this.props_id = props_id;
    }

    public void addProp(int prop_id){
        this.props_id.add(prop_id);
    }
    public void removeProp(int prop_id){
        this.props_id.remove(prop_id);
    }

    @Override
    public String toString() {
        return "Room{" +
                "_id=" + _id +
                ", name=" + name +
                ", theme=" + theme +
                ", difficulty=" + difficulty +
                ", clues_id=" + clues_id +
                ", props_id=" + props_id +
                '}';
    }
}
