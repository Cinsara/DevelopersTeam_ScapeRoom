package escapeRoom.Manager;

import java.time.LocalDate;

public class GameNotAvailableException extends Exception{
    public GameNotAvailableException(LocalDate dateGame) {
        super("No game available for booking on " + dateGame +". Booking only availabe two weeks in advance.");
    }
    public GameNotAvailableException(LocalDate dateGame,int roomId){
        super("No game available for booking on "+ dateGame +" and in room number " +roomId);
    }
}
