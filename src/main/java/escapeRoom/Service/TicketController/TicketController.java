package escapeRoom.Service.TicketController;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.AssetService.TicketService;
import escapeRoom.model.AssetsArea.TicketBuilder.Ticket;
import escapeRoom.model.GameArea.GameBuilder.Game;

import java.sql.SQLException;
import java.util.List;

public class TicketController {
    private TicketService ticketService;
    public TicketController(){
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
            listTickets.forEach(ticket -> System.out.println(ticket.toString()));
            System.out.println("""
                    ---------------------------------------
                    Total Sales
                    """ + totalSale);
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    public void showSalesInventory(int year){
        try{
            List<Ticket> listTickets = ticketService.getAllEntities(ConnectionManager.getConnection());
            GameManager gameManager = new GameManager();
            List<Game> listGames = gameManager.showBookedGames().filter(game->game.getDate().getYear() == year).toList();
            float totalSale = listTickets.stream().map(Ticket::getPrice).reduce(0F, Float::sum);
            listTickets.forEach(ticket -> System.out.println(ticket.toString()));
            System.out.println("""
                    ---------------------------------------
                    Total Sales
                    """ + totalSale);
        } catch (SQLException e) {
            System.out.println("Error" + e.getMessage());
        }
    }
}
