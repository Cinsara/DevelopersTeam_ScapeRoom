package escapeRoom.model.GameArea.RoomBuilder;

import java.util.List;

public interface Builder<T> {

    Builder<T> setId (int id);
    Builder<T> setRoomName(String name);
    Builder<T> setRoomTheme(String theme);
    Builder<T> setRoomDifficulty (Difficulty difficulty);
//    Builder<T> setRoomClues (List<Integer> clues_id);
//    Builder<T> setRoomProps (List<Integer> props_id);
    T build();
}
