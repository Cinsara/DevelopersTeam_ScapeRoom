package escapeRoom.Controller.TicketController;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Controller.GameController.GameManager;
import escapeRoom.Service.AssetService.RewardService;
import escapeRoom.Service.AssetService.TicketService;
import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.ManyToManyService.GameHasUserService;
import escapeRoom.Service.ManyToManyService.GameUsesClueService;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.Service.RoomService.RoomService;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

class TicketManagerTest {

    @Test
    void showSalesInventory() throws SQLException{
        Connection connection = ConnectionManager.getConnection();
        TicketManager ticketManager = new TicketManager(new TicketService(ConnectionManager.getConnection()),new GameManager(new GameService(connection),new RoomService(connection),new TicketService(connection),new RewardService(connection),new UserService(connection),new GameHasUserService(connection),new GameUsesClueService(connection)));
        ticketManager.showSalesInventory();
    }

    @Test
    void testShowSalesInventory() throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        TicketManager ticketManager = new TicketManager(new TicketService(ConnectionManager.getConnection()),new GameManager(new GameService(connection),new RoomService(connection),new TicketService(connection),new RewardService(connection),new UserService(connection),new GameHasUserService(connection),new GameUsesClueService(connection)));
        ticketManager.showSalesInventory(2024);
    }
}
