package escapeRoom.Notification;

public class Notification {
    private String id;
    private String content;

    public Notification(String content){
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString(){
        return content;
    }
}
