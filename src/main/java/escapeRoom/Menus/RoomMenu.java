package escapeRoom.Menus;

import escapeRoom.Controller.RoomManager.RoomManager;
import escapeRoom.Service.InputService.BackToSecondaryMenuException;
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
        try{
            return inputService.readInt(menu);
        } catch (BackToSecondaryMenuException e) {
            return 0;
        }
    }

    public void startRoomMenu() {
        int option;
        do {
            try{
                option = principalRoomMenu();
                switch(option){
                    case 1 -> roomManager.createRoom();
                    case 2 -> System.out.println(TablePrinter.buildTable(roomManager.prepPrintableRooms().get(2),false));
                    case 3 -> roomManager.updateRoom();
                    case 4 -> roomManager.deleteRoom();
                    case 0 -> {
                        System.out.println("Returning to the main menu.");
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }catch(BackToSecondaryMenuException e){}

        } while(true);
    }
}