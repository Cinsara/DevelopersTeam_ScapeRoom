package escapeRoom.model.PeopleArea;

public class Captain extends User{
    private final int ticket_id;

    public Captain(User user, int ticket_id) {
        super(user.getId(), user.getName(), user.getLastname(), user.getEmail(), user.getPhoneNumber(), user.getDob(), user.isNotificationStatus());
        this.ticket_id = ticket_id;
    }

    public int getTicket_id() {
        return ticket_id;
    }
}
