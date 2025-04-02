package escapeRoom.Controller.TicketController;

import escapeRoom.Service.InputService.InputService;

import java.util.Scanner;

public class TicketController {
    private TicketManager ticketManager;
    private InputService inputService;
    public  TicketController(){
        this.ticketManager = new TicketManager();
        this.inputService = new InputService(new Scanner(System.in));
    }
    public void showSalesInventory(){
        ticketManager.showSalesInventory();
    }
    public void showYearlySalesInventory(){
        int year = inputService.readInt("Which year are you interested in?");
        ticketManager.showSalesInventory(year);
    }
}
