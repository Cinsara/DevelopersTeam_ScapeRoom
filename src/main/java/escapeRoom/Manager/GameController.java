package escapeRoom.Manager;

import escapeRoom.model.GameArea.GameBuilder.Game;

import java.sql.SQLException;

public class GameController {
    private GameManager gameManager;
    public GameController(){
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
                System.out.println("New game booked on the " + newCoordinates.getDate() + " in room " + newCoordinates.getRoomId() + " for customer " + captainId);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void addPlayerToGame(){
        GameCoordinates newCoordinates;
        int playerId;
        try {
            newCoordinates = GameInputCollector.getGameCoordinates();
            playerId = GameInputCollector.getTargetCostumer();
            if (gameManager.addPlayerToGame(newCoordinates.getDate(),newCoordinates.getRoomId(),playerId)){
                System.out.println("Customer number "+ playerId + " added to the game to be held on " + newCoordinates.getDate() + "in room " + newCoordinates.getRoomId());
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void removePlayerFromGame(){
        GameCoordinates newCoordinates;
        int playerId;
        try {
            newCoordinates = GameInputCollector.getGameCoordinates();
            playerId = GameInputCollector.getTargetCostumer();
            if (gameManager.removePlayerFromGame(newCoordinates.getDate(),newCoordinates.getRoomId(),playerId)){
                System.out.println("Customer number "+ playerId + " removed from the game to be held on " + newCoordinates.getDate() + "in room " + newCoordinates.getRoomId());
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void playGame(){
        GameCoordinates newCoordinates;
        try{
            newCoordinates = GameInputCollector.getGameCoordinates();
            Game playedGame = gameManager.playGame(newCoordinates.getDate(),newCoordinates.getRoomId());
            if (playedGame != null){
                String result = playedGame.isSuccess() ? "success":"failure";
                System.out.println("On the " + playedGame.getDate() + " a game was played in room " + playedGame.getRoom_id()+ ". Its result was a " + result + ", after " + playedGame.getEllapsedTimeInSeconds() + "seconds.");
            }
        }catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
