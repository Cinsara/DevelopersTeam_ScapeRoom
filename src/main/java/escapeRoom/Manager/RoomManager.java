package escapeRoom.Manager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.model.GameArea.CluePropFactory.*;
import escapeRoom.model.GameArea.GameBuilder.Game;
import escapeRoom.model.GameArea.RoomBuilder.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomManager {

    private final Connection connection = ConnectionManager.getConnection();
    private RoomService roomService;
    private InputService inputService;
    GameElementFactory elementFactory;
    ClueManager clueManager;

    public RoomManager() throws SQLException {
    }

    public void createRoom() throws SQLException {

        try{
            int id = getNextRoomId();
            String newName = inputService.readString("Enter name:");
            String newTheme = (inputService.readString(("Enter Theme (LOVEAFFAIR, FANTASTIC, MYSTERY, SCIFI): "))).toUpperCase();
            Theme newThemeEnum = null;

            switch(newTheme){
                case "LOVEAFFAIR" -> newThemeEnum = Theme.LOVEAFFAIR;
                case "FANTASTIC" -> newThemeEnum = Theme.FANTASTIC;
                case "MYSTERY" -> newThemeEnum = Theme.MYSTERY;
                case "SCIFI" -> newThemeEnum = Theme.SCIFI;
            }

            String newDifficulty = (inputService.readString(("Enter new Difficulty (EASY, MEDIUM, HARD): "))).toUpperCase();
            Difficulty newDifficultyEnum = null;

            switch(newDifficulty){
                case "EASY" -> newDifficultyEnum = Difficulty.EASY;
                case "MEDIUM" -> newDifficultyEnum = Difficulty.MEDIUM;
                case "HARD" -> newDifficultyEnum = Difficulty.HARD;
            }

            //LISTA CLUES
            List<GameElement> clues = new ArrayList<>();

            int opc = inputService.readInt("How many Clues - Indications?\n");
            for (int i = 0; i < opc; i++) {clues.add(elementFactory.createGameElement(ClueType.INDICATION, id));}

            opc = inputService.readInt("How many Clues - Enigmas?\n");
            for (int i = 0; i < opc; i++) {clues.add(elementFactory.createGameElement(ClueType.ENIGMA, id));}

            List<Integer> newCluesId = new ArrayList<>(); for (GameElement clue : clues) { newCluesId.add(clue.getId()); }


            //LISTA PROPS
            List<GameElement> props = new ArrayList<>();

            opc = inputService.readInt("How many Props - Spade?\n");
            for (int i = 0; i < opc; i++) {props.add(elementFactory.createGameElement(PropType.CLOSET, id));}

            opc = inputService.readInt("How many Props - Closet?\n");
            for (int i = 0; i < opc; i++) {props.add(elementFactory.createGameElement(PropType.SPADE, id));}

            opc = inputService.readInt("How many Props - Mountain?\n");
            for (int i = 0; i < opc; i++) {props.add(elementFactory.createGameElement(PropType.MOUNTAIN, id));}

            List<Integer> newPropsId = new ArrayList<>(); for (GameElement prop : props) { newPropsId.add(prop.getId()); }


            Builder<Room> roomBuilder = new RoomBuilder();

            roomBuilder.setId(id);
            roomBuilder.setRoomName(newName);
            roomBuilder.setRoomTheme(newThemeEnum);
            roomBuilder.setRoomDifficulty(newDifficultyEnum);
            roomBuilder.setRoomClues(newCluesId);
            roomBuilder.setRoomProps(newPropsId);

            Room newRoom = roomBuilder.build();

            roomService.create(new Room(newName,newThemeEnum,newDifficultyEnum,newCluesId, newPropsId));

            System.out.println("Room created successfully!");
            System.out.println(newRoom);

        } catch (SQLException e) {
            System.out.println("Error updating room: " + e.getMessage());
        }
    }

    public void readRoom(int id) throws SQLException {

        try {
            Optional <Room> roomOpt = roomService.read(id);
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

    public void updateRoom(int id) throws SQLException {

        try {
            Optional<Room> roomOpt = roomService.read(id);
            if (roomOpt.isEmpty()) {
                System.out.println("Room not found with ID: " + id);
                return;
            }

            System.out.println("\nEnter new values (leave blank to keep current):");

            Room existingRoom = roomOpt.get();

            String newName = inputService.readString("Enter new name:");
            String newTheme = (inputService.readString(("Enter new Theme (LOVEAFFAIR, FANTASTIC, MYSTERY, SCIFI): "))).toUpperCase();
            Theme newThemeEnum = null;

            switch(newTheme){
                case "LOVEAFFAIR" -> newThemeEnum = Theme.LOVEAFFAIR;
                case "FANTASTIC" -> newThemeEnum = Theme.FANTASTIC;
                case "MYSTERY" -> newThemeEnum = Theme.MYSTERY;
                case "SCIFI" -> newThemeEnum = Theme.SCIFI;
            }

            String newDifficulty = (inputService.readString(("Enter new Difficulty (EASY, MEDIUM, HARD): "))).toUpperCase();
            Difficulty newDifficultyEnum = null;

            switch(newDifficulty){
                case "EASY" -> newDifficultyEnum = Difficulty.EASY;
                case "MEDIUM" -> newDifficultyEnum = Difficulty.MEDIUM;
                case "HARD" -> newDifficultyEnum = Difficulty.HARD;
            }

            //UPDATE LISTA CLUES
            System.out.println("This Room has " + roomOpt.get().getClues_id().size() + " clues: \n");

            for (Integer clueid :  roomOpt.get().getClues_id()){
                clueManager.read(clueid);
            }

            boolean opc = inputService.readBoolean("Do you want to add more clues? Y/N");

            if (opc) {
                roomOpt.get().getClues_id().add(clueManager.create().getId());
            }


            //UPDATE LISTA PROPS
            System.out.println("This Room has " + roomOpt.get().getProps_id().size() +
                    " props: " + roomOpt.get().getProps_id().toString());


            List<GameElement> props = new ArrayList<>();
            opc = inputService.readInt("How many Spade props?\n");

            for (int i = 0; i < opc; i++) {
                props.add(propFactory.createGameElement(PropType.SPADE, roomManager.getNextRoomId()));
            }

            opc = inputService.readInt("How many Closet props?\n");
            for (int i = 0; i < opc; i++) {
                props.add(propFactory.createGameElement(PropType.CLOSET, roomManager.getNextRoomId()));
            }

            opc = inputService.readInt("How many Mountain props?\n");
            for (int i = 0; i < opc; i++) {
                props.add(propFactory.createGameElement(PropType.MOUNTAIN, roomManager.getNextRoomId()));
            }

            List<Integer> props_id = new ArrayList<>();
            for (GameElement prop : props) {
                props_id.add(prop.getId());
            }

            Room updatedRoom = new Room(
                    id,
                    newName.isEmpty() ? existingRoom.getName() : newName,
                    newTheme.isEmpty() ? existingRoom.getTheme() : newThemeEnum,
                    newDifficulty.isEmpty() ? existingRoom.getDifficulty() : newDifficultyEnum,
                    newCluesId.isEmpty() ? existingRoom.getClues_id() : newCluesId,
                    newPropsId.isEmpty() ? existingRoom.getProps_id() : newPropsId);

            roomService.update(updatedRoom);
            System.out.println("Room updated successfully!");
            System.out.println(updatedRoom);

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
            }
        } catch (SQLException e) {
            System.out.println("Error deleting Room: " + e.getMessage());
        }
    }
}
