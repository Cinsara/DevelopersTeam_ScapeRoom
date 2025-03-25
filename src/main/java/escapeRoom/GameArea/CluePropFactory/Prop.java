package escapeRoom.GameArea.CluePropFactory;

public class Prop implements GameElement {

    private final PropType type;
    private int _id;
    private int room_room_id;

    public Prop(PropType type, int room_room_id) {
        this.type = type;
        this._id = room_room_id;
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

    @Override
    public void setId(int generatedId) {
        this._id = _id;
    }

    @Override
    public int getRoomId() {
        return this.room_room_id;
    }

    public double getValue(){
        return this.type.getValue();
    }

}
