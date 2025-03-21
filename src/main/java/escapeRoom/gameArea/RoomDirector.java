package escapeRoom.gameArea;

import java.util.List;

public class RoomDirector {

    public void buildRoom(RoomBuilder roomBuilder, String name, Theme theme, Difficulty difficulty, List<Integer> clues_id, List<Integer> props_id){
        roomBuilder.setRoomName(name);
        roomBuilder.setRoomTheme(theme);
        roomBuilder.setRoomDifficulty(difficulty);
        roomBuilder.setRoomClues(clues_id);
        roomBuilder.setRoomProps(props_id);
    }

}
