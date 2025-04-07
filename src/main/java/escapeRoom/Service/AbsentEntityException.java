package escapeRoom.Service;

public class AbsentEntityException extends Exception{
    public AbsentEntityException(int id,Class<?> type){
        super("No entity of type "+type.getName() +" with id number " + id + " in database");
    }
}
