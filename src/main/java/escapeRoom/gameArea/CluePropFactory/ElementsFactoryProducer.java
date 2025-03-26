package escapeRoom.gameArea.CluePropFactory;

public class ElementsFactoryProducer {
    public static GameElementFactory getFactory(String factoryType) {
        switch (factoryType) {
            case "Clue":
                return new ClueFactory();
            case "Prop":
                return new PropFactory();
            default:
                throw new IllegalArgumentException("Unknown factory type: " + factoryType);
        }
    }
}

