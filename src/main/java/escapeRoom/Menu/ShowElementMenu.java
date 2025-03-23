package escapeRoom.Menu;

import java.util.Scanner;

public class ShowElementMenu {

    static final Scanner sc = new Scanner(System.in);

    public static void showElementMenu() {

        int opc;

        do {
            System.out.println("************\n" +
                    "SHOW MENU\n" +
                    "************\n" + "\n" +
                    "1. SHOW ROOMS\n" +
                    "2. SHOW CLUES\n" +
                    "3. SHOW PROPS\n" +
                    "4. SHOW EVERYTHING\n" +
                    "0. GO BACK\n");

            System.out.print("Your selection: ");

            // Validate user input
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number between 0 and 4.");
                sc.next(); // Clear invalid input
                System.out.print("Your selection: ");
            }

            opc = sc.nextInt();

            // Validate range
            if (opc < 0 || opc > 4) {
                System.out.println("Invalid option! Please enter a number between 0 and 4.");
            } else {
                switch (opc) {
                    case 1 -> showElementMenu();
//                    case 2 -> deleteElementMenu();
//                    case 3 -> individualInventoryMenu();
//                    case 4 -> globalInventoryMenu();
                    case 0 -> System.out.println("Going back...");
                }
            }
        } while (opc != 0);
    }
}
