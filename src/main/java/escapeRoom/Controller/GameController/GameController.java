package escapeRoom.Controller.GameController;

import escapeRoom.Service.InputService.BackToSecondaryMenuException;
import escapeRoom.Service.InputService.InputCollector;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.InputService.InputServiceManager;
import escapeRoom.Model.GameArea.GameBuilder.Game;
import escapeRoom.Service.OutPutService.TablePrinter;
import escapeRoom.ThrowingFunction;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class GameController {
    private final GameManager gameManager;
    private final InputCollector inputCollector;
    private final ManagerWrapper managerWrapper;

    public GameController(GameManager gameManager, InputCollector inputCollector){
        this.inputCollector = inputCollector;
        this.gameManager = gameManager;
        this.managerWrapper = new ManagerWrapper(inputCollector);
    }

    public void bookGame() throws BackToSecondaryMenuException {
        Optional<GameCoordinates> coordinates = managerWrapper.runManagerMethod(GameCoordinates::getFullCoordinates,gameManager::bookGame);
        coordinates.ifPresent(gameCoordinates -> System.out.println("New game booked on the "
                + gameCoordinates.gameDate + " in room "
                + gameCoordinates.gameRoom.getName().toUpperCase()
                + " for customer " + gameCoordinates.captain.getName().toUpperCase()
                + " " + gameCoordinates.captain.getLastname().toUpperCase()));
    }
    public void cancelBooking() throws BackToSecondaryMenuException {
        Optional<GameCoordinates> coordinates = managerWrapper.runManagerMethod(GameCoordinates::getPartialCoordinates,gameManager::cancelBooking);
        coordinates.ifPresent(gameCoordinates -> System.out.println("New game booked on the "
                + gameCoordinates.gameDate + " in room "
                + gameCoordinates.gameRoom.getName().toUpperCase()
                + " for customer " + gameCoordinates.captain.getName().toUpperCase()
                + " " + gameCoordinates.captain.getLastname().toUpperCase()));
    }
    public void addPlayerToGame() throws BackToSecondaryMenuException {
        Optional<GameCoordinates> coordinates = managerWrapper.runManagerMethod(GameCoordinates::getFullCoordinates,gameManager::addPlayerToGame);
        coordinates.ifPresent(gameCoordinates-> System.out.println("Customer "
                + gameCoordinates.captain.getName().toUpperCase()+ " "+ gameCoordinates.captain.getLastname().toUpperCase()+
                " added to the game to be held on " + gameCoordinates.gameDate+ "in room "
                + gameCoordinates.gameRoom.getName().toUpperCase()));
    }
    public void removePlayerFromGame() throws BackToSecondaryMenuException {
        Optional<GameCoordinates> coordinates = managerWrapper.runManagerMethod(GameCoordinates::getFullCoordinates,gameManager::removePlayerFromGame);
        coordinates.ifPresent(gameCoordinates-> System.out.println("Customer "
                + gameCoordinates.captain.getName().toUpperCase()+ " "+ gameCoordinates.captain.getLastname().toUpperCase()+
                " removed from the game to be held on " + gameCoordinates.gameDate+ "in room "
                + gameCoordinates.gameRoom.getName().toUpperCase()));
    }
    public void playGame() throws BackToSecondaryMenuException {
        try{
            GameCoordinates newCoordinates = GameCoordinates.getPartialCoordinates(inputCollector);
            Game playedGame = gameManager.playGame(newCoordinates);
            if (playedGame != null){
                System.out.println("                  ------------------- GAME SUMMARY  --------------------\n");
                System.out.println(TablePrinter.buildTable(List.of(playedGame),false));
            }
        }catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void showBookedGames() throws BackToSecondaryMenuException {
        showGames("Booked",gameManager::showBookedGames,gameManager::showBookedGames,gameManager::showBookedGames);
    }
    public void showAvailableGames() throws BackToSecondaryMenuException {
        showGames("Availabe",gameManager::showAvailableGames,gameManager::showAvailableGames,gameManager::showAvailableGames);
    }
    private void showGames(String bookedOrAvailable,Supplier<List<Game>> empty,Function<LocalDate,List<Game>> withDate ,Function<Integer,List<Game>> withRoom) throws BackToSecondaryMenuException {
        int choice = chooseGamesClassification(bookedOrAvailable.toLowerCase());
        try{
            List<Game> gamesToDisplay = getGamesToDisplay(choice,empty,withDate,withRoom);
            if (gamesToDisplay.isEmpty()) {
                System.out.println("Nothing to display.");
            }else{
                System.out.println("\n" + bookedOrAvailable + " Games\n");
                System.out.println(TablePrinter.buildTable(gamesToDisplay,false));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private int chooseGamesClassification(String bookedOrAvailable) throws BackToSecondaryMenuException {
        InputService inputService = InputServiceManager.getInputService();
        String menuBasis = " ----\n Do you want to see \n ---- \n";
        StringBuilder listOptions = new StringBuilder();
        listOptions.append("1. All the ").append(bookedOrAvailable).append(" games\n").append("2. Only the ").append(bookedOrAvailable).append(" games on a given date\n").append("3. Only the ").append(bookedOrAvailable).append(" games in a given room.\n").append("4. Take me back!");
        int input;
        do {input = inputService.readInt(menuBasis + listOptions);} while(!new HashSet<>(List.of(1, 2, 3, 4)).contains(input));
        return input;
    }
    private List<Game> getGamesToDisplay(int choice, Supplier<List<Game>> allGamesSupplier,Function<LocalDate,List<Game>> withDate ,Function<Integer,List<Game>> withRoom) throws BackToSecondaryMenuException, SQLException {
        return switch (choice){
            case 1:
                yield allGamesSupplier.get();
            case 2:
                yield withDate.apply(inputCollector.getDate());
            case 3:
                yield withRoom.apply(inputCollector.getRoom().getId());
            default:
                yield new ArrayList<>();
        };
    }
}
