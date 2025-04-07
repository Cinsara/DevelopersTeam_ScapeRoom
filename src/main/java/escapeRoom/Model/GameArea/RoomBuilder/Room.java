package escapeRoom.Model.GameArea.RoomBuilder;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private int id;
    private String name;
    private String theme;
    private Difficulty difficulty;
    private List<Integer> clues_id;
    private List<Integer> props_id;

    public Room(int id,String name, String theme, Difficulty difficulty, List<Integer> clues_id, List<Integer> props_id) {
        this.id = id;
        this.name = name;
        this.theme = theme;
        this.difficulty = difficulty;
        this.clues_id = clues_id == null ? new ArrayList<>() : new ArrayList<>(clues_id);
        this.props_id = props_id == null ? new ArrayList<>() : new ArrayList<>(props_id);
    }

    public Room(String name, String theme, Difficulty difficulty, List<Integer> clues_id, List<Integer> props_id) {
        this.name = name;
        this.theme = theme;
        this.difficulty = difficulty;
        this.clues_id = clues_id == null ? new ArrayList<>() : new ArrayList<>(clues_id);
        this.props_id = props_id == null ? new ArrayList<>() : new ArrayList<>(props_id);
    }

    public Room(String name, String theme, Difficulty difficulty) {
        this.name = name;
        this.theme = theme;
        this.difficulty = difficulty;
    }


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }
    public void setTheme(String theme) {
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
        return "Room ID: " + id +
                ", Name: " + name +
                ", Theme: " + theme +
                ", Difficulty: " + difficulty +
                ", Clues IDs: " + clues_id +
                ", Props IDs: " + props_id ;
    }
}