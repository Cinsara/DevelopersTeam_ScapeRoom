package escapeRoom.Controller.CertificateManager;

import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.ManyToManyService.GameHasUserService;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.model.GameArea.GameBuilder.Game;

import java.sql.SQLException;

public class CertificateValidation {
    private final UserService userService;
    private final GameService gameService;
    private final RoomService roomService;
    private final GameHasUserService gameHasUserService;

    public CertificateValidation(UserService userService, GameService gameService, RoomService roomService,
                                 GameHasUserService gameHasUserService) throws SQLException {
        this.userService = userService;
        this.gameService = gameService;
        this.roomService = roomService;
        this.gameHasUserService = gameHasUserService;
    }

    public UserService getUserService(){
        return userService;
    }

    public GameService getGameService(){
        return gameService;
    }

    public RoomService getRoomService(){
        return roomService;
    }

    public GameHasUserService getGameHasUserService(){
        return gameHasUserService;
    }

    public void validateCertificate(int gameId, int userId) throws SQLException {
        Game game = gameService.read(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        if(!game.isSuccess()){
            throw new IllegalArgumentException("Error. Cannot generate certificate. The user" +
                    "didn't beat the game.");
        }

        userService.read(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        roomService.read(game.getRoom_id())
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        if(!isUserParticipant(gameId,userId)){
            throw new IllegalArgumentException("Error. The user didn't participate in this game.");
        }
    }

    public boolean isUserParticipant(int gameId, int userId) throws SQLException{
        return gameHasUserService.getMatches(gameId).contains(userId);
    }
}
