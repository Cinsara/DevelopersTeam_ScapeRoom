package escapeRoom;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Controller.CertificateManager.CertificateManager;
import escapeRoom.Controller.CertificateManager.CertificateMenu;
import escapeRoom.Controller.GameController.GameController;
import escapeRoom.Controller.GameController.GameManager;
import escapeRoom.Controller.GameController.GameMenu;
import escapeRoom.Controller.InventoryController.InventoryController;
import escapeRoom.Controller.NotificationManager.NotificationManager;
import escapeRoom.Controller.NotificationManager.NotificationMenu;
import escapeRoom.Controller.RewardController.RewardController;
import escapeRoom.Controller.RoomManager.ClueManager;
import escapeRoom.Controller.RoomManager.PropManager;
import escapeRoom.Controller.RoomManager.RoomManager;
import escapeRoom.Controller.RoomManager.RoomMenu;
import escapeRoom.Controller.TicketController.TicketController;
import escapeRoom.Controller.TicketController.TicketManager;
import escapeRoom.Controller.TicketController.TicketMenu;
import escapeRoom.Controller.UserManager.UserManager;
import escapeRoom.Controller.UserManager.UserMenu;
import escapeRoom.Service.AbsentEntityException;
import escapeRoom.Service.AssetService.CertificateService;
import escapeRoom.Service.AssetService.RewardService;
import escapeRoom.Service.AssetService.TicketService;
import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.InputService.InputServiceManager;
import escapeRoom.Service.ManyToManyService.GameHasUserService;
import escapeRoom.Service.ManyToManyService.GameUsesClueService;
import escapeRoom.Service.NotificationService.NotificationService;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.Service.PropAndClueService.ClueService;
import escapeRoom.Service.PropAndClueService.PropService;
import escapeRoom.Service.RoomService.RoomService;

import java.sql.Connection;
import java.sql.SQLException;

public class EscapeRoomInitializer {
    private final InputService inputService;
    private final TicketMenu ticketMenu;
    private final GameMenu gameMenu;
    private final RewardController rewardController;
    private final UserMenu userMenu;
    private final NotificationMenu notificationMenu;
    private final CertificateMenu certificateMenu;
    private final InventoryController inventoryController;
    private final RoomMenu roomMenu;
    private final GameManager gameManager;
    private final GameController gameController;
    private final UserManager userManager;
    private final UserService userService;
    private final CertificateManager certificateManager;
    private final NotificationManager notificationManager;
    private final NotificationService notificationService;
    private final Connection connection;
    private final RoomManager roomManager;
    private final RoomService roomService;
    private final ClueManager clueManager;
    private final PropManager propManager;
    private final ClueService clueService;
    private final PropService propService;
    private final GameService gameService;
    private final TicketService ticketService;
    private final RewardService rewardService;
    private final CertificateService certificateService;
    private final GameHasUserService gameHasUserService;
    private final GameUsesClueService gameUsesClueService;
    private final TicketManager ticketManager;
    private final TicketController ticketController;

    public EscapeRoomInitializer() throws SQLException, AbsentEntityException {
        //connection
        this.connection = ConnectionManager.getConnection();
        //services
        this.gameService = new GameService(connection);
        this.roomService = new RoomService(connection);
        this.propService = new PropService(connection);
        this.clueService = new ClueService(connection);
        this.userService = new UserService(connection);
        this.ticketService = new TicketService(connection);
        this.rewardService = new RewardService(connection);
        this.certificateService = new CertificateService(connection);
        this.notificationService = new NotificationService(connection);
        this.gameHasUserService = new GameHasUserService(connection);
        this.gameUsesClueService = new GameUsesClueService(connection);

        this.inputService = InputServiceManager.getInputService();

        //managers
        this.gameManager = new GameManager(gameService,roomService,ticketService,rewardService,userService,gameHasUserService,gameUsesClueService);
        this.certificateManager = new CertificateManager(inputService,certificateService,userService,gameService,roomService,gameHasUserService);
        this.userManager = new UserManager(userService,inputService);
        this.roomManager = new RoomManager(inputService,roomService);
        this.clueManager = new ClueManager(inputService, clueService, roomService);
        this.propManager = new PropManager(inputService, propService,roomService);
        this.notificationManager = new NotificationManager(notificationService,connection,inputService,userService);
        this.ticketManager = new TicketManager(ticketService,gameManager);

        //controllers
        this.inventoryController = new InventoryController(roomService,propService,clueService);
        this.gameController = new GameController(gameManager);
        this.rewardController = new RewardController(rewardService,gameService,userService);
        this.ticketController = new TicketController(inputService,ticketManager);

        //menus
        this.gameMenu = new GameMenu(inputService,gameController);
        this.ticketMenu = new TicketMenu(inputService,ticketController);
        this.userMenu = new UserMenu(inputService,userManager);
        this.notificationMenu = new NotificationMenu(inputService, notificationManager);
        this.certificateMenu = new CertificateMenu(inputService,certificateManager);
        this.roomMenu = new RoomMenu(inputService, roomManager);

    }

    public InputService getInputService() {
        return inputService;
    }

    public TicketMenu getTicketMenu() {
        return ticketMenu;
    }

    public GameMenu getGameMenu() {
        return gameMenu;
    }

    public RewardController getRewardController() {
        return rewardController;
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

    public InventoryController getInventoryController() {
        return inventoryController;
    }

    public RoomMenu getRoomMenu() {
        return roomMenu;
    }
}
