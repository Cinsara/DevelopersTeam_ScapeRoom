package escapeRoom.Controller.GameController;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.model.GameArea.RoomBuilder.Room;
import escapeRoom.model.PeopleArea.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class GameInputCollector {
    static InputService inputService = new InputService(new Scanner(System.in));

    static public LocalDate getGameDate() {
        return inputService.readDate("Introduce the date of the game you are interested in","yyyy MM dd");
    }
    static public int getGameRoomId() throws SQLException {
        RoomService roomService = new RoomService();
        List<Room> rooms = roomService.getAllEntities(ConnectionManager.getConnection());
        StringBuilder listRooms = new StringBuilder();
        for (Room room: rooms){
            listRooms.append("Room Number: ").append(room.getId()).append(" - Room Name: ").append(room.getName()).append(" - Room Theme: ").append(room.getTheme()).append(" - Room Difficulty: ").append(room.getDifficulty()).append("\n");
        }
        AtomicInteger roomId = new AtomicInteger(inputService.readInt("Introduce the number of the room you want to play in :\n"+listRooms));
        while (rooms.stream().filter(room -> room.getId()==roomId.get()).toList().isEmpty()){
            roomId.set(inputService.readInt("Let's do it again. Make sure to chose the number of a room that exists :\n"+listRooms));
        }
        return roomId.get();
    }

    static public int getTargetCostumer() throws SQLException {
        UserService userService = new UserService();
        List<User> users = userService.getAllEntities(ConnectionManager.getConnection());
        StringBuilder listUsers = new StringBuilder();
        for (User user: users){
            listUsers.append("User ID: ").append(user.getId()).append(" // ").append(user.getName()).append(" ").append(user.getLastname()).append("\n");
        }
        AtomicInteger returnedValue = new AtomicInteger(inputService.readInt("Introduce the ID of the customer your are interested in :\n"+listUsers));
        while(users.stream().filter(user -> user.getId()== returnedValue.get()).toList().isEmpty()){
            returnedValue.set(inputService.readInt("Let's do it again. Make sure to introduce the ID of an existing customer:\n"+listUsers));
        }
        return returnedValue.get();
    }

    static public int chooseGamesClassification(String bookedOrAvailable){
        String menuBasis = " ----\n Do you want to see \n ---- \n";
        StringBuilder listOptions = new StringBuilder();
        listOptions.append("1. All the ").append(bookedOrAvailable).append(" games\n").append("2. Only the ").append(bookedOrAvailable).append(" games on a given date\n").append("3. Only the ").append(bookedOrAvailable).append(" games in a given room.\n").append("4. Take me back!");
        int input;
        do {input = inputService.readInt(menuBasis + listOptions);} while(!new HashSet<Integer>(List.of(1, 2, 3, 4)).contains(input));
        return input;
    }
}
