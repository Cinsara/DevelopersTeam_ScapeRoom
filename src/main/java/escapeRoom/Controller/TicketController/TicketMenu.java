package escapeRoom.Controller.TicketController;

import escapeRoom.Controller.GeneralMenu;
import escapeRoom.Service.InputService.InputService;

public class TicketMenu {
    private TicketController ticketController;
    private InputService inputService;

    public TicketMenu(InputService inputService){
        this.ticketController = new TicketController();
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
        return inputService.readInt(menu);
    }
    public void startticketMenu(){
        int option;
        do {
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
        } while(true);
    }
}
