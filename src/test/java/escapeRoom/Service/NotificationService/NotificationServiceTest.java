package escapeRoom.Service.NotificationService;

import escapeRoom.model.Notification.Notification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class NotificationServiceTest {
    static NotificationService service;

    @BeforeAll
    static void setUp() throws SQLException {
        service = new NotificationService();
    }
    @Test
    void create() throws SQLException {
        Notification newNotification = new Notification("hola", LocalDate.now());
        Notification returnedNotification = service.create(newNotification);
        assertNotNull(returnedNotification);
        assertEquals(6,returnedNotification.getId());
    }

    @Test
    void read() throws SQLException {
        Notification newNotification = service.read(1).get();
        assertEquals("Your escape room session is booked for tomorrow!",newNotification.getContent());
    }

    @Test
    void update() throws SQLException {
        Notification newNotification = service.update(new Notification(1,"Hola",LocalDate.now()));
    }

    @Test
    void delete() throws SQLException {
        service.delete(1);
    }
}