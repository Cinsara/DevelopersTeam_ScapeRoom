package escapeRoom.SetUp;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Controller.CertificateManager.CertificateMenu;
import escapeRoom.Controller.GameController.GameMenu;
import escapeRoom.Controller.InventoryController.InventoryController;
import escapeRoom.Controller.NotificationManager.NotificationMenu;
import escapeRoom.Controller.RewardController.RewardController;
import escapeRoom.Controller.RoomManager.RoomMenu;
import escapeRoom.Controller.TicketController.TicketMenu;
import escapeRoom.Controller.UserManager.UserMenu;
import escapeRoom.Service.AbsentEntityException;
import escapeRoom.Service.InputService.InputCollector;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.InputService.InputServiceManager;

import java.sql.Connection;
import java.sql.SQLException;

public class EscapeRoomInitializer {
    private final Connection connection;
    private final InputService inputService;
    private final InputCollector inputCollector;
    private final EscapeRoomServices escapeRoomServices;
    private final EscapeRoomManagers escapeRoomManagers;
    private final EscapeRoomControllers escapeRoomControllers;
    private final EscapeRoomMenus escapeRoomMenus;

    public EscapeRoomInitializer() throws SQLException, AbsentEntityException {

        this.connection = ConnectionManager.getConnection();
        this.escapeRoomServices = new EscapeRoomServices(connection);
        this.inputService = InputServiceManager.getInputService();
        this.inputCollector = new InputCollector(inputService,escapeRoomServices.getRoomService(),escapeRoomServices.getUserService());
        this.escapeRoomManagers = new EscapeRoomManagers(inputService,inputCollector,escapeRoomServices);
        this.escapeRoomControllers = new EscapeRoomControllers(inputService,inputCollector,escapeRoomServices,escapeRoomManagers);
        this.escapeRoomMenus = new EscapeRoomMenus(inputService,escapeRoomControllers,escapeRoomManagers);

    }

    public InputService getInputService() {
        return inputService;
    }

    public TicketMenu getTicketMenu() {
        return escapeRoomMenus.getTicketMenu();
    }

    public GameMenu getGameMenu() {
        return escapeRoomMenus.getGameMenu();
    }

    public RewardController getRewardController() {
        return escapeRoomControllers.getRewardController();
    }

    public UserMenu getUserMenu() {
        return escapeRoomMenus.getUserMenu();
    }

    public NotificationMenu getNotificationMenu() {
        return escapeRoomMenus.getNotificationMenu();
    }

    public CertificateMenu getCertificateMenu() {
        return escapeRoomMenus.getCertificateMenu();
    }

    public InventoryController getInventoryController() {
        return escapeRoomControllers.getInventoryController();
    }

    public RoomMenu getRoomMenu() {
        return escapeRoomMenus.getRoomMenu();
    }
}
