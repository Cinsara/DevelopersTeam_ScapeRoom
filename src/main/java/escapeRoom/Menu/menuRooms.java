package escapeRoom.Menu;

import escapeRoom.gameArea.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class menuRooms {

    static Scanner sc = new Scanner(System.in);

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
                    case 1:
                        newRoomMenu();
                        break;
                    case 2:

                        break;
                    case 0:
                        System.out.println("Going back...");
                        break;
                }
            }
        } while (opc != 0); // Keep asking until user selects "0" to go back
    }

    public static void newRoomMenu() {

        String roomName;
        Theme theme = null;
        Difficulty difficulty = null;

        System.out.print("Your selection: ");

        int opc;

        do {
            System.out.println("************\n" +
                    "NEW ROOM MENU\n" +
                    "************\n");

            System.out.println("What theme?\n" +
                    "1. Love Affair\n" +
                    "2. Fantastic\n" +
                    "3. Mystery\n" +
                    "4. Sci-Fi\n" +
                    "0. Go Back");

            System.out.print("Your selection: ");

            // Validate user input
            while (!sc.hasNextInt()) {
                System.out.println("Invalid option! Please enter a number between 0 and 4.");
                sc.next(); // Clear invalid input
                System.out.print("Your selection: ");
            }

            opc = sc.nextInt();

            if (opc < 0 || opc > 4) {
                System.out.println("Invalid option! Please enter a number between 0 and 4.");
            } else if (opc > 0) {  // Exit the loop after a valid selection
                switch (opc) {
                    case 1:
                        theme = Theme.LOVEAFFAIR;
                        break;
                    case 2:
                        theme = Theme.FANTASTIC;
                        break;
                    case 3:
                        theme = Theme.MYSTERY;
                        break;
                    case 4:
                        theme = Theme.SCIFI;
                        break;
                }
                break; // Exit the loop after a valid selection
            }

        } while (opc != 0); // If "0" is selected, exit

        if (opc > 0) {
            System.out.println("You have selected: " + theme +"\n");
            // Continue the program...
        } else {
            System.out.println("Going back...");
        }


        do {

            System.out.println("How difficult?\n" +
                    "1. Easy\n" +
                    "2. Medium\n" +
                    "3. Hard\n");

            System.out.print("Your selection: ");

            while (!sc.hasNextInt()) {
                System.out.println("Invalid option! Please enter a number between 1 and 4.");
                sc.next();
                System.out.println("Your selection: ");
            }

            opc = sc.nextInt();

            if (opc < 0 || opc > 3) {
                System.out.println("Invalid option! Please enter a number between 1 and 3");
                sc.next();
                System.out.println("Your selection: ");
            } else {

                switch (opc) {
                    case 1:
                        difficulty = Difficulty.EASY;
                        break;
                    case 2:
                        difficulty = Difficulty.MEDIUM;
                        break;
                    case 3:
                        difficulty = Difficulty.HARD;
                        break;
                }
                break;
            }

        } while (opc != 0);

        if (opc > 0) {
            System.out.println("You have selected: " + difficulty);
        } else {
            System.out.println("Going back...");
        }

            System.out.print("How many Indications (clues)?\n");
            opc = sc.nextInt();

            List<GameElement> clues = new ArrayList<>();

            for (int i = 0; i < opc; i++) {
                clues.add(ClueFactory.createClue("Indication"));
            }

            System.out.print("How many Enigmas (clues)?\n");
            opc = sc.nextInt();

            for (int i = 0; i < opc; i++) {
                clues.add(ClueFactory.createClue("Enigma"));
            }

            List<Integer> clues_id = new ArrayList<>();

            for (GameElement clue : clues) {
                clues_id.add(clue.getId());
            }

            System.out.print("How many Props \"Spade\"?\n");
            opc = sc.nextInt();

            List<GameElement> props = new ArrayList<>();

            for (int i = 0; i < opc; i++) {
                props.add(PropFactory.createProp("Spade"));
            }

            System.out.print("How many Props \"Closet\"?\n");
            opc = sc.nextInt();

            for (int i = 0; i < opc; i++) {
                props.add(PropFactory.createProp("Closet"));
            }

            System.out.print("How many Props \"Mountain\"?\n");
            opc = sc.nextInt();

            for (int i = 0; i < opc; i++) {
                props.add(PropFactory.createProp("Mountain"));
            }

            List<Integer> props_id = new ArrayList<>();

            for (GameElement prop : props) {
                props_id.add(prop.getId());
            }


//        //Clues are created with Factory Pattern
//        GameElement clue1 = ClueFactory.createClue("Enigma");
//        GameElement clue2 = ClueFactory.createClue("Indication");
//        GameElement clue3 = ClueFactory.createClue("Indication");
//
//
//
//        clues_id.add(clue1.getId());
//        clues_id.add(clue2.getId());
//        clues_id.add(clue3.getId());
//
//        //Props as well the same way
//        GameElement prop1 = PropFactory.createProp("Spade");
//        GameElement prop2 = PropFactory.createProp("Closet");
//        GameElement prop3 = PropFactory.createProp("Mountain");
//
//        List<Integer> props_id = new ArrayList<>();
//
//        props_id.add(prop1.getId());
//        props_id.add(prop2.getId());
//        props_id.add(prop3.getId());

            sc.nextLine();

            System.out.print("What name should it have?");

            roomName = sc.nextLine();

            System.out.println("Sweet. Here's your new room:");

            RoomDirector roomDirector = new RoomDirector();
            RoomBuilder roomBuilder = new RoomBuilder();

            roomDirector.buildRoom(roomBuilder, roomName, theme, difficulty, clues_id, props_id);

            List<Room> roomList = new ArrayList<>();

            roomList.add(roomBuilder.create());

            System.out.println(roomList.get(roomList.size()-1));


            for (int i = 0; i < opc; i++) {
                props.add(PropFactory.createProp("Mountain"));
            }


        }


    public void newPropMenu(){

    }

    public void newClueMenu(){

    }

    public void inventaryMenu(){

    }


}
