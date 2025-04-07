package escapeRoom.Controller.RoomManager;


import escapeRoom.Service.InputService.InputService;

import java.sql.SQLException;

public class ClueMenu {
    private ClueManager clueManager;
    private InputService inputService;

    public ClueMenu(InputService inputService) throws SQLException {
        this.clueManager = new ClueManager();
        this.inputService = inputService;

    }

    public int principalRoomMenu(){
        String menu = """
                ------
                Clue menu:
                ------
                1. Create Clue
                2. Show all Clues
                3. Update Clue
                4. Delete Clue
                0. Back to the main menu.
                ------""";
        return inputService.readInt(menu);
    }

    public void startGameMenu() throws SQLException {
        int option;
        do {
            option = principalRoomMenu();
            switch(option){
                case 1 -> clueManager.create();
                case 2 -> clueManager.getAllClues();
                case 3 -> clueManager.update();
                case 4 -> clueManager.delete();
                case 0 -> {
                    System.out.println("Returning to the main menu.");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while(true);
    }
}