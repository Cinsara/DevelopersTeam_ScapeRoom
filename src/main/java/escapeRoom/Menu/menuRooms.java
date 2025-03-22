package escapeRoom.Menu;

import java.util.Scanner;

import static escapeRoom.Menu.InventoryMenu.inventoryMenu;
import static escapeRoom.Menu.NewRoomMenu.newRoomMenu;

public class menuRooms {

    static final Scanner sc = new Scanner(System.in);

    public static void mainMenuRooms(){

        int opc;

        do {
            System.out.println("************\n" +
                    "ROOM MENU\n" +
                    "************\n" + "\n" +
                    "1. NEW ROOM\n" +
                    "2. INVENTORY\n" +
                    "0. GO BACK\n");

            System.out.print("Your selection: ");

            // Validate user input
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number between 0 and 2.");
                sc.next(); // Clear invalid input
                System.out.print("Your selection: ");
            }

            opc = sc.nextInt();

            // Validate range
            if (opc < 0 || opc > 2) {
                System.out.println("Invalid option! Please enter a number between 0 and 2.");
            } else {
                switch (opc) {
                    case 1 -> newRoomMenu();
                    case 2 -> inventoryMenu();
                    case 0 -> System.out.println("Going back...");
                }
            }
        } while (opc != 0); // Keep asking until user selects "0" to go back
    }




    public void newPropMenu(){

    }

    public void newClueMenu(){

    }

    public static void inventaryMenu(){

    }


}
