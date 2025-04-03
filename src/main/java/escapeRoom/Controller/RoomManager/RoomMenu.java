package escapeRoom.Controller.RoomManager;

import escapeRoom.Controller.InventoryController.InventoryController;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.RoomService.RoomService;

import java.sql.SQLException;

public class RoomMenu {

    private RoomManager roomManager;
    private InputService inputService;


    public RoomMenu(InputService inputService)  {
        this.inputService = inputService;
        try{
            this.roomManager = new RoomManager(this.inputService);

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

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

    public void startRoomMenu() {
        int option;
        do {
            option = principalRoomMenu();
            switch(option){
                case 1 -> roomManager.createRoom();
                case 2 -> new InventoryController().showInventory();
                case 3 -> roomManager.updateRoom();
                case 4 -> roomManager.deleteRoom();
                case 0 -> {
                    System.out.println("Returning to the main menu.");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while(true);
    }
}