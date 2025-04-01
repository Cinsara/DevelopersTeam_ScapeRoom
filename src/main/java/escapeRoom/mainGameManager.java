package escapeRoom;

import escapeRoom.Manager.GameInputCollector;

import java.sql.SQLException;

public class mainGameManager {
    static public void main (String[] args){
        try{
            GameInputCollector.getTargetCostumer();
        } catch (SQLException e) {

        }
    }
}
