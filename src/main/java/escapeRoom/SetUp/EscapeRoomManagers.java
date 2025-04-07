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
    private final GameManager gameManager;
    private final UserManager userManager;
    private final CertificateManager certificateManager;
    private final NotificationManager notificationManager;
    private final RoomManager roomManager;
    private final ClueManager clueManager;
    private final PropManager propManager;
    private final TicketManager ticketManager;

    public EscapeRoomManagers(InputService inputService, InputCollector inputCollector, EscapeRoomServices services) throws SQLException {
        this.gameManager = new GameManager(services.getPartialServices());
        this.userManager = new UserManager(inputService,services.getUserService());
        this.certificateManager = new CertificateManager(inputCollector, services.getPartialServices());
        this.notificationManager = new NotificationManager(inputService,services.getNotificationService(),services.getUserService());
        this.clueManager = new ClueManager(inputService,services.getClueService(),services.getRoomService());
        this.propManager = new PropManager(inputService,services.getPropService(),services.getRoomService());
        this.roomManager = new RoomManager(inputService,services.getRoomService(),clueManager,propManager);
        this.ticketManager = new TicketManager(services.getTicketService(),gameManager);
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public CertificateManager getCertificateManager() {
        return certificateManager;
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public RoomManager getRoomManager() {
        return roomManager;
    }

    public ClueManager getClueManager() {
        return clueManager;
    }

    public PropManager getPropManager() {
        return propManager;
    }

    public TicketManager getTicketManager() {
        return ticketManager;
    }
}
