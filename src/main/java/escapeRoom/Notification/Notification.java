package escapeRoom.Notification;

import java.time.LocalDate;

public class Notification {
    private int id;
    private final String content;
    private final LocalDate dataSent;

    public Notification(String content, LocalDate dataSent){
        this.content = content;
        this.dataSent = dataSent;
    }

    public Notification(int id, String content, LocalDate dataSent) {
        this.id = id;
        this.content = content;
        this.dataSent = dataSent;
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
