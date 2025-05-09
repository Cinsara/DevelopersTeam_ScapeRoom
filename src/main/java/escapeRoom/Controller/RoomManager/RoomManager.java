package escapeRoom.Controller.RoomManager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.InputService.BackToSecondaryMenuException;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.Model.GameArea.CluePropFactory.*;
import escapeRoom.Model.GameArea.RoomBuilder.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomManager {

    private final Connection connection = ConnectionManager.getConnection();
    private RoomService roomService;
    private InputService inputService;
    private GameElementFactory clueFactory;
    private GameElementFactory propFactory;
    private ClueManager clueManager;
    private PropManager propManager;

    public RoomManager(InputService inputService, RoomService roomService, ClueManager clueManager, PropManager propManager) throws SQLException {
        this.inputService = inputService;
        this.roomService = roomService;
        this.clueManager = clueManager;
        this.propManager = propManager;
        this.clueFactory = new ClueFactory();
        this.propFactory = new PropFactory();
    }

    public void createRoom() throws BackToSecondaryMenuException {

        try{
            int id = getNextRoomId();
            String newName = inputService.readString("Enter name:");
            String newTheme = (inputService.readString(("Enter Theme (LOVEAFFAIR, FANTASTIC, MYSTERY, SCIFI): "))).toUpperCase();
            Theme newThemeEnum = null;

            switch(newTheme){
                case "LOVEAFFAIR" -> newTheme = Theme.LOVEAFFAIR.getDisplayName();
                case "FANTASTIC" -> newTheme = Theme.FANTASTIC.getDisplayName();
                case "MYSTERY" -> newTheme = Theme.MYSTERY.getDisplayName();
                case "SCIFI" -> newTheme = Theme.SCIFI.getDisplayName();
            }

            String newDifficulty = (inputService.readString(("Enter new Difficulty (EASY, MEDIUM, HARD): "))).toUpperCase();
            Difficulty newDifficultyEnum = null;

            switch(newDifficulty){
                case "EASY" -> newDifficultyEnum = Difficulty.EASY;
                case "MEDIUM" -> newDifficultyEnum = Difficulty.MEDIUM;
                case "HARD" -> newDifficultyEnum = Difficulty.HARD;
            }

            Builder<Room> roomBuilder = new RoomBuilder();

            roomBuilder.setId(id);
            roomBuilder.setRoomName(newName);
            roomBuilder.setRoomTheme(newTheme);
            roomBuilder.setRoomDifficulty(newDifficultyEnum);

            Room newRoom = roomBuilder.build();

            roomService.create(newRoom);


            List<GameElement> clues = new ArrayList<>();

            int opc = inputService.readInt("How many Clues - Indications?\n");
            for (int i = 0; i < opc; i++) {clues.add(clueManager.createInNewRoom(ClueType.INDICATION, newRoom.getId()));}

            opc = inputService.readInt("How many Clues - Enigmas?\n");
            for (int i = 0; i < opc; i++) {clues.add(clueManager.createInNewRoom(ClueType.ENIGMA, newRoom.getId()));}

            List<Integer> newCluesId = new ArrayList<>();

            for (GameElement clue : clues) { newCluesId.add(clue.getId()); }

            newRoom.setClues_id(newCluesId);


            List<GameElement> props = new ArrayList<>();

            opc = inputService.readInt("How many Props - Spade?\n");
            for (int i = 0; i < opc; i++) {props.add(propManager.createInNewRoom(PropType.CLOSET, newRoom.getId()));}

            opc = inputService.readInt("How many Props - Closet?\n");
            for (int i = 0; i < opc; i++) {props.add(propManager.createInNewRoom(PropType.SPADE, newRoom.getId()));}

            opc = inputService.readInt("How many Props - Mountain?\n");
            for (int i = 0; i < opc; i++) {props.add(propManager.createInNewRoom(PropType.MOUNTAIN, newRoom.getId()));}

            List<Integer> newPropsId = new ArrayList<>();

            for (GameElement prop : props) { newPropsId.add(prop.getId()); }

            newRoom.setProps_id(newPropsId);


            System.out.println("Room created successfully!");
            System.out.println(newRoom);

        } catch (SQLException e) {
            System.out.println("Error updating room: " + e.getMessage());
        }
    }

    public void readRoom(int id) {
        try {
            Optional<Room> roomOpt = roomService.read(id);
            if (roomOpt.isEmpty()) {
                System.out.println("Room not found with ID: " + id);
            } else {
                System.out.println(roomOpt);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving Room from DB: " + e.getMessage());;
        }

    }

    public List<Room> getAllRooms() {

        try {
            return roomService.getAllEntities(connection);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;

    }

    public int getNextRoomId()  {
        Optional<Integer> maxId = getAllRooms().stream()
                .map(Room::getId)
                .max(Integer::compareTo);

        return maxId.orElse(0) + 1;
    }

    public void updateRoom() throws BackToSecondaryMenuException{


        try {
            List<Room> rooms = getAllRooms();
            for (Room room : rooms) {
                roomService.read(room.getId()).ifPresent(System.out::println);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving all the rooms available: " + e.getMessage());
        }

        int roomId = inputService.readInt("Which room do you want to update? (Enter the ID)");
        try {
            Optional<Room> roomOpt = roomService.read(roomId);
            if (roomOpt.isEmpty()) {
                System.out.println("Room not found with ID: " + roomId);
                return;
            }

            Room existingRoom = roomOpt.get();

            System.out.println("\nEnter new values (leave blank to keep current):");

            String newName = inputService.readString("Enter new name:");
            String newTheme = (inputService.readString(("Enter new Theme (LOVEAFFAIR, FANTASTIC, MYSTERY, SCIFI): "))).toUpperCase();

            switch (newTheme) {
                case "LOVEAFFAIR" -> newTheme = Theme.LOVEAFFAIR.getDisplayName();
                case "FANTASTIC" -> newTheme = Theme.FANTASTIC.getDisplayName();
                case "MYSTERY" -> newTheme = Theme.MYSTERY.getDisplayName();
                case "SCIFI" -> newTheme = Theme.SCIFI.getDisplayName();
            }

            String newDifficulty = (inputService.readString(("Enter new Difficulty (EASY, MEDIUM, HARD): "))).toUpperCase();
            Difficulty newDifficultyEnum = null;

            switch (newDifficulty) {
                case "EASY" -> newDifficultyEnum = Difficulty.EASY;
                case "MEDIUM" -> newDifficultyEnum = Difficulty.MEDIUM;
                case "HARD" -> newDifficultyEnum = Difficulty.HARD;
            }

            //UPDATE LISTA CLUES
            System.out.println("This Room has " + roomOpt.get().getClues_id().size() + " Clues: \n");

            for (Integer clueId : roomOpt.get().getClues_id()) {
                clueManager.read(clueId);
            }

            System.out.println();

            boolean addClues = inputService.readBoolean("Do you want to add more Clues? yes/no");

            List<Integer> newCluesId = null;
            if (addClues) {
                newCluesId = new ArrayList<>(roomOpt.get().getClues_id());

                for (Clue clue : clueManager.addCluesToRoom(roomId)) {
                    newCluesId.add(clue.getId());
                }
            }

            System.out.println();

            boolean removeClues = inputService.readBoolean("Do you want to remove Clues? yes/no");

            if (removeClues) {
              clueManager.removeClueFromRoom(roomId);
            }
            //UPDATE LISTA PROPS
            System.out.println("This Room has " + roomOpt.get().getProps_id().size() + " Props: \n");

            for (Integer propId : roomOpt.get().getProps_id()) {
                propManager.read(propId);
            }

            System.out.println();

            boolean addProps = inputService.readBoolean("Do you want to add more Props? yes/no");

            List<Integer> newPropsId = null;

            if (addProps) {
                newPropsId = new ArrayList<>(roomOpt.get().getProps_id());
                for (Prop prop : propManager.addPropsToRoom(roomId)) {
                    newPropsId.add(prop.getId());
                }
            }

            System.out.println();

            boolean removeProps = inputService.readBoolean("Do you want to remove Props? yes/no");

            if (removeProps) {
                propManager.removePropFromRoom(roomId);
            }

            if (newName.isEmpty() && newTheme.isEmpty() && newDifficulty.isEmpty() && !addClues && !removeClues && !addProps) {
                System.out.println("Nothing new to update. Room stays the same!");
            } else {

                Room updatedRoom = new Room(
                        roomId,
                        newName.isEmpty() ? existingRoom.getName() : newName,
                        newTheme.isEmpty() ? existingRoom.getTheme() : newTheme,
                        newDifficulty.isEmpty() ? existingRoom.getDifficulty() : newDifficultyEnum,
                        addClues ? existingRoom.getClues_id() : newCluesId,
                        addProps ? existingRoom.getProps_id() : newPropsId);

                roomService.update(updatedRoom);

                System.out.println("Room updated successfully!");

            }
            System.out.println();

            readRoom(roomId);

        } catch (SQLException e) {
            System.out.println("Error updating room: " + e.getMessage());
        }
    }


    public void deleteRoom() throws BackToSecondaryMenuException {

        getAllRooms();

        int roomId = inputService.readInt("Which room do you want to delete? (Enter the ID)");

        try {
            Optional<Room> roomOpt = roomService.read(roomId);
            if (roomOpt.isEmpty()) {
                System.out.println("Room not found with ID: " + roomId);
            } else {
                roomService.delete(roomId);
                System.out.println("Room with ID " + roomId + " deleted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting Room: " + e.getMessage());
        }
    }

    public List<List<?>>prepPrintableRooms() {
        try{
            List<Room> rooms = roomService.getAllEntities(roomService.getConnection());
            List<Prop> props = propManager.getAllProps();
            List<Clue> clues = clueManager.getAllClues();
            List<InventoryUtils.RoomWrapper> roomWrappers = InventoryUtils.wrapRooms(rooms,props,clues);
            return List.of(rooms,props,roomWrappers);
        } catch(SQLException e){
            System.out.println("Error :" + e.getMessage());
            return null;
        }
    }

}