package escapeRoom.Manager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.model.GameArea.RoomBuilder.Room;
import escapeRoom.model.PeopleArea.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class GameInputCollector {
    static InputService inputService = new InputService(new Scanner(System.in));

    static public GameCoordinates getGameCoordinates() throws SQLException {
        LocalDate date = inputService.readDate("Introduce the date of the game you are interested in","yyyy MM dd");
        RoomService roomService = new RoomService();
        List<Room> rooms = roomService.getAllEntities(ConnectionManager.getConnection());
        StringBuilder listrooms = new StringBuilder();
        for (Room room: rooms){
            listrooms.append("Room Number: ").append(room.getId()).append(" - Room Name: ").append(room.getName()).append(" - Room Theme: ").append(room.getTheme().name()).append(" - Room Difficulty: ").append(room.getDifficulty()).append("\n");
        }
        int roomId = inputService.readInt("Introduce the number of the room you want to play in :\n"+listrooms);
        GameCoordinates newCoordinates = new GameCoordinates(date,roomId);
        while (rooms.stream().filter(room -> room.getId()==newCoordinates.getRoomId()).toList().isEmpty()){
            newCoordinates.setRoomId(inputService.readInt("Let's do it again. Make sure to chose the number of a room that exists :\n"+listrooms));
        }
        return newCoordinates;
    }

    static public int getTargetCostumer() throws SQLException {
        UserService userService = new UserService();
        List<User> users = userService.getAllEntities(ConnectionManager.getConnection());
        StringBuilder listusers = new StringBuilder();
        for (User user: users){
            listusers.append("User ID: ").append(user.getId()).append(" // ").append(user.getName()).append(" ").append(user.getLastname()).append("\n");
        }

        AtomicInteger returnedValue = new AtomicInteger(inputService.readInt("Introduce the ID of the customer your are interested in :\n"+listusers));
        while(users.stream().filter(user -> user.getId()== returnedValue.get()).toList().isEmpty()){
            returnedValue.set(inputService.readInt("Let's do it again. Make sure to introduce the ID of an existing customer:\n"+listusers));
        }
        return returnedValue.get();
    }

}
