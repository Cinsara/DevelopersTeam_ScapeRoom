package escapeRoom.Controller.GameController;

import escapeRoom.Model.GameArea.GameBuilder.Game;
import escapeRoom.Service.InputService.BackToSecondaryMenuException;
import escapeRoom.Service.InputService.InputCollector;
import escapeRoom.ThrowingFunction;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static escapeRoom.Controller.GameController.GameControllerHelpers.*;

public class GameManagerWrapper {
    private final InputCollector inputCollector;
    private Object storedReturnValue;
    private void setStoredReturnValue(Object storedReturnValue) {
        this.storedReturnValue = storedReturnValue;
    }
    public GameManagerWrapper(InputCollector inputCollector){
        this.inputCollector = inputCollector;
    }

     public GameManagerWrapper pickAndRunManagerMethod
            (ThrowingFunction<InputCollector, GameCoordinates> coordinatesGetter,
             ThrowingFunction<GameCoordinates,Object> managerMethod)
            throws BackToSecondaryMenuException {
        try{
            GameCoordinates newCoordinates = coordinatesGetter.apply(inputCollector);
            Object returnedValue = managerMethod.apply(newCoordinates);
            if (returnedValue.equals(true)) {
                setStoredReturnValue(newCoordinates);
            }else if(returnedValue instanceof Game){
                setStoredReturnValue(returnedValue);
            }
        }catch (Exception e){
            if (e instanceof SQLException) System.out.println("Error: " + e.getMessage());
            else if (e instanceof BackToSecondaryMenuException){
                throw (BackToSecondaryMenuException) e;
            }
        }
        return this;
    }
    public GameManagerWrapper pickAndRunManagerMethod
            (String bookedOrAvailable,
             Supplier<List<Game>> supplyAllGames,
             Function<LocalDate,List<Game>> filterByDate,
             Function<Integer,List<Game>> filterByRoom) throws BackToSecondaryMenuException{

        int choice = chooseGamesClassification(bookedOrAvailable.toLowerCase());
        try{
            List<Game> gamesToDisplay = getGamesToDisplay(choice,supplyAllGames,filterByDate,filterByRoom, inputCollector);
            if (gamesToDisplay.isEmpty()) {
                System.out.println("Nothing to display.");
            }else{
                setStoredReturnValue(gamesToDisplay);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return this;
    }
    public GameCoordinates returnCoordinates() throws BackToSecondaryMenuException {
        if (this.storedReturnValue instanceof GameCoordinates){
            return (GameCoordinates) this.storedReturnValue;
        }else{
            expressFailure();
            throw new BackToSecondaryMenuException();
        }
    }
    public Game returnGame() throws BackToSecondaryMenuException {
        if (this.storedReturnValue instanceof Game){
            return (Game) this.storedReturnValue;
        }else{
            expressFailure();
            throw new BackToSecondaryMenuException();
        }
    }
    public List<Game> returnListGame() throws BackToSecondaryMenuException{
        if (isGameList(this.storedReturnValue)){
                return (List<Game>) this.storedReturnValue; // unchecked cast, but safe after check
            }else{
            expressFailure();
            throw new BackToSecondaryMenuException();
        }
    }

}
