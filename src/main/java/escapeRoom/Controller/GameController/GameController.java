package escapeRoom.Controller.GameController;

import escapeRoom.Controller.GameController.ControllerHelpers.GameCoordinates;
import escapeRoom.Controller.GameController.ControllerHelpers.GameManagerWrapper;
import escapeRoom.Controller.GameController.GameManager.GameManager;
import escapeRoom.Service.InputService.BackToSecondaryMenuException;
import escapeRoom.Service.InputService.InputCollector;
import escapeRoom.Model.GameArea.GameBuilder.Game;

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
        printNewBooking(gameManagerWrapper.pickGameAndRunManagerMethod(GameCoordinates::getFullCoordinates,gameManager::bookGame).returnCoordinates());
    }
    public void cancelBooking() throws BackToSecondaryMenuException {
        printCancellation(gameManagerWrapper.pickGameAndRunManagerMethod(GameCoordinates::getPartialCoordinates,gameManager::cancelBooking).returnCoordinates());

    }
    public void addPlayerToGame() throws BackToSecondaryMenuException {
        printNewPlayer(gameManagerWrapper.pickGameAndRunManagerMethod(GameCoordinates::getFullCoordinates,gameManager::addPlayerToGame).returnCoordinates());
    }

    public void removePlayerFromGame() throws BackToSecondaryMenuException {
        printRemovedPlayer(gameManagerWrapper.pickGameAndRunManagerMethod(GameCoordinates::getFullCoordinates,gameManager::removePlayerFromGame).returnCoordinates());
    }

    public void playGame() throws BackToSecondaryMenuException {
        Game playedGame = gameManagerWrapper.pickGameAndRunManagerMethod(GameCoordinates::getPartialCoordinates,gameManager::playGame).returnGame();
        printListGames("\t\t------ GAME SUMMARY  -------\n",List.of(playedGame));
    }
    public void showBookedGames() throws BackToSecondaryMenuException {
        List<Game> gamesToDisplay = gameManagerWrapper.showSelectedGames("Booked",gameManager::showBookedGames,gameManager::showBookedGames,gameManager::showBookedGames).returnListGame();
        printListGames("\t\t------ Booked Games -------\n", gamesToDisplay);
    }
    public void showAvailableGames() throws BackToSecondaryMenuException {
        List<Game> gamesToDisplay = gameManagerWrapper.showSelectedGames("Available",gameManager::showAvailableGames,gameManager::showAvailableGames,gameManager::showAvailableGames).returnListGame();
        printListGames("\t\t------ Available Games -------\n", gamesToDisplay);
    }

}
