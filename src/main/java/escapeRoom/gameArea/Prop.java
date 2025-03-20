package escapeRoom.gameArea;

public class Prop {

        private static int counter = 0; // Shared counter for all instances
        private final int _id;
        private String name;
        private String type;


        public Prop(String name, String type) {
            this._id = ++counter;
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

    public String printProps() {

            String props = this._id +", "+ this.name +", "+ this.type;

            return props;

    }
    
}
