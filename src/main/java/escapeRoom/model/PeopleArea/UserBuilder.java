package escapeRoom.model.PeopleArea;


import java.time.LocalDate;

public class UserBuilder {
    private String name, lastname, email, phoneNumber;
    private int id;
    private LocalDate dob;
    private LocalDate dateRegistration;
    private boolean notificationStatus;

    UserBuilder(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setId(int id) {
        this.id = id;
        return this;
    }
    public UserBuilder setDob(LocalDate dob) {
        this.dob = dob;
        return this;
    }
    public UserBuilder setDateRegistration(LocalDate dateRegistration) {
        this.dateRegistration = dateRegistration;
        return this;
    }
    public UserBuilder setNotificationStatus(boolean notificationStatus) {
        this.notificationStatus = notificationStatus;
        return this;
    }
    public User build(){
        return new User(this.id,this.name,this.lastname,this.email,this.phoneNumber,this.dob,this.notificationStatus);
    }
}


