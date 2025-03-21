package escapeRoom.gameArea;

public class ElementsFactoryProducer {
    public static Object getFactory(String factoryType) {
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

