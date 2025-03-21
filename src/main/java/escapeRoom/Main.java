package escapeRoom;

import escapeRoom.gameArea.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Clue clue1 = new Clue("clue1", "Indication");
        Clue clue2 = new Clue("clue2", "Enigma");
        Clue clue3 = new Clue("clue3", "Enigma");

        List<Integer> clues_id = new ArrayList<>();

        clues_id.add(clue1.get_id());
        clues_id.add(clue2.get_id());
        clues_id.add(clue3.get_id());

        Prop prop1 = new Prop("prop1", "Spade");
        Prop prop2 = new Prop("prop2", "Closet");
        Prop prop3 = new Prop("prop3", "Window");

        List<Integer> props_id = new ArrayList<>();

        props_id.add(prop1.get_id());
        props_id.add(prop2.get_id());
        props_id.add(prop3.get_id());

        RoomDirector roomDirector = new RoomDirector();
        RoomBuilder roomBuilder = new RoomBuilder();

        roomDirector.buildRoomLoveAffair(roomBuilder,"LoveEscape",clues_id,props_id);
        Room room1 = roomBuilder.getResult();

        System.out.println(room1.toString());

    }
}