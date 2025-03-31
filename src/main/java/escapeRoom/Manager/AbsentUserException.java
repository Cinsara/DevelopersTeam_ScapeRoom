package escapeRoom.Manager;

public class AbsentUserException extends Exception{
    AbsentUserException(int id){
        super("No user with id number " + id + " in database");
    }
}
