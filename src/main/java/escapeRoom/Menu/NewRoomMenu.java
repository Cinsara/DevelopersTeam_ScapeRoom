package escapeRoom.Menu;

import escapeRoom.gameArea.CluePropFactory.*;
import escapeRoom.gameArea.Inventory.RoomPrinter;
import escapeRoom.gameArea.RoomBuilder.*;
import escapeRoom.gameArea.RoomCreation.RoomCreationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


    public class NewRoomMenu {

        private static final Scanner sc = new Scanner(System.in);
        private final RoomCreationService roomCreationService = new RoomCreationService();

        public void showMenu() {

            NewRoomMenu menu = new NewRoomMenu();
            RoomCreationService roomService = new RoomCreationService();
            RoomPrinter printer = new RoomPrinter(roomService);


            String roomName;
            Theme theme = getThemeFromUser();
            Difficulty difficulty = getDifficultyFromUser();


            System.out.print("Enter a name for your new Room: ");
            roomName = sc.nextLine();

            int indications = getIntInput("How many Indications (clues)? ");
            int enigmas = getIntInput("How many Enigmas (clues)? ");
            int spades = getIntInput("How many Props \"Spade\"? ");
            int closets = getIntInput("How many Props \"Closet\"? ");
            int mountains = getIntInput("How many Props \"Mountain\"? ");

            roomCreationService.createRoom(roomName, theme, difficulty, indications, enigmas, spades, closets, mountains);
            System.out.println("✅ Room created successfully!\n");

            // Print Rooms
            printer.printLastRoomCreated();

            System.out.println("All rooms: ");
            printer.printAllRooms();
        }

        private Theme getThemeFromUser() {
            int option;
            do {
                System.out.println("""
                    ************
                    NEW ROOM MENU
                    ************
                    What theme?
                    1. Love Affair
                    2. Fantastic
                    3. Mystery
                    4. Sci-Fi
                    """);
                option = getIntInput("Your selection: ");
            } while (option < 1 || option > 4);

            return switch (option) {
                case 1 -> Theme.LOVEAFFAIR;
                case 2 -> Theme.FANTASTIC;
                case 3 -> Theme.MYSTERY;
                case 4 -> Theme.SCIFI;
                default -> throw new IllegalStateException("Unexpected value: " + option);
            };
        }

        private Difficulty getDifficultyFromUser() {
            int option;
            do {
                System.out.println("""
                    How difficult?
                    1. Easy
                    2. Medium
                    3. Hard
                    """);
                option = getIntInput("Your selection: ");
            } while (option < 1 || option > 3);

            sc.nextLine();

            return switch (option) {
                case 1 -> Difficulty.EASY;
                case 2 -> Difficulty.MEDIUM;
                case 3 -> Difficulty.HARD;
                default -> throw new IllegalStateException("Unexpected value: " + option);
            };


        }

        private int getIntInput(String message) {
            System.out.print(message);
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.next();
                System.out.print(message);
            }
            return sc.nextInt();
        }

}
