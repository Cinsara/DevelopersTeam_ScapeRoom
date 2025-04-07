package escapeRoom.model.GameArea.CluePropFactory;

public interface GameElement {

    int getId();
    ElementType getType();
    String printElement();
    void setId(int generatedId);
    int getRoomId();
    default public String getTypeName(){
        if (getType() instanceof PropType){
            return ((PropType) getType()).name();
        }else if(getType() instanceof ClueType){
            return((ClueType) getType()).name();
        }
        return "Unknown Type";
    }
}
