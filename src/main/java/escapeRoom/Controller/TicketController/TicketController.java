package escapeRoom.Controller.TicketController;

import escapeRoom.Service.InputService.BackToSecondaryMenuException;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.InputService.InputServiceManager;


public class TicketController {
    private TicketManager ticketManager;
    private InputService inputService;
    public  TicketController(InputService inputService,TicketManager ticketManager){
        this.ticketManager = ticketManager;
        this.inputService = inputService;
    }
    public void showSalesInventory(){
        ticketManager.showSalesInventory();
    }
    public void showYearlySalesInventory() throws BackToSecondaryMenuException {
        int year = inputService.readInt("Which year are you interested in?");
        ticketManager.showSalesInventory(year);
    }
}
