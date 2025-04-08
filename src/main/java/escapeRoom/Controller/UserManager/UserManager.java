package escapeRoom.Controller.UserManager;
import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.InputService.BackToSecondaryMenuException;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.OutPutService.TablePrinter;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.Model.PeopleArea.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserManager {
    private final UserService userService;
    private final InputService inputService;
    private final Connection connection = ConnectionManager.getConnection();

    public UserManager(InputService inputService, UserService userService) throws SQLException {
        this.inputService = inputService;
        this.userService = userService;
    }

    public int selectOptionMenu() throws BackToSecondaryMenuException {
        return inputService.readInt("Select an option:");
    }

    public void createUser() throws BackToSecondaryMenuException{
        try {
            String name = inputService.readString("Write a name:");
            String lastname = inputService.readString("Write a lastname:");
            String email = inputService.readString("Email:");
            String phoneNumber = inputService.readString("Phone number:");
            LocalDate bod = inputService.readDate("Birth date [yyyy MM dd] :", "yyyy MM dd");
            boolean notifications = inputService.readBoolean("Do you would like receive notifications about our new rooms? (yes/no)");

            User user = new User(name, lastname, email, phoneNumber, bod, notifications);
            userService.create(user);

        } catch (SQLException e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }

    public void showUserById() throws BackToSecondaryMenuException{
        int id = inputService.readInt("Enter user ID:");
        try {
            Optional<User> userOptional = userService.read(id);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                System.out.println(user);
            } else {
                System.out.println("User not found with ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user: " + e.getMessage());
        }
    }

    public void showAllUsers(){
        try {
            List<User> userList = userService.getAllEntities(connection);
            if(userList.isEmpty()){
                System.out.println("The user list is empty.");
            } else {
                List<UserWrapper> wrapperList = new ArrayList<>();
                for(User user : userList ){
                    wrapperList.add(new UserWrapper(user));
                }
                StringBuilder table = TablePrinter.buildTable(wrapperList,true);
                System.out.println(table);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user list: " + e.getMessage());
        }
    }

    public void updateUser() throws BackToSecondaryMenuException{
        try {
            int id = inputService.readInt("Enter user ID:");

            Optional<User> userOpt = userService.read(id);
            if (userOpt.isEmpty()) {
                System.out.println("User not found with ID: " + id);
                return;
            }
            User existingUser = userOpt.get();

            System.out.println("\nEnter new values (leave blank to keep current):");

            String name = inputService.readString("New name:");
            String lastname = inputService.readString("New lastname:");
            String email = inputService.readString("New email:");
            String phoneNumber = inputService.readString("New Phone number:");
            String dobInput = inputService.readString("New birth date [yyyy MM dd]:");
            LocalDate bod = null;
            if (!dobInput.isEmpty()) {
                bod = LocalDate.parse(dobInput, DateTimeFormatter.ofPattern("yyyy MM dd"));
            }

            boolean notificationStatus = existingUser.isNotificationStatus();
            System.out.print("Change notification status? (current: " +
                    (notificationStatus ? "enabled)" : "disabled)"));
            String choose = inputService.readString("").toLowerCase();

            if (choose.equals("y") || choose.equals("yes")) {
                notificationStatus = !notificationStatus;
            } else if (!choose.equals("n") && !choose.equals("no") && !choose.isEmpty()) {
                System.out.println("Invalid option. Keeping current status: " +
                        (notificationStatus ? "enabled" : "disabled"));
            }

            User updatedUser = new User(
                    id,
                    name.isEmpty() ? existingUser.getName() : name,
                    lastname.isEmpty() ? existingUser.getLastname() : lastname,
                    email.isEmpty() ? existingUser.getEmail() : email,
                    phoneNumber.isEmpty() ? existingUser.getPhoneNumber() : phoneNumber,
                    bod == null ? existingUser.getDob() : bod,
                    notificationStatus
            );

            userService.update(updatedUser);
            System.out.println("\nUser updated successfully!");
            System.out.println(updatedUser);

        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
        }
    }

        public void deleteUserById() throws BackToSecondaryMenuException{
            int id = inputService.readInt("Enter user ID to delete: ");
            try {
            boolean isDeleted = userService.delete(id);
            if (isDeleted) {
                System.out.println("User with ID " + id + " was deleted successfully.");
            } else {
                System.out.println("User with ID " + id + " was not found.");
            }
            } catch (SQLException e) {
                System.out.println("An error occurred while deleting the user: " + e.getMessage());
            }
        }

    public boolean subscribeUser() throws BackToSecondaryMenuException{
        int id = inputService.readInt("Enter user ID to subscribe: ");
        try {
            Optional<User> userOpt = userService.read(id);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                if (!user.isNotificationStatus()) {
                    user.setNotificationStatus(true);
                    userService.update(user);
                    System.out.println("Added user subscription.");
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error subscribing user: " + e.getMessage());
            return false;
        }
    }

    public boolean unsubscribeUser() throws BackToSecondaryMenuException {
        int id = inputService.readInt("Enter user ID to unsubscribe:");

        try {
            Optional<User> userOpt = userService.read(id);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                if (user.isNotificationStatus()) {
                    user.setNotificationStatus(false);
                    userService.update(user);
                    System.out.println("User subscription removed.");
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error unsubscribing user: " + e.getMessage());
            return false;
        }
    }
}
