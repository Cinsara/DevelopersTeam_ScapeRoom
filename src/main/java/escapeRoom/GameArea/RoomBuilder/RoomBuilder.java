package escapeRoom.GameArea.RoomBuilder;

import java.util.List;

public class RoomBuilder implements Builder<Room>{

    private int id;
    private String name;
    private Theme theme;
    private Difficulty difficulty;
    private List<Integer> clues_id;
    private List<Integer> props_id;


    @Override
    public Builder<Room> setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public Builder<Room> setRoomName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public Builder<Room> setRoomTheme(Theme theme) {
        this.theme = theme;
        return this;
    }

    @Override
    public Builder<Room> setRoomDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    @Override
    public Builder<Room>  setRoomClues(List<Integer> clues_id) {
        this.clues_id = clues_id;
        return this;
    }

    @Override
    public Builder<Room>  setRoomProps(List<Integer> props_id) {
        this.props_id = props_id;
        return this;
    }
    @Override
    public Room build() {
        return new Room(this.id,this.name,this.theme,this.difficulty,this.clues_id,this.props_id);
    }
}
