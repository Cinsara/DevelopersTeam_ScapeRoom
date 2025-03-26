package escapeRoom.PeopleService.PeopleClasses_Testing;

import java.time.LocalDate;

public class User extends Person { //falta añadir: implements Observer
    private LocalDate dateRegistration;
    private boolean notificationStatus;

    public User(int id,String name, String lastname, LocalDate dob, String email,
                String phoneNumber, boolean notificationStatus) {
        super(id,name, lastname, dob, email, phoneNumber);
        this.dateRegistration = LocalDate.now();
        this.notificationStatus = notificationStatus;
    }

    public User(String name, String lastname, LocalDate dob, String email,
                String phoneNumber, boolean notificationStatus){
        super(name, lastname, dob, email, phoneNumber);
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

    // Se queda comentado hasta implementar la clase NotificationsArea, Notifications y Observer

   /* @Override
    public void update(Notification notification){
        System.out.printf("%s have received a new notification: %s%n", this.getName(), notification.getContent());
    } */

    @Override
    public String toString(){
        return "User ID: " + this.getId() + " | Name: " + this.getName() + " " + this.getLastname() + " | Email: "
                + this.getEmail() + " | Phone number: " + this.getPhoneNumber() + " | Date birth: " + this.getDob()
                + " | Date registration: " + this.getDateRegistration() + " | Notification status: " + this.isNotificationStatus();
    }
}
