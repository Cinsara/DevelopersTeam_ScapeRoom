package escapeRoom.Controller.GameController;

import escapeRoom.Service.AbsentEntityException;
import escapeRoom.Service.InputService.InputCollector;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.OutPutService.TablePrinter;
import escapeRoom.model.GameArea.GameBuilder.Game;
import escapeRoom.model.GameArea.RoomBuilder.Room;
import escapeRoom.model.PeopleArea.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class GameController {
    private GameManager gameManager;
    public GameController(){
        try{
            this.gameManager = new GameManager();
        } catch (SQLException | AbsentEntityException e) {
            System.out.println("Error:" +e.getMessage());
        }
    }

    public void bookGame(){

        try{
            LocalDate gameDate = InputCollector.getDate();
            Room room = InputCollector.getRoom();
            User captain = InputCollector.getTargetCostumer();
            if (gameManager.bookGame(gameDate, room.getId(), captain)){
                System.out.println("New game booked on the " + gameDate + " in room " + room.getName().toUpperCase() + " for customer " + captain.getName().toUpperCase() + " " + captain.getLastname().toUpperCase());
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void cancelBooking(){
        try{
            LocalDate gameDate = InputCollector.getDate();
            Room room = InputCollector.getRoom();
            if(gameManager.cancelBooking(gameDate,room.getId())){
                System.out.println("Booking on the " + gameDate + " in room " + room.getName().toUpperCase() + " cancelled.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void addPlayerToGame(){
        try {
            LocalDate gameDate = InputCollector.getDate();
            Room room = InputCollector.getRoom();
            User player = InputCollector.getTargetCostumer();

            if (gameManager.addPlayerToGame(gameDate,room.getId(), player)){
                System.out.println("Customer " + player.getName().toUpperCase()+ " "+ player.getLastname().toUpperCase()+" added to the game to be held on " + gameDate+ "in room " + room.getName().toUpperCase());
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void removePlayerFromGame(){

        try {
            LocalDate gameDate = InputCollector.getDate();
            Room room = InputCollector.getRoom();
            User player = InputCollector.getTargetCostumer();
            if (gameManager.removePlayerFromGame(gameDate,room.getId(),player)){
                System.out.println("Customer " + player.getName().toUpperCase()+ " "+ player.getLastname().toUpperCase()+" removed from the game to be held on " + gameDate + "in room " + room.getName().toUpperCase());
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void playGame(){

        try{
            LocalDate gameDate = InputCollector.getDate();
            Room room = InputCollector.getRoom();
            Game playedGame = gameManager.playGame(gameDate,room.getId());
            if (playedGame != null){
                String result = playedGame.isSuccess() ? "success":"failure";
                System.out.println("On the " + playedGame.getDate() + " a game was played in room " + playedGame.getRoom_id()+ ". Its result was a " + result + ", after " + playedGame.getEllapsedTimeInSeconds() + " seconds.");
            }
        }catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void showBookedGames(){
        int choice = chooseGamesClassification("booked");
        try {
            List<Game> gamesToDisplay = switch (choice) {
                case 1:
                    yield gameManager.showBookedGames();
                case 2:
                    yield gameManager.showBookedGames(InputCollector.getDate());
                case 3:
                    yield gameManager.showBookedGames(InputCollector.getRoom().getId());
                default:
                    yield new ArrayList<>();
            };

            if (gamesToDisplay.isEmpty()) {
                System.out.println("Nothing to display.");
            }else{
                System.out.println("\nBooked Games\n");
                System.out.println(TablePrinter.buildTable(gamesToDisplay));
            }
        }catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }

    }
    public void showAvailableGames(){
        int choice = chooseGamesClassification("available");
        try {
            List<Game> gamesToDisplay = switch (choice) {
                case 1:
                    yield gameManager.showAvailableGames();
                case 2:
                    yield gameManager.showAvailableGames(InputCollector.getDate());
                case 3:
                    yield gameManager.showAvailableGames(InputCollector.getRoom().getId());
                default:
                    yield new ArrayList<>();
            };

            if (gamesToDisplay.isEmpty()) {
                System.out.println("Nothing to display.");
            }else{
                System.out.println("\nAvailable Games\n");
                System.out.println(TablePrinter.buildTable(gamesToDisplay));
            }
        }catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    private int chooseGamesClassification(String bookedOrAvailable){
        InputService inputService = new InputService(new Scanner(System.in));
        String menuBasis = " ----\n Do you want to see \n ---- \n";
        StringBuilder listOptions = new StringBuilder();
        listOptions.append("1. All the ").append(bookedOrAvailable).append(" games\n").append("2. Only the ").append(bookedOrAvailable).append(" games on a given date\n").append("3. Only the ").append(bookedOrAvailable).append(" games in a given room.\n").append("4. Take me back!");
        int input;
        do {input = inputService.readInt(menuBasis + listOptions);} while(!new HashSet<>(List.of(1, 2, 3, 4)).contains(input));
        return input;
    }

}
