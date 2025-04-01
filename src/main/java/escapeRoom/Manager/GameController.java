package escapeRoom.Manager;

import java.sql.SQLException;

public class GameController {
    private GameManager gameManager;
    GameController(){
        try{
            this.gameManager = new GameManager();
        } catch (SQLException e) {
            System.out.println("Error:" +e.getMessage());
        }
    }

    public void bookGame(){
        GameCoordinates newCoordinates;
        int captainId;
        try{
            newCoordinates = GameInputCollector.getGameCoordinates();
            captainId = GameInputCollector.getTargetCostumer();
            if (gameManager.bookGame(newCoordinates.getDate(),newCoordinates.getRoomId(),captainId)){
                System.out.println("New game booked on the " + newCoordinates.getDate() + "in room " + newCoordinates.getRoomId() + "for customer " + captainId);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
