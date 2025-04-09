package escapeRoom.SetUp;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Menus.CertificateMenu;
import escapeRoom.Menus.GameMenu;
import escapeRoom.Controller.InventoryController.InventoryController;
import escapeRoom.Menus.NotificationMenu;
import escapeRoom.Controller.RewardController.RewardController;
import escapeRoom.Menus.RoomMenu;
import escapeRoom.Menus.TicketMenu;
import escapeRoom.Menus.UserMenu;
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
        this.inputCollector = new InputCollector(inputService,escapeRoomServices.roomService,escapeRoomServices.userService);
        this.escapeRoomManagers = new EscapeRoomManagers(inputService,escapeRoomServices);
        this.escapeRoomControllers = new EscapeRoomControllers(inputService,inputCollector,escapeRoomServices,escapeRoomManagers);
        this.escapeRoomMenus = new EscapeRoomMenus(inputService,escapeRoomControllers,escapeRoomManagers);

    }

    public InputService getInputService() {
        return inputService;
    }

    public TicketMenu getTicketMenu() {
        return escapeRoomMenus.ticketMenu;
    }

    public GameMenu getGameMenu() {
        return escapeRoomMenus.gameMenu;
    }

    public RewardController getRewardController() {
        return escapeRoomControllers.rewardController;
    }

    public UserMenu getUserMenu() {
        return escapeRoomMenus.userMenu;
    }

    public NotificationMenu getNotificationMenu() {
        return escapeRoomMenus.notificationMenu;
    }

    public CertificateMenu getCertificateMenu() {
        return escapeRoomMenus.certificateMenu;
    }

    public InventoryController getInventoryController() {
        return escapeRoomControllers.inventoryController;
    }

    public RoomMenu getRoomMenu() {
        return escapeRoomMenus.roomMenu;
    }
}
