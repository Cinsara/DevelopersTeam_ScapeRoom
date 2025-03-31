package escapeRoom;

import escapeRoom.Controller.UserController;
import escapeRoom.Controller.UserMenuPrueba;
import escapeRoom.Service.NotificationService.NotificationService;
import escapeRoom.Service.PeopleService.UserService;

import java.sql.SQLException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws SQLException {
        try (Scanner mainScanner = new Scanner(System.in)) {  // Scanner principal
            UserService userService = new UserService();
            NotificationService notificationService = new NotificationService();

            // Pasa el Scanner al controller
            UserController userController = new UserController(
                    mainScanner,
                    userService
            );

            UserMenuPrueba userMenuPrueba = new UserMenuPrueba(userController);
            userMenuPrueba.startUserMenu();
        }
    }
}