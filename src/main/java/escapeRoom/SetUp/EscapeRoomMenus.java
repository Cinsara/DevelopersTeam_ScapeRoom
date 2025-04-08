package escapeRoom.SetUp;

import escapeRoom.Controller.CertificateManager.CertificateMenu;
import escapeRoom.Controller.GameController.GameMenu;
import escapeRoom.Controller.NotificationManager.NotificationMenu;
import escapeRoom.Controller.RoomManager.RoomMenu;
import escapeRoom.Controller.TicketController.TicketMenu;
import escapeRoom.Controller.UserManager.UserMenu;
import escapeRoom.Service.InputService.InputService;

public class EscapeRoomMenus {
    public final TicketMenu ticketMenu;
    public final GameMenu gameMenu;
    public final UserMenu userMenu;
    public final NotificationMenu notificationMenu;
    public final CertificateMenu certificateMenu;
    public final RoomMenu roomMenu;

    public EscapeRoomMenus(InputService inputService,EscapeRoomControllers escapeRoomControllers, EscapeRoomManagers escapeRoomManagers) {
        this.gameMenu = new GameMenu(inputService,escapeRoomControllers.gameController);
        this.ticketMenu = new TicketMenu(inputService,escapeRoomControllers.ticketController);
        this.userMenu = new UserMenu(inputService,escapeRoomManagers.userManager);
        this.notificationMenu = new NotificationMenu(inputService, escapeRoomManagers.notificationManager);
        this.certificateMenu = new CertificateMenu(inputService,escapeRoomControllers.certificateController);
        this.roomMenu = new RoomMenu(inputService, escapeRoomManagers.roomManager);
    }

}
