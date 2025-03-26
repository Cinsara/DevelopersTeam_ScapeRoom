package escapeRoom.GameArea.CluePropFactory;

public class Clue implements GameElement {

    private final ClueType type;
    private int _id;

    public Clue(ClueType type, int _id) {
        this.type = type;
        this._id = _id;
    }

    public Clue(ClueType type) {
        this.type = type;
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



}
