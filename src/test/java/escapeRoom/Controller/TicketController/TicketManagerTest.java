package escapeRoom.Controller.TicketController;

import org.junit.jupiter.api.Test;

class TicketManagerTest {

    @Test
    void showSalesInventory() {
        TicketManager ticketManager = new TicketManager();
        ticketManager.showSalesInventory();
    }

    @Test
    void testShowSalesInventory() {
        TicketManager ticketManager = new TicketManager();
        ticketManager.showSalesInventory(2024);
    }
}