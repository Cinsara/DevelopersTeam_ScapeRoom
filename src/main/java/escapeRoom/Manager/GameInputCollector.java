package escapeRoom.Manager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.model.GameArea.RoomBuilder.Room;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class GameInputCollector {
    static InputService inputService = new InputService(new Scanner(System.in));

    static public GameCoordinates getGameCoordinates() throws SQLException {
        LocalDate date = inputService.readDate("Introduce the date of the game you are interested in","YYYY MM DD");
        RoomService roomService = new RoomService();
        List<Room> rooms = roomService.getAllEntities(ConnectionManager.getConnection());
        String listrooms = "";
        for (Room room: rooms){
            listrooms += "Room Number: " + room.getId() + " - Room Name: " + room.getName() + " - Room Theme: " + room.getTheme().name() + " - Room Difficulty: " + room.getDifficulty() + "\n";
        }
        int roomId = inputService.readInt("Introduce the number of the room you want to play in \n"+listrooms);
        return new GameCoordinates(date,roomId);
    }

}
