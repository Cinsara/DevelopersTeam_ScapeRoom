package escapeRoom.Model.GameArea.CluePropFactory;

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

    public Prop(PropType type, int id, int room_room_id) {
        this.type = type;
        this.id = id;
        this.room_room_id = room_room_id;
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
        return this.id +", " + this.type +", Value: "+ getValue();
    }

    @Override
    public void setId(int generatedId) {
        this.id = generatedId;
    }

    @Override
    public int getRoomId() {
        return this.room_room_id;
    }

    public int getValue() { return this.type.getValue(); }

    @Override
    public String toString() {
        return "Prop ID: " + id +
                ", Type: " + type +
                ", Room ID: " + room_room_id +
                ", Value: " + value + "€";
    }
}
