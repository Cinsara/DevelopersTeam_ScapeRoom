package escapeRoom.gameArea.CluePropFactory;

public enum PropType {
    SPADE(20.0),
    CLOSET(140.0),
    MOUNTAIN(70.5);

    private final double value;

    PropType(double value){
        this.value = value;
    }

    public double getValue() {
        return value;
    }


}
