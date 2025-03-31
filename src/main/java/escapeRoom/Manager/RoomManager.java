package escapeRoom.Manager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.RoomService.RoomService;
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

    public RoomManager() throws SQLException {
    }

    public void createRoom(int id, String roomName, Theme theme, Difficulty difficulty) throws SQLException {

        try{
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

            //TODO: UPDATE LISTA CLUES
            List<Integer> newCluesId = null;


            //TODO: UPDATE LISTA PROPS
            List<Integer> newPropsId = null;

            Room updatedRoom = new Room(
                    id,
                    newName,
                    newThemeEnum,
                    newDifficultyEnum,
                    newCluesId,
                    newPropsId);

            roomService.update(updatedRoom);
            System.out.println("Room created successfully!");
            System.out.println(updatedRoom);

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

            //TODO: UPDATE LISTA CLUES
            List<Integer> newCluesId = null;


            //TODO: UPDATE LISTA PROPS
            List<Integer> newPropsId = null;

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
