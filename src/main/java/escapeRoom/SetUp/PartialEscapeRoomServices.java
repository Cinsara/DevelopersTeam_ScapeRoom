package escapeRoom.SetUp;

import escapeRoom.Service.AssetService.CertificateService;
import escapeRoom.Service.AssetService.RewardService;
import escapeRoom.Service.AssetService.TicketService;
import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.ManyToManyService.GameHasUserService;
import escapeRoom.Service.ManyToManyService.GameUsesClueService;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.Service.RoomService.RoomService;

public class PartialEscapeRoomServices {
    private final UserService userService;
    private final RoomService roomService;
    private final GameService gameService;
    private final TicketService ticketService;
    private final RewardService rewardService;
    private final CertificateService certificateService;
    private final GameHasUserService gameHasUserService;
    private final GameUsesClueService gameUsesClueService;

    protected PartialEscapeRoomServices(EscapeRoomServices services){
        this.userService = services.getUserService();
        this.roomService = services.getRoomService();
        this.gameService = services.getGameService();
        this.ticketService = services.getTicketService();
        this.rewardService = services.getRewardService();
        this.certificateService = services.getCertificateService();
        this.gameHasUserService = services.getGameHasUserService();
        this.gameUsesClueService = services.getGameUsesClueService();

    };

    public GameUsesClueService getGameUsesClueService() {
        return gameUsesClueService;
    }

    public GameHasUserService getGameHasUserService() {
        return gameHasUserService;
    }

    public CertificateService getCertificateService() {
        return certificateService;
    }

    public RewardService getRewardService() {
        return rewardService;
    }

    public TicketService getTicketService() {
        return ticketService;
    }

    public GameService getGameService() {
        return gameService;
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public UserService getUserService() {
        return userService;
    }



}
