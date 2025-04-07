package escapeRoom.Service.UserService;

import escapeRoom.Service.AbsentEntityException;
import escapeRoom.model.PeopleArea.User;
import escapeRoom.Service.PeopleService.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class UserServiceTest {
    static UserService userService;
    static User testUser;

    @BeforeAll
    static void setUp() throws SQLException {
        userService = new UserService();

        testUser = new User(1,
                "Test",
                "User",
                "test@example.com",
                "123456789",
                LocalDate.of(2000, 1, 1),
                false
        );
    }

    @Test
    void create() throws SQLException{
        User newUser = new User(
                1,
                "Ali",
                "Vazquez",
                "gmail.com",
                "9866363663",
                LocalDate.of(1999,12,2),
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
                "gmail.com",
                "9866363663",
                LocalDate.of(1999,12,2),
                true);

        User createUser = userService.create(newUser);
        Optional<User> foundUser = userService.read(createUser.getId());
        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertEquals("Ali", foundUser.get().getName());
    }

    @Test
    void update() throws SQLException {

        Optional<User> result = userService.read(3);

        User updateData = new User(3, "Santi", "Lorec",
                "sani@gmail.com","656890666",  LocalDate.of(1999, 2, 3), false);
        userService.update(updateData);

        Optional<User> optional = userService.read(3);
        Assertions.assertTrue(optional.isPresent());

        User userFromDb = optional.get();
        Assertions.assertEquals(updateData.getId(), userFromDb.getId());
        Assertions.assertEquals(updateData.getName(), userFromDb.getName());
        Assertions.assertEquals(updateData.getLastname(), userFromDb.getLastname());
        Assertions.assertEquals(updateData.getEmail(), userFromDb.getEmail());
        Assertions.assertEquals(updateData.getPhoneNumber(), userFromDb.getPhoneNumber());
        Assertions.assertEquals(updateData.getDob(), userFromDb.getDob());
        Assertions.assertEquals(updateData.isNotificationStatus(), userFromDb.isNotificationStatus());
    }

    @Test
    void delete() throws SQLException {
        User newUser = new User("Sani", "Lorec",
                "sani@gmail.com","656890345",  LocalDate.of(1999, 2, 3), false);
        User createdUser = userService.create(testUser);
        int userId = createdUser.getId();

        Assertions.assertTrue(userService.delete(userId));

        Optional<User> deletedUser = userService.read(userId);
        assertFalse(deletedUser.isPresent());

        try(ResultSet rs = userService.getAll(userService.getConnection())){
            boolean userExists = false;
            while(rs.next()){
                if (rs.getInt("customer_id") == userId) {
                    userExists = true;
                    break;
            }
        }
            assertFalse(userExists, "The user should not exist after the delete");
        }
    }

    @Test
    void testCheckExistence() throws SQLException, AbsentEntityException {
        assertTrue(userService.existEntity(1,User.class));
        assertThrows(AbsentEntityException.class, ()->userService.existEntity(8,User.class));
    }
}
