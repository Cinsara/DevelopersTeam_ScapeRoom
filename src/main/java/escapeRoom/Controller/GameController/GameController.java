package escapeRoom.Controller.GameController;

import escapeRoom.model.GameArea.GameBuilder.Game;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

        try{
            LocalDate gameDate = GameInputCollector.getGameDate();
            int roomId = GameInputCollector.getGameRoomId();
            int captainId = GameInputCollector.getTargetCostumer();
            if (gameManager.bookGame(gameDate,roomId,captainId)){
                System.out.println("New game booked on the " + gameDate + " in room " + roomId + " for customer " + captainId);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void cancelBooking(){
        try{
            LocalDate gameDate = GameInputCollector.getGameDate();
            int roomId = GameInputCollector.getGameRoomId();
            if(gameManager.cancelBooking(gameDate,roomId)){
                System.out.println("Booking on the " + gameDate + " in room " + roomId + " cancelled.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void addPlayerToGame(){
        try {
            LocalDate gameDate = GameInputCollector.getGameDate();
            int roomId = GameInputCollector.getGameRoomId();
            int playerId = GameInputCollector.getTargetCostumer();

            if (gameManager.addPlayerToGame(gameDate,roomId,playerId)){
                System.out.println("Customer number "+ playerId + " added to the game to be held on " + gameDate+ "in room " + roomId);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void removePlayerFromGame(){

        try {
            LocalDate gameDate = GameInputCollector.getGameDate();
            int roomId = GameInputCollector.getGameRoomId();
            int playerId = GameInputCollector.getTargetCostumer();
            if (gameManager.removePlayerFromGame(gameDate,roomId,playerId)){
                System.out.println("Customer number "+ playerId + " removed from the game to be held on " + gameDate + "in room " + roomId);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void playGame(){

        try{
            LocalDate gameDate = GameInputCollector.getGameDate();
            int roomId = GameInputCollector.getGameRoomId();
            Game playedGame = gameManager.playGame(gameDate,roomId);
            if (playedGame != null){
                String result = playedGame.isSuccess() ? "success":"failure";
                System.out.println("On the " + playedGame.getDate() + " a game was played in room " + playedGame.getRoom_id()+ ". Its result was a " + result + ", after " + playedGame.getEllapsedTimeInSeconds() + "seconds.");
            }
        }catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void showBookedGames(){
        int choice = GameInputCollector.chooseGamesClassification("booked");
        try {
            List<Game> gamesToDisplay = switch (choice) {
                case 1:
                    yield gameManager.showBookedGames();
                case 2:
                    yield gameManager.showBookedGames(GameInputCollector.getGameDate());
                case 3:
                    yield gameManager.showBookedGames(GameInputCollector.getGameRoomId());
                default:
                    yield new ArrayList<>();
            };

            if (gamesToDisplay.isEmpty()) {
                System.out.println("Nothing to display.");
            }else{
             gamesToDisplay.forEach(game -> System.out.println(game.toString()));
            }
        }catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }

    }
    public void showAvailableGames(){
        int choice = GameInputCollector.chooseGamesClassification("available");
        try {
            List<Game> gamesToDisplay = switch (choice) {
                case 1:
                    yield gameManager.showAvailableGames();
                case 2:
                    yield gameManager.showAvailableGames(GameInputCollector.getGameDate());
                case 3:
                    yield gameManager.showAvailableGames(GameInputCollector.getGameRoomId());
                default:
                    yield new ArrayList<>();
            };

            if (gamesToDisplay.isEmpty()) {
                System.out.println("Nothing to display.");
            }else{
                gamesToDisplay.forEach(game -> System.out.println(game.toString()));
            }
        }catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

}
