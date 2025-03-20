package escapeRoom.gameArea;

public class Clue {

    private static int counter = 0; // Shared counter for all instances
    private final int _id;
    private String name;
    private String type;


    public Clue(String name, String type) {
        this._id= ++counter;
        this.name= name;
        this.type = type;
    }

    public int get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String printClues() {

        String clues = this._id +", "+ this.name +", "+ this.type;

        return clues;

    }

}
