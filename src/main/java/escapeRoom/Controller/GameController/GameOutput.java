package escapeRoom.Controller.GameController;

import escapeRoom.Controller.GameController.ControllerHelpers.GameCoordinates;
import escapeRoom.Model.GameArea.GameBuilder.Game;
import escapeRoom.Service.OutPutService.TablePrinter;

import java.util.List;

public class GameOutput {
    static void printNewBooking(GameCoordinates gameCoordinates){
        System.out.println("New game booked on the "
                + gameCoordinates.gameDate + " in room "
                + gameCoordinates.gameRoom.getName().toUpperCase()
                + " for customer " + gameCoordinates.captain.getName().toUpperCase()
                + " " + gameCoordinates.captain.getLastname().toUpperCase());
    }
    static void printCancellation(GameCoordinates gameCoordinates){
        System.out.println("Booking for game on the "
                + gameCoordinates.gameDate + " in room "
                + gameCoordinates.gameRoom.getName().toUpperCase()
                + " cancelled.");
    }
    static void printNewPlayer(GameCoordinates gameCoordinates){
        System.out.println("Customer "
                + gameCoordinates.captain.getName().toUpperCase()+ " "+ gameCoordinates.captain.getLastname().toUpperCase()+
                " added to the game to be held on " + gameCoordinates.gameDate+ " in room "
                + gameCoordinates.gameRoom.getName().toUpperCase());
    }
    static void printRemovedPlayer(GameCoordinates gameCoordinates){
        System.out.println("Customer "
                + gameCoordinates.captain.getName().toUpperCase()+ " "+ gameCoordinates.captain.getLastname().toUpperCase()+
                " removed from the game to be held on " + gameCoordinates.gameDate+ " in room "
                + gameCoordinates.gameRoom.getName().toUpperCase());
    }
    static void printListGames(String header, List<Game> gamesToDisplay) {
        System.out.println(header);
        System.out.println(TablePrinter.buildTable(gamesToDisplay,false));
    }
}
