package escapeRoom.Manager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.model.GameArea.CluePropFactory.*;
import escapeRoom.model.GameArea.RoomBuilder.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomManager {

    private final Connection connection = ConnectionManager.getConnection();
    private RoomService roomService;
    private final InputService inputService;
    private GameElementFactory clueFactory;
    private GameElementFactory propFactory;
    private ClueManager clueManager;
    private PropManager propManager;

    public RoomManager(RoomService roomService, ClueManager clueManager,
                       PropManager propManager, InputService inputService,
                       GameElementFactory clueFactory, GameElementFactory propFactory ) throws SQLException {
        this.roomService = roomService;
        this.clueManager = clueManager;
        this.propManager = propManager;
        this.inputService = inputService;
        this.clueFactory = clueFactory;
        this.propFactory = propFactory;
    }

    public void createRoom() throws SQLException {

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
//            roomBuilder.setRoomClues(newCluesId);
//            roomBuilder.setRoomProps(newPropsId);

            Room newRoom = roomBuilder.build();

            roomService.create(newRoom);
            //roomService.create(new Room(newName,newThemeEnum,newDifficultyEnum));
            //roomService.create(new Room(newName,newThemeEnum,newDifficultyEnum,newCluesId, newPropsId));


            //LISTA CLUES
            List<GameElement> clues = new ArrayList<>();

            int opc = inputService.readInt("How many Clues - Indications?\n");
            for (int i = 0; i < opc; i++) {clues.add(clueManager.createInRoom(ClueType.INDICATION, newRoom.getId()));}

            opc = inputService.readInt("How many Clues - Enigmas?\n");
            for (int i = 0; i < opc; i++) {clues.add(clueManager.createInRoom(ClueType.ENIGMA, newRoom.getId()));}

            List<Integer> newCluesId = new ArrayList<>();

            for (GameElement clue : clues) { newCluesId.add(clue.getId()); }

            newRoom.setClues_id(newCluesId);


            //LISTA PROPS
            List<GameElement> props = new ArrayList<>();

            opc = inputService.readInt("How many Props - Spade?\n");
            for (int i = 0; i < opc; i++) {props.add(propManager.createInRoom(PropType.CLOSET, newRoom.getId()));}

            opc = inputService.readInt("How many Props - Closet?\n");
            for (int i = 0; i < opc; i++) {props.add(propManager.createInRoom(PropType.SPADE, newRoom.getId()));}

            opc = inputService.readInt("How many Props - Mountain?\n");
            for (int i = 0; i < opc; i++) {props.add(propManager.createInRoom(PropType.MOUNTAIN, newRoom.getId()));}

            List<Integer> newPropsId = new ArrayList<>();

            for (GameElement prop : props) { newPropsId.add(prop.getId()); }

            newRoom.setProps_id(newPropsId);


            System.out.println("Room created successfully!");
            System.out.println(newRoom);

        } catch (SQLException e) {
            System.out.println("Error updating room: " + e.getMessage());
        }
    }

    public void readRoom(int id) throws SQLException {

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

    public List<Room> getAllRooms() throws SQLException {

        return roomService.getAllEntities(connection);

    }

    public int getNextRoomId() throws SQLException {

        Optional<Integer> maxId = getAllRooms().stream()
                .map(Room::getId)
                .max(Integer::compareTo);

        return maxId.orElse(0) + 1;

    }

    public void updateRoom(int roomId) throws SQLException {

        try {
            Optional<Room> roomOpt = roomService.read(roomId);
            if (roomOpt.isEmpty()) {
                System.out.println("Room not found with ID: " + roomId);
                return;
            }

            System.out.println("\nEnter new values (leave blank to keep current):");

            Room existingRoom = roomOpt.get();

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

            boolean moreClues = inputService.readBoolean("Do you want to add more Clues? yes/no");

            List<Integer> newCluesId = null;
            if (moreClues) {
                newCluesId = new ArrayList<>(roomOpt.get().getClues_id());
                newCluesId.add(clueManager.create(roomId).getId());
            }


            //UPDATE LISTA PROPS
            System.out.println("This Room has " + roomOpt.get().getProps_id().size() + " Props: \n");

            for (Integer propId : roomOpt.get().getProps_id()) {
                propManager.read(propId);
            }

            System.out.println();

            boolean moreProps = inputService.readBoolean("Do you want to add more Props? yes/no");

            List<Integer> newPropsId = null;

            if (moreProps) {
                newPropsId = new ArrayList<>(roomOpt.get().getProps_id());
                newPropsId.add(propManager.create(roomId).getId());
            }

            if (newName.isEmpty() && newTheme.isEmpty() && newDifficulty.isEmpty() && !moreClues && !moreProps) {
                System.out.println("Nothing new to update. Room stays the same!");
            } else {

                Room updatedRoom = new Room(
                        roomId,
                        newName.isEmpty() ? existingRoom.getName() : newName,
                        newTheme.isEmpty() ? existingRoom.getTheme() : newTheme,
                        newDifficulty.isEmpty() ? existingRoom.getDifficulty() : newDifficultyEnum,
                        moreClues ? existingRoom.getClues_id() : newCluesId,
                        moreProps ? existingRoom.getProps_id() : newPropsId);

                roomService.update(updatedRoom);

                System.out.println("Room updated successfully!");

            }
            System.out.println();

            readRoom(roomId);

        } catch (SQLException e) {
            System.out.println("Error updating room: " + e.getMessage());
        }
    }

    public void deleteRoom(int id) throws SQLException {

        try {
            Optional<Room> roomOpt = roomService.read(id);
            if (roomOpt.isEmpty()) {
                System.out.println("Room not found with ID: " + id);
            } else {
                roomService.delete(id);
                System.out.println("Room with ID " + id + " deleted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting Room: " + e.getMessage());
        }
    }
}