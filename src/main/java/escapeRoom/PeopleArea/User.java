package escapeRoom.PeopleArea;

import escapeRoom.Notification.Notification;
import escapeRoom.Notification.Observer;

import java.time.LocalDate;

public class User extends Person implements Observer {
    private LocalDate dateRegistration;
    private boolean notificationStatus;

    public User(int id,String name, String lastname, String email,
                String phoneNumber, LocalDate dob,
                boolean notificationStatus) {
        super(id,name, lastname, email, phoneNumber, dob);
        this.dateRegistration = LocalDate.now();
        this.notificationStatus = notificationStatus;
    }

    public User(String name, String lastname, String email,
                String phoneNumber, LocalDate dob,
                boolean notificationStatus){
        super(name, lastname, email, phoneNumber, dob);
        this.dateRegistration = LocalDate.now();
        this.notificationStatus = notificationStatus;
    }

    public LocalDate getDateRegistration() {
        return dateRegistration;
    }

    public boolean isNotificationStatus() {
        return notificationStatus;
    }

    public void setDateRegistration(LocalDate dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public void setNotificationStatus(boolean notificationStatus) {
        this.notificationStatus = notificationStatus;
    }


    @Override
    public void update(Notification notification){
        System.out.printf("%s have received a new notification: %s%n", this.getName(), notification.getContent());
    }

    @Override
    public String toString(){
        return "User ID: " + this.getId() + " | Name: " + this.getName() + " " + this.getLastname() + " | Email: "
                + this.getEmail() + " | Phone number: " + this.getPhoneNumber() + " | Date birth: " + this.getDob()
                + " | Date registration: " + this.getDateRegistration() + " | Notification status: " + this.isNotificationStatus();
    }
}
