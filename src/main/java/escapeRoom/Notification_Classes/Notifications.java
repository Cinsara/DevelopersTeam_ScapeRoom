package escapeRoom.Notification_Classes;

public class Notifications {
    private String id;
    private String content;

    public Notifications(String content){
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
