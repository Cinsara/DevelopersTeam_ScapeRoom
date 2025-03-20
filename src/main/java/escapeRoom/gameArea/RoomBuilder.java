package escapeRoom.gameArea;

import java.util.List;

public class RoomBuilder implements Builder{

    private int _id;
    private Theme theme;
    private Difficulty difficulty;
    private List<Integer> clues_id;
    private List<Integer> props_id;

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

    public Room getResult() {
        return new Room(theme,difficulty,clues_id,props_id);
    }
}
