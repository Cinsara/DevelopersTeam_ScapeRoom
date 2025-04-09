package escapeRoom.Controller.GameController.GameManager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Controller.GameController.Exceptions.GameAlreadyPlayed;
import escapeRoom.Controller.GameController.Exceptions.GameNotBookedException;
import escapeRoom.Controller.GameController.Exceptions.NoTicketException;
import escapeRoom.Model.AssetsArea.TicketBuilder.Ticket;
import escapeRoom.Model.GameArea.GameBuilder.Game;
import escapeRoom.Service.AssetService.TicketService;

import java.sql.SQLException;
import java.util.List;

public class GameManagerHelpers {
    static void validateGameCancellable(Game game) throws GameNotBookedException, GameAlreadyPlayed {
        if (game.getCaptainId()==null) {
            throw new GameNotBookedException(game);
        }
        if (game.getEllapsedTimeInSeconds()>0){
            throw new GameAlreadyPlayed(game.getDate(),game.getRoom_id());
        }
    }
    static Ticket getGameTicket(Game game, TicketService ticketService) throws SQLException, NoTicketException {
        List<Ticket> allTickets = ticketService.getAllEntities(ConnectionManager.getConnection());
        Ticket targetTicket = allTickets.stream().filter(ticket -> ticket.getGame_id() == game.getId()).findFirst().orElse(null);
        if (targetTicket == null){
            throw new NoTicketException(game);
        }
        return targetTicket;
    }
}
