package escapeRoom.gameArea;

public class Clue implements GameElement {

    private static int counter = 0; // Shared counter for all instances
    private final int _id;
    private String type;


    public Clue(String type) {
        this._id= ++counter;
        this.type = type;
    }

    @Override
    public int getId() {
        return _id;
    }

    @Override
    public String printElement() {
        return this._id +", " + this.type;
    }

    @Override
    public double getValue() {
        return 0;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
