package escapeRoom.gameArea.CluePropFactory;

public class PropFactory implements GameElementFactory {

    @Override
    public GameElement createGameElement(ElementType type) {
        return new Prop((PropType) type);
    }
}
