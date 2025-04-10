package escapeRoom.Model.GameArea.CluePropFactory;

public enum PropType implements ElementType {
    SPADE(20),
    CLOSET(140),
    MOUNTAIN(70);

    private final int value;

    PropType(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }


}
