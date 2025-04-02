package escapeRoom.Controller;

import escapeRoom.Controller.GameController.GameMenu;
import escapeRoom.Controller.NotificationManager.NotificationMenu;
import escapeRoom.Controller.RewardController.RewardController;
import escapeRoom.Controller.TicketController.TicketMenu;
import escapeRoom.Controller.UserManager.UserMenu;
import escapeRoom.Service.InputService.InputService;

import java.util.Scanner;

public class GeneralMenu {
    private InputService inputService;
    private TicketMenu ticketMenu;
    private GameMenu  gameMenu;
    private RewardController rewardController;
    private UserMenu userMenu;
    private NotificationMenu notificationMenu;

    public GeneralMenu(){
        this.inputService = new InputService(new Scanner(System.in));
        this.ticketMenu = new TicketMenu(this.inputService,this);
        this.gameMenu = new GameMenu(this.inputService, this);
        this.rewardController = new RewardController();
        this.userMenu = new UserMenu(this.inputService,this);
        this.notificationMenu = new NotificationMenu(this.inputService,this);
    }

    public int principalGeneralMenu(){
        String menu = """
                ------
                General menu:
                ------
                1. Game related operations
                2. Sale Inventory
                3. Rewards Inventory
                4. User related operations
                5. Notification Center
                0. Exit.
                ------""";
        return inputService.readInt(menu);
    }
    public void startGeneralMenu(){
        int option;
        do {
            option = principalGeneralMenu();
            switch(option){
                case 1 -> gameMenu.startGameMenu();
                case 2 -> ticketMenu.startticketMenu();
                case 3 -> rewardController.showAllRewards();
                case 4 -> userMenu.startUserMenu();
                case 5 -> notificationMenu.startNotificationMenu();
                case 0 -> System.out.println("Bye bye!");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while(option != 0);
    }
}
