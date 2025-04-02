package escapeRoom.Controller.GameController;

import java.time.LocalDate;

public class GameAlreadyPlayed extends Exception {
    public GameAlreadyPlayed(LocalDate gameDate, int roomId) {
        super("Game on " + gameDate + " in room "+ roomId + " has already been played.");
    }
}
