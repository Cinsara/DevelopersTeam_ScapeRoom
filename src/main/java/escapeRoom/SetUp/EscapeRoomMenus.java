package escapeRoom.SetUp;

import escapeRoom.Controller.CertificateManager.CertificateMenu;
import escapeRoom.Controller.GameController.GameMenu;
import escapeRoom.Controller.NotificationManager.NotificationMenu;
import escapeRoom.Controller.RoomManager.RoomMenu;
import escapeRoom.Controller.TicketController.TicketMenu;
import escapeRoom.Controller.UserManager.UserMenu;
import escapeRoom.Service.InputService.InputService;

public class EscapeRoomMenus {
    private final TicketMenu ticketMenu;
    private final GameMenu gameMenu;
    private final UserMenu userMenu;
    private final NotificationMenu notificationMenu;
    private final CertificateMenu certificateMenu;
    private final RoomMenu roomMenu;

    public EscapeRoomMenus(InputService inputService,EscapeRoomControllers escapeRoomControllers, EscapeRoomManagers escapeRoomManagers) {
        this.gameMenu = new GameMenu(inputService,escapeRoomControllers.getGameController());
        this.ticketMenu = new TicketMenu(inputService,escapeRoomControllers.getTicketController());
        this.userMenu = new UserMenu(inputService,escapeRoomManagers.getUserManager());
        this.notificationMenu = new NotificationMenu(inputService, escapeRoomManagers.getNotificationManager());
        this.certificateMenu = new CertificateMenu(inputService,escapeRoomManagers.getCertificateManager());
        this.roomMenu = new RoomMenu(inputService, escapeRoomManagers.getRoomManager());
    }

    public TicketMenu getTicketMenu() {
        return ticketMenu;
    }

    public GameMenu getGameMenu() {
        return gameMenu;
    }

    public UserMenu getUserMenu() {
        return userMenu;
    }

    public NotificationMenu getNotificationMenu() {
        return notificationMenu;
    }

    public CertificateMenu getCertificateMenu() {
        return certificateMenu;
    }

    public RoomMenu getRoomMenu() {
        return roomMenu;
    }
}
