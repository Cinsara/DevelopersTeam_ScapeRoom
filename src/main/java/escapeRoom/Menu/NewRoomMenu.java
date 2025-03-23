package escapeRoom.Menu;

import escapeRoom.gameArea.CluePropFactory.*;
import escapeRoom.gameArea.RoomBuilder.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NewRoomMenu {

    static final Scanner sc = new Scanner(System.in);

    public static void newRoomMenu() {

        String roomName;
        Theme theme = null;
        Difficulty difficulty = null;

        int opc;

        do {
            System.out.println("************\n" +
                    "NEW ROOM MENU\n" +
                    "************\n");

            System.out.println("What theme?\n" +
                    "1. Love Affair\n" +
                    "2. Fantastic\n" +
                    "3. Mystery\n" +
                    "4. Sci-Fi\n");

            System.out.print("Your selection: ");

            // Validate user input
            while (!sc.hasNextInt()) {
                System.out.println("Invalid option! Please enter a number between 1 and 4.");
                sc.next();
                System.out.print("Your selection: ");
            }

            opc = sc.nextInt();

            if (opc < 1 || opc > 4) {
                System.out.println("Invalid option! Please enter a number between 1 and 4.");
            } else if (opc > 0) {
                switch (opc) {
                    case 1 -> theme = Theme.LOVEAFFAIR;
                    case 2 -> theme = Theme.FANTASTIC;
                    case 3 -> theme = Theme.MYSTERY;
                    case 4 -> theme = Theme.SCIFI;
                }
                break;
            }

        } while (true);

        System.out.println("You have selected: " + theme +"\n");

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
            } else if (opc>0){
                switch (opc) {
                    case 1 -> difficulty = Difficulty.EASY;
                    case 2 -> difficulty = Difficulty.MEDIUM;
                    case 3 -> difficulty = Difficulty.HARD;
                }
                break;
            }

        } while (true);

        System.out.println("You have selected: " + difficulty + "\n");

        //CLUE INPUT

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

        //PROP INPUT

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

        sc.nextLine();

        for (int i = 0; i < opc; i++) {
            props.add(PropFactory.createProp("Mountain"));
        }

        List<Integer> props_id = new ArrayList<>();

        for (GameElement prop : props) {
            props_id.add(prop.getId());
        }

        System.out.println();

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

        //ROOM NAME INPUT
        System.out.print("Enter a name for your new Room: ");

        roomName = sc.nextLine();

        //ROOM CREATION

        RoomDirector roomDirector = new RoomDirector();
        RoomBuilder roomBuilder = new RoomBuilder();

        roomDirector.buildRoom(roomBuilder, roomName, theme, difficulty, clues_id, props_id);
        Room newRoom = roomBuilder.create();

        System.out.println("Sweet! Here's your new room:\n" + newRoom + "\n");

        addRoomToList(newRoom);

    }

    public static void addRoomToList(Room newRoom){
        List<Room> roomList = new ArrayList<>();
        roomList.add(newRoom);

    }

}
