package escapeRoom.Controller;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.model.PeopleArea.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

public class UserController {
    private final Scanner input;
    private final UserService userService;

    public UserController(Scanner input, UserService userService){
        this.input = new Scanner(System.in);
        this.userService = userService;
    }

    public int selectOptionMenu(){
        System.out.print("Select an option:");
        int option = input.nextInt();
        input.nextLine();
        return option;
    }

    public void createUser() {
        String name = "";
        String lastname = "";
        String email = "";
        String phoneNumber = "";
        LocalDate bodLocalDate = null;
        boolean notifications = false;

        try {
            System.out.println("Users creation." +
                    "\nWrite a name: ");
            name = input.nextLine();

            System.out.println("Write a lastname:");
            lastname = input.nextLine();

            System.out.println("Email:");
            email = input.nextLine();

            System.out.println("Phone number:");
            phoneNumber = input.nextLine();

            System.out.println("Birth date [yyyy MM dd] :");
            String bod = input.nextLine();
            DateTimeFormatter bodTransform = DateTimeFormatter.ofPattern("yyyy MM dd");
            bodLocalDate = LocalDate.parse(bod, bodTransform);

            System.out.println("Do you would like receive notifications about our new rooms? (yes/no)");
            String choose = input.nextLine();

            if (choose.equalsIgnoreCase("yes")) {
                notifications = true;
            } else if (choose.equalsIgnoreCase("no")) {
                notifications = false;
            } else {
                System.out.println("Please, write yes or no");
            }

            User user = new User(name, lastname, email, phoneNumber, bodLocalDate, notifications);
            userService.create(user);

        } catch (SQLException e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }

    public void showUserById(){
        System.out.print("Enter user ID: ");
        int id = input.nextInt();
        input.nextLine();

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

    public void updateUser() {
        try {
            System.out.println("Please, enter the user ID:");
            int id = input.nextInt();
            input.nextLine();

            Optional<User> userOpt = userService.read(id);
            if (userOpt.isEmpty()) {
                System.out.println("User not found with ID: " + id);
                return;
            }

            User existingUser = userOpt.get();

            System.out.println("\nEnter new values (leave blank to keep current):");

            System.out.print("New name: ");
            String name = input.nextLine();

            System.out.print("New lastname: ");
            String lastname = input.nextLine();

            System.out.print("New email: ");
            String email = input.nextLine();

            System.out.print("New Phone number: ");
            String phoneNumber = input.nextLine();

            System.out.print("New birth date [yyyy MM dd] : ");
            String bod = input.nextLine();
            LocalDate bodLocalDate = existingUser.getDob();

            if (!bod.isEmpty()) {
                try {
                    DateTimeFormatter bodTransform = DateTimeFormatter.ofPattern("yyyy MM dd");
                    bodLocalDate = LocalDate.parse(bod, bodTransform);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Keeping current birth date.");
                }
            }

            boolean notificationStatus = existingUser.isNotificationStatus();
            System.out.print("Change notification status? (current: " +
                    (notificationStatus ? "enabled" : "disabled") + ") [y/n]: ");
            String choose = input.nextLine().trim().toLowerCase();

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
                    bodLocalDate,
                    notificationStatus
            );

            userService.update(updatedUser);
            System.out.println("\nUser updated successfully!");
            System.out.println(updatedUser);

        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
        }
    }

        public void deleteUserById() {
            System.out.print("Enter user ID to delete: ");
            int id = input.nextInt();
            input.nextLine();

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

    public boolean subscribeUser() {
        System.out.print("Enter user ID to subscribe: ");
        int id = input.nextInt();
        input.nextLine();

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

    public boolean unsubscribeUser() {
        System.out.print("Enter user ID to unsubscribe: ");
        int id = input.nextInt();
        input.nextLine();

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
