package escapeRoom.gameArea.GameBuilder;

import java.time.LocalDate;


public class GameBuilder {
    public static Game createGame(int room_id, LocalDate date){
        return new Game(room_id,date);
    }
}