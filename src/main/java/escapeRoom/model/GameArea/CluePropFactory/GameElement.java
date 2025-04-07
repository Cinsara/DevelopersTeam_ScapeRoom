package escapeRoom.model.GameArea.CluePropFactory;

public interface GameElement {

    int getId();
    ElementType getType();
    String printElement();
    void setId(int generatedId);
    int getRoomId();
}
