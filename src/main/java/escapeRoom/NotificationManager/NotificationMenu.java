package escapeRoom.NotificationManager;

public class NotificationMenu {
    private final NotificationManager notificationManager;

    public NotificationMenu(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
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
                case 0 -> System.out.println("Returning to the main menu.");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while(option != 0);
    }
}
