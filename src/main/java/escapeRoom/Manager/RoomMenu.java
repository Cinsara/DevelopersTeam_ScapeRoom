package escapeRoom.Manager;

import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.RoomService.RoomService;

import java.sql.SQLException;

public class RoomMenu {

    private RoomManager roomManager;
    private InputService inputService;
    private GeneralMenu generalMenu;


    public RoomMenu(InputService inputService, GeneralMenu generalMenu) throws SQLException {
        this.roomManager = new RoomManager();
        this.inputService = inputService;
        this.generalMenu = generalMenu;
        RoomService roomService = new RoomService();
    }

    public int principalRoomMenu(){
        String menu = """
                ------
                Room menu:
                ------
                1. Create Room
                2. Show all Rooms
                3. Update Room
                4. Delete Room
                0. Back to the main menu.
                ------""";
        return inputService.readInt(menu);
    }

    public void startRoomMenu() throws SQLException {
        int option;
        do {
            option = principalRoomMenu();
            switch(option){
                case 1 -> roomManager.createRoom();
                case 2 -> roomManager.getAllRooms();
                case 3 -> roomManager.updateRoom();
                case 4 -> roomManager.deleteRoom();
                case 0 -> {
                    System.out.println("Returning to the main menu.");
                    generalMenu.startGeneralMenu();
//                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while(option!=0);
    }
}