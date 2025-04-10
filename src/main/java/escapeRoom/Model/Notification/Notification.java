package escapeRoom.Model.Notification;

import java.time.LocalDate;

public class Notification {
    private int id;
    private final String content;
    private final LocalDate dateSent;

    public LocalDate getDateSent() {
        return dateSent;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Notification(String content, LocalDate dateSent){
        this.content = content;
        this.dateSent = dateSent;
    }

    public Notification(int id, String content, LocalDate dateSent) {
        this.id = id;
        this.content = content;
        this.dateSent = dateSent;
    }

    public int getId() {
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
