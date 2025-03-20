package escapeRoom.gameArea;

import java.util.List;

public class RoomDirector {

    public void buildRoomLoveAffair(RoomBuilder roomBuilder, List<Integer> clues_id, List<Integer> props_id){
        roomBuilder.setRoomTheme(Theme.LOVEAFFAIR);
        roomBuilder.setRoomDifficulty(Difficulty.EASY);
        roomBuilder.setRoomClues(clues_id);
        roomBuilder.setRoomProps(props_id);
    }

}
