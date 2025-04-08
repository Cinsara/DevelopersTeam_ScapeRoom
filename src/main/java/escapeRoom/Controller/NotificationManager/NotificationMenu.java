package escapeRoom.Controller.NotificationManager;

import escapeRoom.Service.InputService.InputService;

public class NotificationMenu {
    private NotificationManager notificationManager;
    private InputService inputService;

    public NotificationMenu(InputService inputService, NotificationManager notificationManager) {
        this.inputService = inputService;
        this.notificationManager = notificationManager;
    }

    public void principalNotificationMenu(){
        String menu = """
                ------
                Notification menu:
                ------
                1. Create notification.
                2. Delete notification.
                3. Show all notifications.
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
                case 3 -> notificationManager.showAllNotifications();
                case 0 -> {
                    System.out.println("Returning to the main menu.");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while(true);
    }
}
