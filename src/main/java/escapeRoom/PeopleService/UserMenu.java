package escapeRoom.PeopleService;

import escapeRoom.PeopleService.PeopleClasses_Testing.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserMenu {

    private final UserController userController;
    private final List<User> userList = new ArrayList<>();

    public UserMenu(UserController userController){
        this.userController = userController;
    }

    public static void principalUserMenu(){
        String menu = """
                User menu:
                
                1. Create user.
            
                2. Find user by ID.
                
                3. Show all users.
                
                4. Delete user by ID.
                
                0. Back to the main menu.""";

        System.out.println(menu);
    }

    public void startUserMenu(){
        int option;
        Scanner input = new Scanner(System.in);
        do{
            principalUserMenu();
            System.out.println("Please, write an option:");
            option = input.nextInt();
            input.nextLine();

           switch(option){
               case 1 -> userList.add(userController.createUser());
               case 2 -> findUserId(input);
              // case 3 -> userController.showAllUsers();
               case 4 -> deleteUserById(input);
               case 0 -> System.out.println("Returning to the main menu.");
               default -> System.out.println("Invalid option. Please try again.");
           }
        }while(option != 0);
    }

    private void findUserId(Scanner input){
        System.out.println("Please, enter the user ID:");
        int id = input.nextInt();
        input.nextLine();
        userController.findUserId(id);
    }

    private void deleteUserById(Scanner input) {
        System.out.println("Enter the user ID to delete:");
        int id = input.nextInt();
        input.nextLine();
        userController.deleteUserById(id);
    }
}
