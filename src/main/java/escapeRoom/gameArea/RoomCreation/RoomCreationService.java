package escapeRoom.gameArea.RoomCreation;

import escapeRoom.gameArea.CluePropFactory.*;
import escapeRoom.gameArea.RoomBuilder.*;

import java.util.ArrayList;
import java.util.List;

public class RoomCreationService {

    private static List<Room> roomList = new ArrayList<>();

    public void createRoom(String roomName, Theme theme, Difficulty difficulty,
                           int indications, int enigmas, int spades,
                           int closets, int mountains) {

        GameElementCreationService elementCreationService = new GameElementCreationService();

        // Create clues and props
        List<Integer> cluesId = elementCreationService.createClues(indications, enigmas);
        List<Integer> propsId = elementCreationService.createProps(spades, closets, mountains);

        // Room Creation
        RoomDirector roomDirector = new RoomDirector();
        RoomBuilder roomBuilder = new RoomBuilder();
        roomDirector.buildRoom(roomBuilder, roomName, theme, difficulty, cluesId, propsId);
        Room newRoom = roomBuilder.create();

        // Add to list
        addRoom(newRoom);
    }

    public void addRoom(Room room) {
        roomList.add(room);
        System.out.println("✅ Room added. Current size of Escape Room: " + roomList.size());
    }

    public List<Room> getAllRooms() {
        return roomList;
    }

    public Room getLastRoom() {
        return roomList.getLast();
    }


}