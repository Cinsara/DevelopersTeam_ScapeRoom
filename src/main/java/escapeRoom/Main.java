package escapeRoom;

import escapeRoom.UserManager.UserManager;
import escapeRoom.UserManager.UserMenuPrueba;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.PeopleService.UserService;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner input = new Scanner(System.in);
        InputService inputService = new InputService(input);
        UserService userService = new UserService();
        UserManager userManager = new UserManager(userService,inputService);
        UserMenuPrueba userMenuPrueba = new UserMenuPrueba(userManager);
        userMenuPrueba.startUserMenu();
    }
}