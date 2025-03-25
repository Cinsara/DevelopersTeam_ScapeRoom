package escapeRoom.GameArea.CluePropFactory;

public class Clue implements GameElement {

    private final ClueType type;
    private int _id;
    private int room_room_id;

    public Clue(ClueType type, int room_room_id) {
        this.type = type; this.room_room_id = room_room_id;
    }

    public Clue(ClueType type, int room_room_id, int _id) {
        this.type = type; this.room_room_id = room_room_id; this._id = _id;
    }

    @Override
    public int getId() {
        return _id;
    }

    public ElementType getType() {
        return type;
    }

    @Override
    public String printElement() {
        return this._id +", " + this.type.name();
    }

    @Override
    public void setId(int generatedId) {
        this._id = generatedId;
    }

    @Override
    public int getRoomId() {
        return this.room_room_id;
    }

}
