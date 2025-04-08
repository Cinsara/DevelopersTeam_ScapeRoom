package escapeRoom.SetUp;

import escapeRoom.Controller.CertificateManager.CertificateController;
import escapeRoom.Controller.GameController.GameController;
import escapeRoom.Controller.InventoryController.InventoryController;
import escapeRoom.Controller.RewardController.RewardController;
import escapeRoom.Controller.TicketController.TicketController;
import escapeRoom.Service.InputService.InputCollector;
import escapeRoom.Service.InputService.InputService;

import java.sql.SQLException;

public class EscapeRoomControllers {
    public final CertificateController certificateController;
    public final RewardController rewardController;
    public final GameController gameController;
    public final TicketController ticketController;
    public final InventoryController inventoryController;

    public EscapeRoomControllers(InputService inputService, InputCollector inputCollector, EscapeRoomServices escapeRoomServices, EscapeRoomManagers escapeRoomManagers) throws SQLException {
        this.inventoryController = new InventoryController(escapeRoomManagers.roomManager);
        this.gameController = new GameController(escapeRoomManagers.gameManager,inputCollector);
        this.rewardController = new RewardController(escapeRoomServices.rewardService,escapeRoomServices.gameService,escapeRoomServices.userService);
        this.ticketController = new TicketController(inputService,escapeRoomManagers.ticketManager);
        this.certificateController = new CertificateController(inputService,inputCollector,escapeRoomManagers.certificateManager);
    }

}
