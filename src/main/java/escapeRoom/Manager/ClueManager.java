package escapeRoom.Manager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.PropAndClueService.ClueService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.model.GameArea.CluePropFactory.*;
import escapeRoom.model.GameArea.RoomBuilder.Room;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClueManager {

    private final Connection connection = ConnectionManager.getConnection();
    private ClueService clueService;
    private InputService inputService;
    private GameElementFactory elementFactory;
    private RoomManager roomManager;

    public ClueManager(ClueService clueService, InputService inputService,
                       GameElementFactory elementFactory) throws SQLException {
        this.clueService = clueService;
        this.inputService = inputService;
        this.elementFactory = elementFactory;
    }

    public Clue create() throws SQLException{

        RoomService roomService = new RoomService();

        String opc = "yes";
        do {
            try {

                GameElementFactory clueFactory = ElementsFactoryProducer.getFactory("Clue");

                String type = (inputService.readString(("Enter Type (ENIGMA or INDICATION): "))).toUpperCase();
                ClueType typeEnum = null;

                switch (type) {
                    case "ENIGMA" -> typeEnum = ClueType.ENIGMA;
                    case "INDICATION" -> typeEnum = ClueType.INDICATION;
                }

                System.out.println("***AVAILABLE ROOMS***");

                try {
                    for (Room room : roomService.getAllEntities(connection)) {
                        roomService.read(room.getId()).ifPresent(System.out::println);
                    }
                } catch (SQLException e) {
                    System.out.println("Error retrieving all the rooms available: " + e.getMessage());
                }

                int newRoomId = inputService.readInt("Enter new Room ID for the Clue: ");

                Clue newClue = (Clue) clueFactory.createGameElement(typeEnum, newRoomId);

                clueService.create(newClue);

            } catch (SQLException e) {
                System.out.println("Error creating Clue: " + e.getMessage());
            }

            opc = inputService.readString("Do you want to create another one? y/n");

        } while (!opc.equals("no"));

        return null;
    }

    public Clue createInNewRoom(ClueType type, int roomId) throws SQLException{

        try {
            Clue newClue = (Clue) elementFactory.createGameElement(type,roomId);

            clueService.create(newClue);

            return newClue;

        } catch (SQLException e) {
            System.out.println("Error creating Clue: " + e.getMessage());
        }
        return null;
    }

    public Clue addCluesToRoom(int roomId) throws SQLException{

        String opc = "yes";
        do {
            try {

                GameElementFactory clueFactory = ElementsFactoryProducer.getFactory("Clue");

                String type = (inputService.readString(("Enter Type (ENIGMA or INDICATION): "))).toUpperCase();
                ClueType typeEnum = null;

                switch (type) {
                    case "ENIGMA" -> typeEnum = ClueType.ENIGMA;
                    case "INDICATION" -> typeEnum = ClueType.INDICATION;
                }

                Clue newClue = (Clue) clueFactory.createGameElement(typeEnum, roomId);

                clueService.create(newClue);

                System.out.println("New Clue created -> ID: " + newClue.getId() +
                        ", Type: " + newClue.getType() +
                        ", Room ID: " + newClue.getRoomId());

            } catch (SQLException e) {
                System.out.println("Error creating Clue: " + e.getMessage());
            }

            opc = inputService.readString("Do you want to create another one? y/n");

        } while (!opc.equals("no"));

        return null;
    }

    public void read(int id) throws SQLException {

        try {
            Optional<Clue> ClueOpt = clueService.read(id);
            if (ClueOpt.isEmpty()) {
                System.out.println("Clue with ID: " + id + " not found!");
            } else {
                ClueOpt.ifPresent(System.out::println);

            }

        } catch (SQLException e) {
            System.out.println("Error retrieving Clue from DB: " + e.getMessage());;
        }

    }

    public List<Clue> getAllClues() throws SQLException {

        List<Clue> clues = clueService.getAllEntities(connection);
        clues.forEach(System.out::println);

        return clues;
    }

    public void update(int id) throws SQLException {

        RoomService roomService = new RoomService();

        try {
            Optional<Clue> clueOpt = clueService.read(id);
            if (clueOpt.isEmpty()) {
                System.out.println("Clue not found with ID: " + id);
                return;
            }

            Clue existingClue = clueOpt.get();

            System.out.println("\nEnter new values (leave blank to keep current):");

            String newType = (inputService.readString(("Enter new Type (ENIGMA or INDICATION): "))).toUpperCase();
            ElementType newTypeEnum = null;

            switch(newType){
                case "ENIGMA" -> newTypeEnum = ClueType.ENIGMA;
                case "INDICATION" -> newTypeEnum = ClueType.INDICATION;
            }
            System.out.println();
            System.out.println("***AVAILABLE ROOMS***");

            try {
                for (Room room : roomService.getAllEntities(connection)) {
                    roomService.read(room.getId()).ifPresent(System.out::println);
                }
            } catch (SQLException e) {
                System.out.println("Error retrieving all the rooms available: " + e.getMessage());
            }

            int newRoomId = inputService.readInt("Enter new Room ID for the Clue: ");

            Clue updatedClue = new Clue(
                    (ClueType) (newType.isEmpty() ? existingClue.getType() : newTypeEnum),
                    id,
                    newRoomId==0 ? existingClue.getRoomId() : newRoomId);

            clueService.update(updatedClue);
            System.out.println("Clue updated successfully!");
            System.out.println(updatedClue);

        } catch (SQLException e) {
            System.out.println("Error updating room: " + e.getMessage());
        }

    }

    public void delete(int id) throws SQLException {

        try {
            Optional<Clue> clueOpt = clueService.read(id);
            if (clueOpt.isEmpty()) {
                System.out.println("Clue not found with ID: " + id);
            } else {
                clueService.delete(id);
                System.out.println("Clue with ID: " + id + " deleted!");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting Clue: " + e.getMessage());
        }
    }

    public void removeClueFromRoom(int roomId) throws SQLException {

        RoomService roomService = new RoomService();

        String opc = "yes";
        do {
            try {
                Optional<Room> optRoom = null;
                try {
                    optRoom = roomService.read(roomId);
                } catch (SQLException e) {
                    System.out.println("Error retrieving Room " + roomId + ": " + e.getMessage());
                }

                System.out.println("This Room has the following Clues: ");

                List<Integer> listClues = new ArrayList<>(optRoom.get().getClues_id());

                for (Integer clue : listClues) {
                    read(clue);
                }

                int clueIdToRemove = inputService.readInt("Which one do you want to remove?");

                clueService.delete(clueIdToRemove);

                System.out.println("Clue with ID " + clueIdToRemove + " has been removed." );


            } catch (SQLException e) {
                System.out.println("Error removing Clue: " + e.getMessage());
            }
            opc = inputService.readString("Do you want to remove another one? y/n");

        } while (!opc.equals("no"));

    }

}