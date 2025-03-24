package escapeRoom.gameArea.CluePropFactory;

import java.util.HashMap;
import java.util.Map;

public class Prop implements GameElement {

    private final PropType type;
    private int _id;

    public Prop(PropType type) {
        this.type = type;
    }

    public Prop(PropType type, int _id) {
        this.type = type;
        this._id = _id;
    }

    @Override
    public int getId() {
        return _id;
    }

    public PropType getType() {
        return type;
    }

    @Override
    public String printElement() {
        return this._id +", " + this.type +", "+ getValue();
    }

    public double getValue(){
        return this.type.getValue();
    }

}
