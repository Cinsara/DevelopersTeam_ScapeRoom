package escapeRoom.PeopleService;

import escapeRoom.PeopleService.PeopleClasses_Testing.User;
import escapeRoom.PeopleService.UserService;

import java.sql.SQLException;
import java.time.LocalDate;

public class Controller_test {
    public static void start() throws SQLException {
        UserService userService = new UserService();

        User u1 = new User(
                "Ali",
                "Alicia",
                LocalDate.of(1999,12,2),
                "gmail.com",
                "9866363663",
                true
        );

        userService.read(5);
    }
}
