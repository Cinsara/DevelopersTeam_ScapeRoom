package escapeRoom.SetUp;

import escapeRoom.Service.AssetService.CertificateService;
import escapeRoom.Service.AssetService.RewardService;
import escapeRoom.Service.AssetService.TicketService;
import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.ManyToManyService.GameHasUserService;
import escapeRoom.Service.ManyToManyService.GameUsesClueService;
import escapeRoom.Service.NotificationService.NotificationService;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.Service.PropAndClueService.ClueService;
import escapeRoom.Service.PropAndClueService.PropService;
import escapeRoom.Service.RoomService.RoomService;

import java.sql.Connection;
import java.sql.SQLException;

public class EscapeRoomServices {
    private final UserService userService;
    private final RoomService roomService;
    private final ClueService clueService;
    private final PropService propService;
    private final GameService gameService;
    private final TicketService ticketService;
    private final RewardService rewardService;
    private final CertificateService certificateService;
    private final GameHasUserService gameHasUserService;
    private final GameUsesClueService gameUsesClueService;
    private final NotificationService notificationService;

    public EscapeRoomServices(Connection connection) throws SQLException {
        this.userService = new UserService(connection);
        this.roomService = new RoomService(connection);
        this.clueService = new ClueService(connection);
        this.propService = new PropService(connection);
        this.gameService = new GameService(connection);
        this.ticketService = new TicketService(connection);
        this.rewardService = new RewardService(connection);
        this.certificateService = new CertificateService(connection);
        this.gameHasUserService = new GameHasUserService(connection);
        this.gameUsesClueService = new GameUsesClueService(connection);
        this.notificationService = new NotificationService(connection);
    }

    public PartialEscapeRoomServices getPartialServices(){
        return new PartialEscapeRoomServices(this);
    }

    public UserService getUserService() {
        return userService;
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public ClueService getClueService() {
        return clueService;
    }

    public PropService getPropService() {
        return propService;
    }

    public GameService getGameService() {
        return gameService;
    }

    public TicketService getTicketService() {
        return ticketService;
    }

    public RewardService getRewardService() {
        return rewardService;
    }

    public CertificateService getCertificateService() {
        return certificateService;
    }

    public GameHasUserService getGameHasUserService() {
        return gameHasUserService;
    }

    public GameUsesClueService getGameUsesClueService() {
        return gameUsesClueService;
    }

    public NotificationService getNotificationService() {
        return notificationService;
    }
}
