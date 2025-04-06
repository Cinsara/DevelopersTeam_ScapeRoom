package escapeRoom.Controller;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Controller.CertificateManager.CertificateMenu;
import escapeRoom.Controller.GameController.GameMenu;
import escapeRoom.Controller.InventoryController.InventoryController;
import escapeRoom.Controller.NotificationManager.NotificationMenu;
import escapeRoom.Controller.RewardController.RewardController;
import escapeRoom.Controller.RoomManager.RoomMenu;
import escapeRoom.Controller.TicketController.TicketMenu;
import escapeRoom.Controller.UserManager.UserMenu;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.InputService.InputServiceManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class GeneralMenu {
    private InputService inputService;
    private TicketMenu ticketMenu;
    private GameMenu  gameMenu;
    private RewardController rewardController;
    private UserMenu userMenu;
    private NotificationMenu notificationMenu;
    private CertificateMenu certificateMenu;
    private InventoryController inventoryController;
    private RoomMenu roomMenu;

    public GeneralMenu(){
        this.inputService = InputServiceManager.getInputService();
        this.ticketMenu = new TicketMenu(this.inputService);
        this.gameMenu = new GameMenu(this.inputService);
        this.rewardController = new RewardController();
        this.userMenu = new UserMenu(this.inputService);
        this.notificationMenu = new NotificationMenu(this.inputService);
        this.certificateMenu = new CertificateMenu(this.inputService);
        this.inventoryController = new InventoryController();
        this.roomMenu = new RoomMenu(this.inputService);
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
                6. Certification Center
                7. Room related operations
                8. Inventory
                0. Exit.
                ------""";
        return inputService.readInt(menu);
    }
    public void startGeneralMenu() {
        int option;
        do {
            option = principalGeneralMenu();
            switch(option){
                case 1 -> gameMenu.startGameMenu();
                case 2 -> ticketMenu.startticketMenu();
                case 3 -> rewardController.showAllRewards();
                case 4 -> userMenu.startUserMenu();
                case 5 -> notificationMenu.startNotificationMenu();
                case 6 -> certificateMenu.startCertificationMenu();
                case 7 -> roomMenu.startRoomMenu();
                case 8 -> inventoryController.showInventory();
                case 0 -> {
                    System.out.println("Bye bye!");
                    try{
                        Connection connection = ConnectionManager.getConnection();
                        connection.close();

                    } catch (SQLException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while(option !=0);
    }
}
