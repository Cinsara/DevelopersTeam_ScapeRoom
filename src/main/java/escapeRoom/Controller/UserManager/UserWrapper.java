package escapeRoom.Controller.UserManager;

import escapeRoom.Model.PeopleArea.User;

import java.time.LocalDate;

public class UserWrapper {
    private String name, lastname, email, phoneNumber;
    private int id;
    private LocalDate dob;
    private LocalDate dateRegistration;
    private boolean notificationStatus;

    public UserWrapper(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.dob = user.getDob();
        this.dateRegistration = user.getDateRegistration();
        this.notificationStatus = user.isNotificationStatus();
    }
}
