package escapeRoom;

import escapeRoom.gameArea.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //Clues are created with Factory Pattern
        GameElement clue1 = ClueFactory.createClue("Enigma");
        GameElement clue2 = ClueFactory.createClue("Indication");
        GameElement clue3 = ClueFactory.createClue("Indication");

        List<Integer> clues_id = new ArrayList<>();

        clues_id.add(clue1.getId());
        clues_id.add(clue2.getId());
        clues_id.add(clue3.getId());

        //Props as well the same way
        GameElement prop1 = PropFactory.createProp("Spade");
        GameElement prop2 = PropFactory.createProp("Closet");
        GameElement prop3 = PropFactory.createProp("Mountain");

        List<Integer> props_id = new ArrayList<>();

        props_id.add(prop1.getId());
        props_id.add(prop2.getId());
        props_id.add(prop3.getId());

        //Rooms are created with Builder pattern
        RoomDirector roomDirector = new RoomDirector();
        RoomBuilder roomBuilder = new RoomBuilder();

        roomDirector.buildRoomLoveAffair(roomBuilder,"LoveEscape",clues_id,props_id);
        Room room1 = roomBuilder.getResult();

        System.out.println(room1);

        System.out.println(room1.getRoomValue());

        System.out.println(prop3.getValue());
    }
}