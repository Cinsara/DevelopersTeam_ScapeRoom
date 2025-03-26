package escapeRoom.GameArea.CluePropFactory;

public class Prop implements GameElement {

    private final PropType type;
    private int id;
    private int room_room_id;
    private int value;

    public Prop(PropType type, int room_room_id) {
        this.type = type;
        this.room_room_id = room_room_id;
        this.id = getId();
    }

    public Prop(PropType type, int id, int room_room_id, int value) {
        this.type = type;
        this.id = id;
        this.room_room_id = room_room_id;
        this.value = value;
    }

    public Prop(PropType type) {
        this.type = type;
    }

    @Override
    public int getId() {
        return id;
    }

    public PropType getType() {
        return type;
    }

    @Override
    public String printElement() {
        return this.id +", " + this.type +", Value: "+ getDBValue();
    }

    @Override
    public void setId(int generatedId) {
        this.id = generatedId;
    }

    @Override
    public int getRoomId() {
        return this.room_room_id;
    }

    public double getDBValue(){ return this.value; }

    public double getENUMValue() { return this.type.getValue(); }

    @Override
    public String toString() {
        return "Prop{" +
                "type=" + type +
                ", id=" + id +
                ", room_room_id=" + room_room_id +
                ", value=" + value +
                '}';
    }
}
