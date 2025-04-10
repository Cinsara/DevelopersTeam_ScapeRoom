package escapeRoom.Controller.GameController.Exceptions;

import escapeRoom.Model.GameArea.GameBuilder.Game;

public class GameNotBookedException extends Exception {
    public GameNotBookedException(Game game) {
        super("Game on " + game.getDate() + " in room " + game.getRoom_id() + "isn't booked.");
    }
}
