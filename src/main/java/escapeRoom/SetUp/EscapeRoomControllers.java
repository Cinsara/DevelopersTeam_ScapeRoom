package escapeRoom.SetUp;

import escapeRoom.Controller.GameController.GameController;
import escapeRoom.Controller.InventoryController.InventoryController;
import escapeRoom.Controller.RewardController.RewardController;
import escapeRoom.Controller.TicketController.TicketController;
import escapeRoom.Service.InputService.InputCollector;
import escapeRoom.Service.InputService.InputService;

public class EscapeRoomControllers {
    private final RewardController rewardController;
    private final GameController gameController;
    private final TicketController ticketController;
    private final InventoryController inventoryController;

    public EscapeRoomControllers(InputService inputService, InputCollector inputCollector, EscapeRoomServices escapeRoomServices, EscapeRoomManagers escapeRoomManagers) {
        this.inventoryController = new InventoryController(escapeRoomServices.getRoomService(),escapeRoomServices.getPropService(),escapeRoomServices.getClueService());
        this.gameController = new GameController(escapeRoomManagers.getGameManager(),inputCollector);
        this.rewardController = new RewardController(escapeRoomServices.getRewardService(),escapeRoomServices.getGameService(),escapeRoomServices.getUserService());
        this.ticketController = new TicketController(inputService,escapeRoomManagers.getTicketManager());
    }

    public RewardController getRewardController() {
        return rewardController;
    }

    public GameController getGameController() {
        return gameController;
    }

    public TicketController getTicketController() {
        return ticketController;
    }

    public InventoryController getInventoryController() {
        return inventoryController;
    }
}
