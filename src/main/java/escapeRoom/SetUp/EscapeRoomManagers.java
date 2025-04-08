package escapeRoom.SetUp;

import escapeRoom.Controller.CertificateManager.CertificateManager;
import escapeRoom.Controller.GameController.GameManager;
import escapeRoom.Controller.NotificationManager.NotificationManager;
import escapeRoom.Controller.RoomManager.ClueManager;
import escapeRoom.Controller.RoomManager.PropManager;
import escapeRoom.Controller.RoomManager.RoomManager;
import escapeRoom.Controller.TicketController.TicketManager;
import escapeRoom.Controller.UserManager.UserManager;
import escapeRoom.Service.InputService.InputCollector;
import escapeRoom.Service.InputService.InputService;

import java.sql.SQLException;

public class EscapeRoomManagers {
    public final GameManager gameManager;
    public final UserManager userManager;
    public final CertificateManager certificateManager;
    public final NotificationManager notificationManager;
    public final RoomManager roomManager;
    public final ClueManager clueManager;
    public final PropManager propManager;
    public final TicketManager ticketManager;

    public EscapeRoomManagers(InputService inputService, EscapeRoomServices services) throws SQLException {
        this.gameManager = new GameManager(services.getServicesForGameManager());
        this.userManager = new UserManager(inputService,services.userService);
        this.certificateManager = new CertificateManager(services.getServicesForCertificateManager());
        this.notificationManager = new NotificationManager(inputService,services.notificationService,services.userService);
        this.clueManager = new ClueManager(inputService,services.clueService,services.roomService);
        this.propManager = new PropManager(inputService,services.propService,services.roomService);
        this.roomManager = new RoomManager(inputService,services.roomService,clueManager,propManager);
        this.ticketManager = new TicketManager(services.ticketService,gameManager);
    }

}
