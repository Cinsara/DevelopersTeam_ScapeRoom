package escapeRoom.PeopleArea_Classes;

import java.time.LocalDate;

public class Captain extends User{
    private String ticket_id;

    public Captain(int id, String name, String lastname, String email, String phoneNumber,
                   LocalDate dob, LocalDate dateRegistration, boolean notificationStatus) {
        super(id,name, lastname, email, phoneNumber, dob, notificationStatus);
        this.ticket_id = ticket_id;
    }

    public Captain(String name, String lastname, String email, String phoneNumber,
                   LocalDate dob, LocalDate dateRegistration, boolean notificationStatus) {
        super(name, lastname, email, phoneNumber, dob, notificationStatus);
        this.ticket_id = ticket_id;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }
}
