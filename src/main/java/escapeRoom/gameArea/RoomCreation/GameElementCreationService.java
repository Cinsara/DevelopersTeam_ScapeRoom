package escapeRoom.gameArea.RoomCreation;

import escapeRoom.gameArea.CluePropFactory.ClueFactory;
import escapeRoom.gameArea.CluePropFactory.GameElement;
import escapeRoom.gameArea.CluePropFactory.PropFactory;

import java.util.ArrayList;
import java.util.List;

public class GameElementCreationService {

    public List<Integer> createClues(int indications, int enigmas) {
        List<GameElement> clues = new ArrayList<>();

        for (int i = 0; i < indications; i++) {
            clues.add(ClueFactory.createClue("Indication"));
        }
        for (int i = 0; i < enigmas; i++) {
            clues.add(ClueFactory.createClue("Enigma"));
        }

        List<Integer> cluesId = new ArrayList<>();
        for (GameElement clue : clues) {
            cluesId.add(clue.getId());
        }
        return cluesId;
    }

    public List<Integer> createProps(int spades, int closets, int mountains) {
        List<GameElement> props = new ArrayList<>();

        for (int i = 0; i < spades; i++) {
            props.add(PropFactory.createProp("Spade"));
        }
        for (int i = 0; i < closets; i++) {
            props.add(PropFactory.createProp("Closet"));
        }
        for (int i = 0; i < mountains; i++) {
            props.add(PropFactory.createProp("Mountain"));
        }

        List<Integer> propsId = new ArrayList<>();
        for (GameElement prop : props) {
            propsId.add(prop.getId());
        }
        return propsId;
    }
}
