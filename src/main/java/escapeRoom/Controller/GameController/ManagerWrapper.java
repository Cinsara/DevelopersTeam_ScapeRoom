package escapeRoom.Controller.GameController;

import escapeRoom.Service.InputService.BackToSecondaryMenuException;
import escapeRoom.Service.InputService.InputCollector;
import escapeRoom.ThrowingFunction;

import java.sql.SQLException;
import java.util.Optional;

public class ManagerWrapper {
    private final InputCollector inputCollector;
    protected ManagerWrapper(InputCollector inputCollector){
        this.inputCollector = inputCollector;
    }
     Optional<GameCoordinates> runManagerMethod
            (ThrowingFunction<InputCollector,GameCoordinates> coordinatesGetter,
             ThrowingFunction<GameCoordinates,Boolean> managerMethod)
            throws BackToSecondaryMenuException {
        try{
            GameCoordinates newCoordinates = coordinatesGetter.apply(inputCollector);
            if (managerMethod.apply(newCoordinates)) {
                System.out.println("\nOperation successful.");
                return Optional.of(newCoordinates);
            }
            return Optional.empty();
        }catch (Exception e){
            if (e instanceof SQLException) System.out.println("Error: " + e.getMessage());
            else if (e instanceof BackToSecondaryMenuException){
                throw (BackToSecondaryMenuException) e;
            }
            return Optional.empty();
        }
    }
}
