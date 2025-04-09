package escapeRoom.Menus;

import escapeRoom.Controller.GameController.GameController;
import escapeRoom.Service.InputService.BackToSecondaryMenuException;
import escapeRoom.Service.InputService.InputService;

public class GameMenu {
    private GameController gameController;
    private InputService inputService;

    public GameMenu(InputService inputService, GameController gameController){
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
                6. Show Booked Games
                7. Show Available Games
                0. Back to the main menu.
                ------""";
           try{
               return inputService.readInt(menu);
           } catch (BackToSecondaryMenuException e) {
               return 0;
           }
    }

    public void startGameMenu(){
        int option;
        do {
            try{
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
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }catch(BackToSecondaryMenuException e){}
        } while(true);
    }
}
