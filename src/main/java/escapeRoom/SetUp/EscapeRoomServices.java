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
    public final UserService userService;
    public final RoomService roomService;
    public final ClueService clueService;
    public final PropService propService;
    public final GameService gameService;
    public final TicketService ticketService;
    public final RewardService rewardService;
    public final CertificateService certificateService;
    public final GameHasUserService gameHasUserService;
    public final GameUsesClueService gameUsesClueService;
    public final NotificationService notificationService;

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

    static public class ServicesForGameManager {
        public final UserService userService;
        public final RoomService roomService;
        public final GameService gameService;
        public final TicketService ticketService;
        public final RewardService rewardService;
        public final GameHasUserService gameHasUserService;
        public final GameUsesClueService gameUsesClueService;

        public ServicesForGameManager(EscapeRoomServices escapeRoomServices) {
            this.userService = escapeRoomServices.userService;
            this.roomService = escapeRoomServices.roomService;
            this.gameService = escapeRoomServices.gameService;
            this.ticketService = escapeRoomServices.ticketService;
            this.rewardService = escapeRoomServices.rewardService;
            this.gameHasUserService = escapeRoomServices.gameHasUserService;
            this.gameUsesClueService = escapeRoomServices.gameUsesClueService;
        }

    }
    public ServicesForGameManager getServicesForGameManager(){
        return new ServicesForGameManager(this);
    }

    static public class ServicesForCertificateManager{
        public final CertificateService certificateService;
        public final UserService userService;
        public final GameService gameService;
        public final RoomService roomService;
        public final GameHasUserService gameHasUserService;

        public ServicesForCertificateManager(EscapeRoomServices services) {
            this.certificateService = services.certificateService;
            this.userService = services.userService;
            this.gameService = services.gameService;
            this.roomService = services.roomService;
            this.gameHasUserService = services.gameHasUserService;
        }
    }

    public ServicesForCertificateManager getServicesForCertificateManager(){
        return new ServicesForCertificateManager(this);
    }

}
