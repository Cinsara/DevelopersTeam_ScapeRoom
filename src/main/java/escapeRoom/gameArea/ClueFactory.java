package escapeRoom.gameArea;

public class ClueFactory {

    public static GameElement createClue(String type) {
        return new Clue(type);
    }
}
