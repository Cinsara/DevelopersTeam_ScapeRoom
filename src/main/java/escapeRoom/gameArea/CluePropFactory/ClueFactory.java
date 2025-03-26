package escapeRoom.gameArea.CluePropFactory;

public class ClueFactory implements GameElementFactory {

    @Override
    public GameElement createGameElement(ElementType type) {
        return new Clue((ClueType) type);
    }
}
