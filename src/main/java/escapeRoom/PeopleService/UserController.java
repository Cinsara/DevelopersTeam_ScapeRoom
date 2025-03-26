package escapeRoom.PeopleService;

import escapeRoom.PeopleService.PeopleClasses_Testing.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    public User createUser() {
        String name = "";
        String lastname = "";
        String email = "";
        String phoneNumber = "";
        LocalDate bodLocalDate= null;
        boolean notifications = false;

        try {
            Scanner input = new Scanner(System.in);
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
            bodLocalDate = LocalDate.parse(bod);

            System.out.println("Do you would like receive notifications about our new rooms? (yes/no)");
            String choose = input.nextLine();

            if (choose.equalsIgnoreCase("yes")) {
                notifications = true;
            } else if (choose.equalsIgnoreCase("no")) {
                notifications = false;
            } else {
                System.out.println("Please, write yes or no");
            }

            User user = new User(name, lastname, bodLocalDate, email, phoneNumber, notifications);
            userService.create(user);
            return user;

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return null;
        }
    }

  /* public void showAllUsers(int id){
       try {
           Optional<User> users = userService.read(id);
           for (User user : users) {
               System.out.println(user);
           }
       } catch (SQLException e) {
           System.out.println("An error occurred while retrieving users from the database: " + e.getMessage());
       }
    } */

    public void findUserId(int id) {
        try {
            Optional<User> user = userService.read(id);
            if (user.isPresent()) {
                System.out.println("User found:");
                System.out.println(user.get());
            } else {
                System.out.println("User with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while searching for the user: " + e.getMessage());
        }
    }

    public void deleteUserById(int id) {
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
}
