package escapeRoom.GameArea.CluePropFactory;

public class PropFactory implements GameElementFactory {

    @Override
    public GameElement createGameElement(ElementType type, int room_room_id) {
        return new Prop((PropType) type, room_room_id);
    }
}
