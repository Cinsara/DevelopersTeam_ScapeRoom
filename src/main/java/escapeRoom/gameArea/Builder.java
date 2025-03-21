package escapeRoom.gameArea;

import java.util.List;

public interface Builder {

    void setRoomName(String name);
    void setRoomTheme(Theme theme);
    void setRoomDifficulty (Difficulty difficulty);
    void setRoomClues (List<Integer> clues_id);
    void setRoomProps (List<Integer> props_id);


}
