package escapeRoom.Controller.GameController;

import escapeRoom.Controller.GameController.GameManager.GameManager;
import escapeRoom.Service.InputService.BackToSecondaryMenuException;
import escapeRoom.Service.InputService.InputCollector;
import escapeRoom.Model.GameArea.GameBuilder.Game;
import escapeRoom.Service.OutPutService.TablePrinter;

import java.util.List;

import static escapeRoom.Controller.GameController.GameOutput.*;

public class GameController {
    private final GameManager gameManager;
    private final GameManagerWrapper gameManagerWrapper;

    public GameController(GameManager gameManager, InputCollector inputCollector){
        this.gameManager = gameManager;
        this.gameManagerWrapper = new GameManagerWrapper(inputCollector);
    }
    public void bookGame() throws BackToSecondaryMenuException {
        printNewBooking(gameManagerWrapper.pickAndRunManagerMethod(GameCoordinates::getFullCoordinates,gameManager::bookGame).returnCoordinates());
    }
    public void cancelBooking() throws BackToSecondaryMenuException {
        printCancellation(gameManagerWrapper.pickAndRunManagerMethod(GameCoordinates::getPartialCoordinates,gameManager::cancelBooking).returnCoordinates());

    }
    public void addPlayerToGame() throws BackToSecondaryMenuException {
        printNewPlayer(gameManagerWrapper.pickAndRunManagerMethod(GameCoordinates::getFullCoordinates,gameManager::addPlayerToGame).returnCoordinates());
    }

    public void removePlayerFromGame() throws BackToSecondaryMenuException {
        printRemovedPlayer(gameManagerWrapper.pickAndRunManagerMethod(GameCoordinates::getFullCoordinates,gameManager::removePlayerFromGame).returnCoordinates());
    }

    public void playGame() throws BackToSecondaryMenuException {
        Game playedGame = gameManagerWrapper.pickAndRunManagerMethod(GameCoordinates::getPartialCoordinates,gameManager::playGame).returnGame();
        printListGames("\t\t------ GAME SUMMARY  -------\n",List.of(playedGame));
    }
    public void showBookedGames() throws BackToSecondaryMenuException {
        List<Game> gamesToDisplay = gameManagerWrapper.pickAndRunManagerMethod("Booked",gameManager::showBookedGames,gameManager::showBookedGames,gameManager::showBookedGames).returnListGame();
        printListGames("\t\t------ Booked Games -------\n", gamesToDisplay);
    }
    public void showAvailableGames() throws BackToSecondaryMenuException {
        List<Game> gamesToDisplay = gameManagerWrapper.pickAndRunManagerMethod("Available",gameManager::showAvailableGames,gameManager::showAvailableGames,gameManager::showAvailableGames).returnListGame();
        printListGames("\t\t------ Available Games -------\n", gamesToDisplay);
    }

}
