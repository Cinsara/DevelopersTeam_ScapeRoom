package escapeRoom.Controller.TicketController;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Controller.GameController.GameManager.GameManager;
import escapeRoom.SetUp.EscapeRoomServices;
import escapeRoom.Service.AssetService.TicketService;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

class TicketManagerTest {

    @Test
    void showSalesInventory() throws SQLException{
        Connection connection = ConnectionManager.getConnection();
        TicketManager ticketManager = new TicketManager(new TicketService(ConnectionManager.getConnection()),new GameManager(new EscapeRoomServices(connection).getServicesForGameManager()));
        ticketManager.showSalesInventory();
    }

    @Test
    void testShowSalesInventory() throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        TicketManager ticketManager = new TicketManager(new TicketService(ConnectionManager.getConnection()),new GameManager(new EscapeRoomServices(connection).getServicesForGameManager()));
        ticketManager.showSalesInventory(2024);
    }
}
