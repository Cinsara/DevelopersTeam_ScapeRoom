package escapeRoom.Service.InputService;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.Model.GameArea.RoomBuilder.Room;
import escapeRoom.Model.PeopleArea.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static escapeRoom.Service.InputService.CustomerPicker.pickUsersByName;

public class InputCollector {
    private final InputService inputService;
    private final RoomService roomService;
    private final UserService userService;

    public InputCollector(InputService inputService,RoomService roomService, UserService userService){
        this.inputService = inputService;
        this.roomService = roomService;
        this.userService = userService;
    }

    public LocalDate getDate() throws BackToSecondaryMenuException{
        return inputService.readDate("Introduce the date of the game you are interested in. Use the following format [yyyy MM dd]","yyyy MM dd");
    }
    public Room getRoom() throws SQLException, BackToSecondaryMenuException {
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

    public User getTargetCostumer() throws SQLException, BackToSecondaryMenuException {
        List<User> users = userService.getAllEntities(ConnectionManager.getConnection());
        String nameForSearch = inputService.readString("Introduce the name and lastname of the customer of your interest. Leave this empty to see all customers in DB.");
        List<User> selectedUsers = nameForSearch.isEmpty() ? users : pickUsersByName(users,nameForSearch);
        StringBuilder listUsers = new StringBuilder();
        for (User user: selectedUsers){
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
