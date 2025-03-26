package escapeRoom.AssetsArea_Classes.Classes_testing.Room_Classes;

import java.util.List;

public class RoomBuilder implements Builder{

    private int _id;
    private String name;
    private Theme theme;
    private Difficulty difficulty;
    private List<Integer> clues_id;
    private List<Integer> props_id;


    @Override
    public void setRoomName(String name) {
        this.name = name;
    }

    @Override
    public void setRoomTheme(Theme theme) {
        this.theme = theme;
    }

    @Override
    public void setRoomDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public void setRoomClues(List<Integer> clues_id) {
        this.clues_id = clues_id;
    }

    @Override
    public void setRoomProps(List<Integer> props_id) {
        this.props_id = props_id;
    }

    public Room create() {
        return new Room(this.name,theme,difficulty,clues_id,props_id);
    }
}
