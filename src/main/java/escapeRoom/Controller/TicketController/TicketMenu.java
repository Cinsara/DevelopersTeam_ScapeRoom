package escapeRoom.Controller.TicketController;

import escapeRoom.Service.InputService.BackToSecondaryMenuException;
import escapeRoom.Service.InputService.InputService;

public class TicketMenu {
    private TicketController ticketController;
    private InputService inputService;

    public TicketMenu(InputService inputService, TicketController ticketController){
        this.ticketController = ticketController;
        this.inputService = inputService;
    }

    public int principalTicketMenu(){
        String menu = """
                ------
                Inventory menu:
                ------
                1. General sale inventory
                2. Yearly sale inventory
                0. Back to the main menu.
                ------""";
        try{
            return inputService.readInt(menu);
        } catch (BackToSecondaryMenuException e) {
            return 0;
        }
    }
    public void startTicketMenu(){
        int option;
        do {
            try{
                option = principalTicketMenu();
                switch(option){
                    case 1 -> ticketController.showSalesInventory();
                    case 2 -> ticketController.showYearlySalesInventory();
                    case 0 -> {
                        System.out.println("Returning to the main menu.");
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }catch(BackToSecondaryMenuException e){}
        } while(true);
    }
}
