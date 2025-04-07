package escapeRoom;

import escapeRoom.Service.AbsentEntityException;

import java.sql.SQLException;

public class Main {
    static public void main (String[] args){
        try{
            EscapeRoomInitializer newInitializer = new EscapeRoomInitializer();
            EscapeRoom escapeRoom = new EscapeRoom(newInitializer);
            escapeRoom.startEscapeRoom();
        } catch (SQLException | AbsentEntityException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
