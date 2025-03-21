package escapeRoom.gameArea;

import java.util.HashMap;
import java.util.Map;

public class Prop {

        private static int counter = 0;
        private static final Map<Integer, Prop> propRegistry = new HashMap<>(); // Stores all props by ID. Will go after DB is implemented

        private final int _id;
        private String name;
        private String type;
        private double value;


        public Prop(String name, String type, double value) {
            this._id = ++counter;
            this.name= name;
            this.type = type;
            this.value = value;

            propRegistry.put(this._id, this);
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public static Double getValueById(int id) {
            Prop propToGetValue = propRegistry.get(id);

            return (propToGetValue!=null) ? propToGetValue.getValue() : null; // Return value if found, otherwise null
    }

    public String printProps() {

            String props = this._id +", "+ this.name +", "+ this.type +", "+ this.value;

            return props;

    }
    
}
