package escapeRoom.UserManager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Controller.UserManager.UserManager;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.InputService.InputServiceManager;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.model.PeopleArea.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {
    private static UserManager userManager;
    private static ByteArrayOutputStream outputStream;
    private static UserService userService;
    private static Connection connection;
    private static InputService inputService;

    @BeforeEach
    void setUp() throws SQLException {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        connection = ConnectionManager.getConnection();
        userService = new UserService();

        inputService = InputServiceManager.getInputService();
        userManager = new UserManager(userService, inputService);
    }

    @AfterEach
    void tearDown() throws SQLException {
        System.setIn(System.in);
        System.setOut(System.out);
    }

    private void setSimulatedInput(String input) throws SQLException {
        InputStream simulatedIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(simulatedIn);
        inputService = InputServiceManager.getInputService();;
        userManager = new UserManager(userService, inputService);
    }

    @Test
    void createUser() throws SQLException {
        String input = "TestUser\nTestLastName\ntest@example.com\n123456789\n1990 01 01\nyes\n";
        setSimulatedInput(input);

        userManager.createUser();

        String output = outputStream.toString();
        assertFalse(output.contains("Error creating user"), "There should be no errors when creating the user");

        try {
            List<User> users = userService.getAllEntities(connection);
            Optional<User> createdUser = users.stream()
                    .filter(u -> u.getEmail().equals("test@example.com"))
                    .findFirst();

            assertTrue(createdUser.isPresent(), "The user should exist in the database");
            assertEquals("TestUser", createdUser.get().getName());
            assertEquals("TestLastName", createdUser.get().getLastname());
            assertEquals("123456789", createdUser.get().getPhoneNumber());
            assertEquals(LocalDate.of(1990, 1, 1), createdUser.get().getDob());
            assertTrue(createdUser.get().isNotificationStatus());

        } catch (SQLException e) {
            System.out.println("SQLException during verification: " + e.getMessage());
        }
    }

    @Test
    void showUserById() throws SQLException {
        User testUser = new User("Show", "User", "show@test.com", "123456", LocalDate.now(), true);
        try {
            userService.create(testUser);
        } catch (SQLException e) {
            System.out.println("Error setting up test: " + e.getMessage());
        }

        setSimulatedInput(testUser.getId() + "\n");

        userManager.showUserById();
        String output = outputStream.toString();

        assertTrue(output.contains("User ID: " + testUser.getId()));
        assertTrue(output.contains("Name: Show"));
        assertTrue(output.contains(" User"));
        assertFalse(output.contains("User not found"));
    }

    @Test
    void showAllUsers() {
        try {
            userService.create(new User("User1", "Last1", "user1@test.com",
                    "111", LocalDate.now(), true));
            userService.create(new User("User2", "Last2", "user2@test.com",
                    "222", LocalDate.now(), false));

        } catch (SQLException e) {
            fail("Error setting up test: " + e.getMessage());
        }

        userManager.showAllUsers();
        String output = outputStream.toString();

        assertTrue(output.contains("User list:"));
        assertTrue(output.contains("User1"));
        assertTrue(output.contains("User2"));
    }

    @Test
    void updateUser() throws SQLException {
        User originalUser = new User("Original", "User", "original@test.com",
                "111", LocalDate.of(1990, 1, 1), false);
        try {
            userService.create(originalUser);
        } catch (SQLException e) {
            System.out.println("Error setting up test: " + e.getMessage());
        }

        String input = originalUser.getId() + "\n" +
                "Updated\n" +
                "\n" +
                "updated@test.com\n" +
                "\n" +
                "1995 05 15\n" +
                "yes\n";

        setSimulatedInput(input);
        userManager.updateUser();
        String output = outputStream.toString();
        assertTrue(output.contains("User updated successfully!"));

        try {
            Optional<User> updatedUser = userService.read(originalUser.getId());
            assertTrue(updatedUser.isPresent());
            assertEquals("Updated", updatedUser.get().getName());
            assertEquals("User", updatedUser.get().getLastname());
            assertEquals("updated@test.com", updatedUser.get().getEmail());
            assertEquals("111", updatedUser.get().getPhoneNumber());
            assertEquals(LocalDate.of(1995, 5, 15), updatedUser.get().getDob());
            assertTrue(updatedUser.get().isNotificationStatus());

        } catch (SQLException e) {
            System.out.println("Error verifying update: " + e.getMessage());
        }
    }

    @Test
    void deleteUserById() throws SQLException {
        User user = new User("ToDelete", "User", "delete@test.com",
                "111", LocalDate.now(), true);
        try {
            userService.create(user);
        } catch (SQLException e) {
            System.out.println("Error setting up test: " + e.getMessage());
        }

        setSimulatedInput(user.getId() + "\n");
        userManager.deleteUserById();
        String output = outputStream.toString();
        assertTrue(output.contains("was deleted successfully"));

        try {
            Optional<User> deletedUser = userService.read(user.getId());
            assertFalse(deletedUser.isPresent());

        } catch (SQLException e) {
            System.out.println("Error. The user can not be deleted: " + e.getMessage());
        }
    }

    @Test
    void subscribeUser() throws SQLException {
        User user = new User("Test", "User", "subscribe@test.com",
                "111", LocalDate.now(), false);
        try {
            userService.create(user);
        } catch (SQLException e) {
            System.out.println("Error setting up test: " + e.getMessage());
        }

        setSimulatedInput(user.getId() + "\n");
        boolean result = userManager.subscribeUser();

        assertTrue(result);
        String output = outputStream.toString();
        assertTrue(output.contains("Added user subscription"));

        try {
            Optional<User> updatedUser = userService.read(user.getId());
            assertTrue(updatedUser.isPresent());
            assertTrue(updatedUser.get().isNotificationStatus());
        } catch (SQLException e) {
            System.out.println("Error verifying subscription: " + e.getMessage());
        }
    }

    @Test
    void unsubscribeUser() throws SQLException {
        User user = new User("Test", "User", "unsubscribe@test.com",
                "111", LocalDate.now(), true);
        try {
            userService.create(user);
        } catch (SQLException e) {
            System.out.println("Error setting up test: " + e.getMessage());
        }

        setSimulatedInput(user.getId() + "\n");
        boolean result = userManager.unsubscribeUser();

        assertTrue(result);
        String output = outputStream.toString();
        assertTrue(output.contains("User subscription removed"));

        try {
            Optional<User> updatedUser = userService.read(user.getId());
            assertTrue(updatedUser.isPresent());
            assertFalse(updatedUser.get().isNotificationStatus());

        } catch (SQLException e) {
            System.out.println("Error verifying subscription: " + e.getMessage());
        }
    }
}