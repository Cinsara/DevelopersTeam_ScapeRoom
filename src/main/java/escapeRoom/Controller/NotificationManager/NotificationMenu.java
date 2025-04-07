package escapeRoom.Controller.NotificationManager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Controller.GeneralMenu;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.NotificationService.NotificationService;
import escapeRoom.Service.PeopleService.UserService;

import java.sql.SQLException;

public class NotificationMenu {
    private NotificationManager notificationManager;

    public NotificationMenu(InputService inputService) {
        try{
            this.notificationManager = new NotificationManager(new NotificationService(), ConnectionManager.getConnection(), inputService, new UserService());
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void principalNotificationMenu(){
        String menu = """
                ------
                Notification menu:
                ------
                1. Create notification.
                2. Delete notification.
                0. Back to the main menu.
                ------
                """;
        System.out.println(menu);
    }

    public void startNotificationMenu(){
        int option;
        do {
            principalNotificationMenu();
            option = notificationManager.selectOptionMenu();

            switch (option) {
                case 1 -> notificationManager.createNotification();
                case 2 -> notificationManager.deleteNotification();
                case 0 -> {
                    System.out.println("Returning to the main menu.");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while(true);
    }
}
