package escapeRoom.gameArea.CluePropFactory;

public class Prop implements GameElement {

    private final PropType type;
    private int _id;

    public Prop(PropType type, int _id) {
        this.type = type;
        this._id = _id;
    }

    public Prop(PropType type) {
        this.type = type;
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
        return this._id +", " + this.type +", Value: "+ getValue();
    }

    public double getValue(){
        return this.type.getValue();
    }

}
