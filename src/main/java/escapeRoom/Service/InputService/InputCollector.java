package escapeRoom.Service.InputService;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.model.GameArea.RoomBuilder.Room;
import escapeRoom.model.PeopleArea.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class InputCollector {
    static InputService inputService = new InputService(new Scanner(System.in));

    static public LocalDate getDate() {
        return inputService.readDate("Introduce the date of the game you are interested in","yyyy MM dd");
    }
    static public Room getRoom() throws SQLException {
        RoomService roomService = new RoomService();
        List<Room> rooms = roomService.getAllEntities(ConnectionManager.getConnection());
        StringBuilder listRooms = new StringBuilder();
        for (Room room: rooms){
            listRooms.append("Room Number: ").append(room.getId()).append(" - Room Name: ").append(room.getName()).append(" - Room Theme: ").append(room.getTheme()).append(" - Room Difficulty: ").append(room.getDifficulty()).append("\n");
        }
        AtomicInteger roomId = new AtomicInteger(inputService.readInt("Introduce the number of the room you want to play in :\n"+listRooms));
        Optional<Room> potentialRoom = rooms.stream().filter(room -> room.getId()==roomId.get()).findFirst();
        while (potentialRoom.isEmpty()){
            roomId.set(inputService.readInt("Let's do it again. Make sure to chose the number of a room that exists :\n"+listRooms));
        }
        return potentialRoom.get();
    }

    static public User getTargetCostumer() throws SQLException {
        UserService userService = new UserService();
        List<User> users = userService.getAllEntities(ConnectionManager.getConnection());
        StringBuilder listUsers = new StringBuilder();
        for (User user: users){
            listUsers.append("User ID: ").append(user.getId()).append(" // ").append(user.getName()).append(" ").append(user.getLastname()).append("\n");
        }
        AtomicInteger returnedValue = new AtomicInteger(inputService.readInt("Introduce the ID of the customer your are interested in :\n"+listUsers));
        Optional<User> potentialUser = users.stream().filter(user -> user.getId()== returnedValue.get()).findFirst();
        while(potentialUser.isEmpty()){
            returnedValue.set(inputService.readInt("Let's do it again. Make sure to introduce the ID of an existing customer:\n"+listUsers));
        }
        return potentialUser.get();
    }


}
