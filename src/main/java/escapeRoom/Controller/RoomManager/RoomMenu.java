package escapeRoom.Controller.RoomManager;

import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.OutPutService.TablePrinter;

public class RoomMenu {

    private RoomManager roomManager;
    private InputService inputService;


    public RoomMenu(InputService inputService, RoomManager roomManager) {

        this.inputService = inputService;
        this.roomManager = roomManager;


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
                case 2 -> System.out.println(TablePrinter.buildTable(roomManager.getAllRooms()));
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