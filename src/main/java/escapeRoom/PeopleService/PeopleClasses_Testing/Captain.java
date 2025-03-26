package escapeRoom.PeopleService.PeopleClasses_Testing;

import java.time.LocalDate;

public class Captain extends User{
    private String ticket_id;

    public Captain(int id, String name, String lastname, LocalDate dob, String email, String phoneNumber,
                   boolean notificationStatus, LocalDate dateRegistration) {
        super(id,name, lastname, dob, email, phoneNumber, notificationStatus);
        this.ticket_id = ticket_id;
    }

    public Captain(String name, String lastname, LocalDate dob, String email, String phoneNumber,
                   boolean notificationStatus, LocalDate dateRegistration) {
        super(name, lastname, dob, email, phoneNumber, notificationStatus);
        this.ticket_id = ticket_id;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }
}