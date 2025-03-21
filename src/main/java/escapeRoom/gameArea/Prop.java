package escapeRoom.gameArea;

import java.util.HashMap;
import java.util.Map;

public class Prop implements GameElement {

    private static int counter = 0;
    private static final Map<Integer, Prop> propRegistry = new HashMap<>(); // Stores all props by ID. Will go after DB is implemented

    private final int _id;
    private String type;
    private final double spadeValue = 20.5;
    private final double closetValue = 120.5;
    private final double mountainValue = 300.3;

    public Prop(String type) {
        this._id = ++counter;
        this.type = type;

        propRegistry.put(this._id, this);
    }

    @Override
    public int getId() {
        return _id;
    }

    public String getType() {
        return type;
    }

    @Override
    public String printElement() {
        return this._id +", " + this.type +", "+ getValue();
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {

        double value = 0.0;

        switch(type){
            case "Spade":
                value = spadeValue;
                break;
            case "Closet":
                value = closetValue;
                break;
            case "Mountain":
                value = mountainValue;
                break;
        }
        return value;
    }

    public static Double getValueById(int id) {
        Prop propToGetValue = propRegistry.get(id);

        return (propToGetValue != null) ? propToGetValue.getValue() : null; // Return value if found, otherwise null
    }

}
