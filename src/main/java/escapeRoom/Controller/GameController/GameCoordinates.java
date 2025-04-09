package escapeRoom.Controller.GameController;

import escapeRoom.Model.GameArea.RoomBuilder.Room;
import escapeRoom.Model.PeopleArea.User;
import escapeRoom.Service.InputService.BackToSecondaryMenuException;
import escapeRoom.Service.InputService.InputCollector;

import java.sql.SQLException;
import java.time.LocalDate;

public class GameCoordinates {
    private InputCollector inputCollector;
    public LocalDate gameDate;
    public Room gameRoom;
    public User captain;

    private GameCoordinates(InputCollector inputCollector){
        this.inputCollector = inputCollector;
    }

    public GameCoordinates(LocalDate gameDate, Room gameRoom, User captain) {
        this.gameDate = gameDate;
        this.gameRoom = gameRoom;
        this.captain = captain;
        this.inputCollector = null;
    }

    GameCoordinates getGameCoordinates() throws BackToSecondaryMenuException, SQLException {
        this.gameDate = inputCollector.getDate();
        this.gameRoom = inputCollector.getRoom();
        return this;
    }
    GameCoordinates getGameFullCoordinates() throws BackToSecondaryMenuException, SQLException {
        this.gameDate = inputCollector.getDate();
        this.gameRoom = inputCollector.getRoom();
        this.captain = inputCollector.getTargetCostumer();
        return this;
    }

    static GameCoordinates getFullCoordinates(InputCollector inputCollector) throws SQLException, BackToSecondaryMenuException {
        return new GameCoordinates(inputCollector).getGameFullCoordinates();
    }
    static GameCoordinates getPartialCoordinates(InputCollector inputCollector) throws SQLException, BackToSecondaryMenuException {
        return new GameCoordinates(inputCollector).getGameCoordinates();
    }

    static GameCoordinates createCoordinate(LocalDate gameDate, Room gameRoom, User gameCaptain){
        return new GameCoordinates(gameDate,gameRoom,gameCaptain);
    }


}
