package escapeRoom.Controller.GameController.Exceptions;

import escapeRoom.Model.GameArea.GameBuilder.Game;

public class NoTicketException extends Exception {
    public NoTicketException(Game game) {
        super("There is not ticket for game on " + game.getDate() + "in room " + game.getRoom_id()+ ".");
    }
}
