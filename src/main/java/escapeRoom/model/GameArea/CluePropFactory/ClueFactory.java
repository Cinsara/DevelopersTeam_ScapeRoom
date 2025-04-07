package escapeRoom.model.GameArea.CluePropFactory;

public class ClueFactory implements GameElementFactory {

    @Override
    public GameElement createGameElement(ElementType type, int room_room_id) {
        return new Clue((ClueType) type, room_room_id);
    }
}
