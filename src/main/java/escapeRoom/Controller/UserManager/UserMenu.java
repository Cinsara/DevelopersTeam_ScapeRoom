package escapeRoom.Controller.UserManager;

import escapeRoom.Service.InputService.BackToSecondaryMenuException;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.PeopleService.UserService;

import java.sql.SQLException;

public class UserMenu {
    private UserManager userManager;
    private InputService inputService;
    
    public UserMenu(InputService inputService, UserManager userManager){
        this.inputService = inputService;
        this.userManager = userManager;
    }

    public int principalUserMenu(){
        String menu = """
                ------
                User menu:
                ------
                1. Create user.
                2. Show all users.
                3. Find user by ID.
                4. Update user.
                5. Delete user by ID.
                6. Add a subscription to a user.
                7. Delete a subscription.
                0. Back to the main menu.
                ------""";
        try{
            return inputService.readInt(menu);
        } catch (BackToSecondaryMenuException e) {
            return 0;
        }
    }

    public void startUserMenu(){
        int option;
        do {
            try{
                option = principalUserMenu();

                switch(option){
                    case 1 -> userManager.createUser();
                    case 2 -> userManager.showAllUsers();
                    case 3 -> userManager.showUserById();
                    case 4 -> userManager.updateUser();
                    case 5 -> userManager.deleteUserById();
                    case 6 -> userManager.subscribeUser();
                    case 7 -> userManager.unsubscribeUser();
                    case 0 -> {
                        System.out.println("Returning to the main menu.");
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }catch (BackToSecondaryMenuException e){}
        } while(true);
    }
}
