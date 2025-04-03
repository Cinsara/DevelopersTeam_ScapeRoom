package escapeRoom.Manager;


import escapeRoom.Service.InputService.InputService;

import java.sql.SQLException;

public class PropMenu {

    private PropManager propManager;
    private InputService inputService;
    private GeneralMenu generalMenu;

    public PropMenu(InputService inputService, GeneralMenu generalMenu) throws SQLException {
        this.propManager = new PropManager();
        this.inputService = inputService;
        this.generalMenu = generalMenu;
    }

    public int principalRoomMenu(){
        String menu = """
                ------
                Prop menu:
                ------
                1. Create Prop
                2. Show all Props
                3. Update Prop
                4. Delete Prop
                0. Back to the main menu.
                ------""";
        return inputService.readInt(menu);
    }

    public void startGameMenu() throws SQLException {
        int option;
        do {
            option = principalRoomMenu();
            switch(option){
                case 1 -> propManager.create();
                case 2 -> propManager.getAllProps();
                case 3 -> propManager.update();
                case 4 -> propManager.delete();
                case 0 -> {
                    System.out.println("Returning to the main menu.");
                    generalMenu.startGeneralMenu();
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while(true);
    }
}