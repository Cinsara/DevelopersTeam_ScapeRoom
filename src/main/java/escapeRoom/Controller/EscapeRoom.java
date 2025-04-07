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
import escapeRoom.EscapeRoomInitializer;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.InputService.InputServiceManager;

import java.sql.Connection;
import java.sql.SQLException;

public class EscapeRoom {
    private EscapeRoomInitializer initializer;

    public EscapeRoom(EscapeRoomInitializer initializer) {
        this.initializer = initializer;
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
        return initializer.getInputService().readInt(menu);
    }
    public void startEscapeRoom() {
        int option;
        do {
            option = principalGeneralMenu();
            switch(option){
                case 1 -> initializer.getGameMenu().startGameMenu();
                case 2 -> initializer.getTicketMenu().startticketMenu();
                case 3 -> initializer.getRewardController().showAllRewards();
                case 4 -> initializer.getUserMenu().startUserMenu();
                case 5 -> initializer.getNotificationMenu().startNotificationMenu();
                case 6 -> initializer.getCertificateMenu().startCertificationMenu();
                case 7 -> initializer.getRoomMenu().startRoomMenu();
                case 8 -> initializer.getInventoryController().showInventory();
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
