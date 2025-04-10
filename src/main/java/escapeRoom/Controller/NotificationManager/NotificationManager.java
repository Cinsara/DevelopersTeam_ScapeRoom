package escapeRoom.Controller.NotificationManager;

import escapeRoom.Service.InputService.BackToSecondaryMenuException;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.NotificationService.NotificationService;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.Model.Notification.Notification;
import escapeRoom.Model.PeopleArea.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class NotificationManager {
    private final NotificationService notificationService;
    private final InputService inputService;
    private final UserService userService;

    public NotificationManager(InputService inputService, NotificationService notificationService,
                               UserService userService) {
        this.notificationService = notificationService;
        this.inputService = inputService;
        this.userService = userService;
    }

    public void sendNotifications() throws SQLException {
        List<User> userGenericList = userService.getAllEntities(userService.getConnection());
        List<User> subscribedUsers = userGenericList.stream()
                .filter(User::isNotificationStatus)
                .toList();

        if(subscribedUsers.isEmpty()) {
            System.out.println("The notification can't be sent. No subscribed users found.");
        }

        System.out.println("Sending notification to subscribed users...");

        for(User user : subscribedUsers) {
            System.out.println("Sent to: " +
                    "\n-----" +
                    "\nName: " + user.getName() +
                    "\nLastname: " + user.getLastname() +
                    "\nEmail: " + user.getEmail() +
                    "\n-----");
            System.out.println("Notification sent successfully");
        }
    }

    public void createNotification() throws BackToSecondaryMenuException {
        try {
            String content = inputService.readString("Write the notification content:");
            LocalDate dateSent = LocalDate.now();
            Notification notification = new Notification(content, dateSent);
            notificationService.create(notification);
            System.out.println("Notification created successfully!");
            sendNotifications();

        } catch (SQLException e) {
            System.out.println("Error creating a new notification: " + e.getMessage());
        }
    }

    public void showAllNotifications(){
        try {
            List<Notification> notificationList = notificationService.getAllEntities(notificationService.getConnection());
            if(notificationList.isEmpty()){
                System.out.println("The notification list is empty.");
            } else {
                for(Notification notification : notificationList ){
                    System.out.println("ID: " + notification.getId() + " - " + notification);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving notification list: " + e.getMessage());
        }
    }

    public void deleteNotification() throws BackToSecondaryMenuException {
        int id = inputService.readInt("Enter the notification ID to delete: ");
        try{
            boolean isDeleted = notificationService.delete(id);
            if(isDeleted){
                System.out.println("The notification with ID " + id + " was deleted successfully.");
            } else {
                System.out.println("The notification with ID " + id + " was not found.");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while deleting the notification: " + e.getMessage());
        }
    }
}
