package escapeRoom.Manager;

import escapeRoom.Service.InputService.InputService;

public class GameMenu {
    private GameController gameController;
    private InputService inputService;

    public GameMenu(GameController gameController, InputService inputService){
        this.gameController = gameController;
        this.inputService = inputService;
    }

    public int principalGameMenu(){
        String menu = """
                ------
                Game menu:
                ------
                1. Book Game
                2. Cancel Booking
                3. Add Player To Game
                4. Remove Player from Game
                5. Play Game
                0. Back to the main menu.
                ------""";
            return inputService.readInt(menu);
    }

    public void startGameMenu(){
        int option;
        do {
            option = principalGameMenu();
            switch(option){
                case 1 -> gameController.bookGame();
                case 2 -> System.out.println("In development");
                case 3 -> gameController.addPlayerToGame();
                case 4 -> gameController.removePlayerFromGame();
                case 5 -> gameController.playGame();
                case 0 -> System.out.println("Returning to the main menu.");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while(option != 0);
    }
}
