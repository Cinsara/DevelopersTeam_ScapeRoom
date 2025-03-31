package escapeRoom.Manager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.GameService.ClueService;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.model.GameArea.CluePropFactory.ClueType;
import escapeRoom.model.GameArea.RoomBuilder.Theme;

import java.sql.Connection;
import java.sql.SQLException;

public class ClueManager {

    private final Connection connection = ConnectionManager.getConnection();
    private ClueService clueService;
    private InputService inputService;

    public ClueManager() throws SQLException {}

    public void create() throws SQLException{

        try {

            int id = 0;
            String newName = inputService.readString("Enter name:");
            String newType = (inputService.readString(("Enter Type (ENIGMA, INDICATION): "))).toUpperCase();
            ClueType newTypeEnum = null;

            switch(newType){
                case "ENIGMA" -> newTypeEnum = ClueType.ENIGMA;
                case "INDICATION" -> newTypeEnum = ClueType.INDICATION;
            }


        } catch (SQLException e) {
            System.out.println("Error creating Clue: " + e.getMessage());
        }

    }




    public void read(int id) {}

    public void update(int id){}

    public void delete(int id){}



}
