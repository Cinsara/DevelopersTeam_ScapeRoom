package escapeRoom.AssetsArea_Classes.Classes_testing.PersonClasses;
import java.time.LocalDate;

public abstract class Person {
    private String name, lastname, email, phoneNumber;
    private int id;
    private LocalDate dob;

    public Person(int id,String name, String lastname, String email,
                  String phoneNumber,LocalDate dob){
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
    }

    public Person(String name, String lastname, String email,
                  String phoneNumber,LocalDate dob){
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname(){
        return lastname;
    }

    public String getEmail(){
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
}