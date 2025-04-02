package escapeRoom.Controller;

import escapeRoom.Controller.GameController.GameMenu;
import escapeRoom.Controller.TicketController.TicketMenu;
import escapeRoom.Service.InputService.InputService;

import java.util.Scanner;

public class GeneralMenu {
    private InputService inputService;
    private TicketMenu ticketMenu;
    private GameMenu  gameMenu;

    public GeneralMenu(){
        this.inputService = new InputService(new Scanner(System.in));
        this.ticketMenu = new TicketMenu(this.inputService,this);
        this.gameMenu = new GameMenu(this.inputService, this);
    }

    public int principalTicketMenu(){
        String menu = """
                ------
                General menu:
                ------
                1. Game related operations
                2. Sale Inventory
                0. Exit.
                ------""";
        return inputService.readInt(menu);
    }
    public void startGeneralMenu(){
        int option;
        do {
            option = principalTicketMenu();
            switch(option){
                case 1 -> gameMenu.startGameMenu();
                case 2 -> ticketMenu.startticketMenu();
                case 0 -> System.out.println("Bye bye!");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while(option != 0);
    }
}
