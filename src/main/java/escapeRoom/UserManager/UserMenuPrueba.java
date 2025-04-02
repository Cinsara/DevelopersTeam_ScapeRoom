package escapeRoom.UserManager;

public class UserMenuPrueba {
    private final UserManager userManager;

    public UserMenuPrueba(UserManager userManager){
        this.userManager = userManager;
    }

    public void principalUserMenu(){
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
        System.out.println(menu);
    }

    public void startUserMenu(){
        int option;
        do {
            principalUserMenu();
            option = userManager.selectOptionMenu();

            switch(option){
                case 1 -> userManager.createUser();
                case 2 -> userManager.showAllUsers();
                case 3 -> userManager.showUserById();
                case 4 -> userManager.updateUser();
                case 5 -> userManager.deleteUserById();
                case 6 -> userManager.subscribeUser();
                case 7 -> userManager.unsubscribeUser();
                case 0 -> System.out.println("Returning to the main menu.");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while(option != 0);
    }
}
