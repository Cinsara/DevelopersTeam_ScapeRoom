package escapeRoom.service.forTesting.UserService;

import escapeRoom.PeopleService.PeopleClasses_Testing.User;
import escapeRoom.PeopleService.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class UserServiceTest {
    static UserService userService;

    @BeforeAll
    static void setUp() throws SQLException {
        userService = new UserService();
    }

    @Test
    void create() throws SQLException{
        User newUser = new User(
                1,
                "Ali",
                "Vazquez",
                LocalDate.of(1999,12,2),
                "gmail.com",
                "9866363663",
                true);

        User createUser = userService.create(newUser);
        Assertions.assertEquals("Ali", createUser.getName());
    }

    @Test
    void read() throws SQLException{
        User newUser = new User(
                1,
                "Ali",
                "Vazquez",
                LocalDate.of(1999,12,2),
                "gmail.com",
                "9866363663",
                true);

        User createUser = userService.create(newUser);
        Optional<User> foundUser = userService.read(createUser.getId());
        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertEquals("Ali", foundUser.get().getName());
    }

    @Test
    void update() throws SQLException {
        User newUser = new User(1, "Sani", "Lorec", LocalDate.of(1999, 2, 3),
                "sani@gmail.com", "656783981", false);
        userService.update(newUser);

        Optional<User> optional = userService.read(1);
        Assertions.assertTrue(optional.isPresent());

        User userFromDb = optional.get();
        Assertions.assertEquals(newUser.getId(), userFromDb.getId());
        Assertions.assertEquals(newUser.getName(), userFromDb.getName());
        Assertions.assertEquals(newUser.getLastname(), userFromDb.getLastname());
        Assertions.assertEquals(newUser.getEmail(), userFromDb.getEmail());
        Assertions.assertEquals(newUser.getPhoneNumber(), userFromDb.getPhoneNumber());
        Assertions.assertEquals(newUser.getDob(), userFromDb.getDob());
        Assertions.assertEquals(newUser.isNotificationStatus(), userFromDb.isNotificationStatus());
    }

    @Test
    void delete() throws SQLException {
        User newUser = new User(1, "Sani", "Lorec", LocalDate.of(1999, 2, 3),
                "sani@gmail.com", "656783981", false);


        assertTrue(userService.delete(1));

    }
}
