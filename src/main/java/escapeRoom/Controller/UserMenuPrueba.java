package escapeRoom.Controller;

public class UserMenuPrueba {
    private final UserController userController;

    public UserMenuPrueba(UserController userController){
        this.userController = userController;
    }

    public void principalUserMenu(){
        String menu = """
                ------
                User menu:
                ------
                1. Create user.
                2. Find user by ID.
                3. Update user.
                4. Delete user by ID.
                5. Add a subscription to a user.
                6. Delete a subscription.
                0. Back to the main menu.
                ------""";
        System.out.println(menu);
    }

    public void startUserMenu(){
        int option;
        do {
            principalUserMenu();
            option = userController.selectOptionMenu();

            switch(option){
                case 1 -> userController.createUser();
                case 2 -> userController.showUserById();
                case 3 -> userController.updateUser();
                case 4 -> userController.deleteUserById();
                case 5 -> userController.subscribeUser();
                case 6 -> userController.unsubscribeUser();
                case 0 -> System.out.println("Returning to the main menu.");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while(option != 0);
    }
}
