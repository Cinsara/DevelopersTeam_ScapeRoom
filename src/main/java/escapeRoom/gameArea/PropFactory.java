package escapeRoom.gameArea;

public class PropFactory {

    public static GameElement createProp(String type) {
        return new Prop(type);
    }
}
