package escapeRoom.Controller.TicketController;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Controller.GameController.GameManager;
import escapeRoom.Service.AssetService.TicketService;
import escapeRoom.Model.AssetsArea.TicketBuilder.Ticket;
import escapeRoom.Model.GameArea.GameBuilder.Game;

import java.sql.SQLException;
import java.util.List;

public class TicketManager {
    private TicketService ticketService;
    private GameManager gameManager;
    public TicketManager(TicketService ticketService, GameManager gameManager){
        this.ticketService = ticketService;
        this.gameManager = gameManager;
    }
    public void showSalesInventory(){
        try{
            List<Ticket> listTickets = ticketService.getAllEntities(ConnectionManager.getConnection());
            float totalSale = listTickets.stream().map(Ticket::getPrice).reduce(0F, Float::sum);
            listTickets.forEach(ticket -> System.out.println("Ticket number " + ticket.getId()+ " for game number " + ticket.getGame_id() +"with price "+ticket.getPrice() +" held by user number " + ticket.getUser_id()));
            System.out.println("\n---------------------------\nTotal Sales\n" + totalSale);
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    public void showSalesInventory(int year){
        try{
            List<Integer> listGameIds = gameManager.showBookedGames().stream().filter(game->game.getDate().getYear() == year).map(Game::getId).toList();
            List<Ticket> listTickets = ticketService.getAllEntities(ConnectionManager.getConnection()).stream().filter(ticket->listGameIds.contains(ticket.getGame_id())).toList();
            float totalSale = listTickets.stream().map(Ticket::getPrice).reduce(0F, Float::sum);
            listTickets.forEach(ticket -> System.out.println("Ticket number " + ticket.getId()+ " for game number " + ticket.getGame_id() + " held by user number " + ticket.getUser_id()));
            System.out.println("\n---------------------------\nTotal Sales for year "+ year +"\n" + totalSale);
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
    }
}
