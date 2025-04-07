package escapeRoom.model.GameArea.CluePropFactory;

public interface GameElementFactory {
    GameElement createGameElement(ElementType type, int room_room_id);
}
