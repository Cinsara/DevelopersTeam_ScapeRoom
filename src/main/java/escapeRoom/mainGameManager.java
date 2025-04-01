package escapeRoom;

import escapeRoom.Manager.GameController;
import escapeRoom.Manager.GameInputCollector;
import escapeRoom.Manager.GameMenu;
import escapeRoom.Service.InputService.InputService;

import java.sql.SQLException;
import java.util.Scanner;

public class mainGameManager {
    static public void main (String[] args){
        GameMenu gameMenu = new GameMenu(new GameController(),new InputService(new Scanner(System.in)));
        gameMenu.startGameMenu();
    }
}
