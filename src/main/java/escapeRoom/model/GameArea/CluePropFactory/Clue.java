package escapeRoom.model.GameArea.CluePropFactory;

public class Clue implements GameElement {

    private final ClueType type;
    private int id;
    private int room_room_id;

    public Clue(ClueType type, int room_room_id) {
        this.type = type; this.room_room_id = room_room_id;
    }

    public Clue(ClueType type, int _id, int room_room_id) {
        this.type = type; this.room_room_id = room_room_id; this.id = _id;
    }

    @Override
    public int getId() {
        return this.id;
    }

    public ElementType getType() {
        return this.type;
    }

    @Override
    public String printElement() {
        return this.id +", " + this.type.name();
    }

    @Override
    public void setId(int generatedId) {
        this.id = generatedId;
    }

    @Override
    public int getRoomId() {
        return this.room_room_id;
    }

    @Override
    public String toString() {
        return "Clue{" +
                "type=" + type +
                ", id=" + id +
                ", room_room_id=" + room_room_id +
                '}';
    }
}
