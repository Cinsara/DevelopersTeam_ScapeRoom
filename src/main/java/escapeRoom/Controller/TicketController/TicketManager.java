package escapeRoom.Controller.TicketController;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Controller.GameController.GameManager;
import escapeRoom.Service.AssetService.TicketService;
import escapeRoom.model.AssetsArea.TicketBuilder.Ticket;
import escapeRoom.model.GameArea.GameBuilder.Game;

import java.sql.SQLException;
import java.util.List;

public class TicketManager {
    private TicketService ticketService;
    public TicketManager(){
        try{
            this.ticketService = new TicketService();
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
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
            GameManager gameManager = new GameManager();
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
