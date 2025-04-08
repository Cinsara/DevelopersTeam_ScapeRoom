package escapeRoom.NotificationManager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Controller.NotificationManager.NotificationManager;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.InputService.InputServiceManager;
import escapeRoom.Service.NotificationService.NotificationService;
import escapeRoom.Service.PeopleService.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class NotificationManagerTest {
    private static NotificationManager notificationManager;
    private static ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() throws SQLException {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        InputStream simulatedIn = new ByteArrayInputStream("3\n".getBytes());
        System.setIn(simulatedIn);

        InputService inputService = InputServiceManager.getInputService();;
        Connection connection = ConnectionManager.getConnection();
        NotificationService notificationService = new NotificationService(connection);
        UserService userService = new UserService(connection);
        notificationManager = new NotificationManager(inputService,notificationService,userService);


    }

    @AfterEach
    void restoreSystemIn() {
        System.setIn(System.in);
    }

    @Test
    void sendNotifications() throws SQLException {
        notificationManager.sendNotifications();
        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("The notification can't be sent. No subscribed users found.") ||
                        consoleOutput.contains("Sending notification to subscribed users..."),
                "No attempt to send notifications was detected.");
    }

    @Test
    void createNotification() throws SQLException {
        notificationManager.createNotification();
        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("Notification created successfully!"),
                "Error creating a new notification: ");
    }

    @Test
    void deleteNotification() throws SQLException{
        notificationManager.deleteNotification();
        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("The notification with ID 3 was deleted successfully."),
                "The deletion message is not as expected.");
    }
}