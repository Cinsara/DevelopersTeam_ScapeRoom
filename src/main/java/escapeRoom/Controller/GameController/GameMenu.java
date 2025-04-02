package escapeRoom.Controller.GameController;

import escapeRoom.Controller.GeneralMenu;
import escapeRoom.Service.InputService.InputService;

public class GameMenu {
    private GameController gameController;
    private InputService inputService;
    private GeneralMenu generalMenu;

    public GameMenu(InputService inputService, GeneralMenu generalMenu){
        this.gameController = new GameController();
        this.inputService = inputService;
        this.generalMenu = generalMenu;
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
                6. Show Booked Games
                7. Show Available Games
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
                case 2 -> gameController.cancelBooking();
                case 3 -> gameController.addPlayerToGame();
                case 4 -> gameController.removePlayerFromGame();
                case 5 -> gameController.playGame();
                case 6 -> gameController.showBookedGames();
                case 7 -> gameController.showAvailableGames();
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
