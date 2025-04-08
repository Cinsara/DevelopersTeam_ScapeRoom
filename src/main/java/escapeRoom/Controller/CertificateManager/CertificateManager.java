package escapeRoom.Controller.CertificateManager;

import escapeRoom.Service.AssetService.CertificateService;
import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.ManyToManyService.GameHasUserService;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.model.AssetsArea.CertificateBuilder.Certificate;
import escapeRoom.model.GameArea.GameBuilder.Game;
import escapeRoom.model.GameArea.RoomBuilder.Room;
import escapeRoom.model.PeopleArea.User;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CertificateManager {
    private final UserService userService;
    private final GameService gameService;
    private final RoomService roomService;
    private final GameHasUserService gameHasUserService;
    private final CertificateService certificateService;

    public CertificateManager(UserService userService, GameService gameService, RoomService roomService,
                              GameHasUserService gameHasUserService, CertificateService certificateService) throws SQLException {
        this.userService = userService;
        this.gameService = gameService;
        this.roomService = roomService;
        this.gameHasUserService = gameHasUserService;
        this.certificateService = certificateService;
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

    public CertificateService getCertificateService() {
        return certificateService;
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

    public int getGameIdByDate(LocalDate gameDate, int roomId) throws SQLException,IllegalArgumentException{

        return gameService.getAllEntities(gameService.getConnection())
                .stream()
                .filter(g -> g.getDate().equals(gameDate))
                .filter(g -> g.getRoom_id() == roomId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Date not valid."))
                .getId();
    }

    public String createCertificate(Certificate certificate) throws SQLException {
        Game game = gameService.read(certificate.getGame_id())
                .orElseThrow(() -> new SQLException("Game data not found"));

        User user = userService.read(certificate.getUser_id())
                .orElseThrow(() -> new SQLException("User data not found"));

        Room room = roomService.read(game.getRoom_id())
                .orElseThrow(() -> new SQLException("Room data not found"));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        int minutes = game.getEllapsedTimeInSeconds() / 60;
        return ("\n------CERTIFICATE------\nCongratulations! This certificates that the user %s %s successfully completed the escape room %s.\n- Theme: %s\n- Difficulty: %s.\n" +
                "- Date: %s\n- Completion time: %d minutes.").formatted(user.getName(), user.getLastname(), room.getName(), room.getTheme(), room.getDifficulty(), game.getDate().format(dateFormatter), minutes);
    }

    public void certificateTxt(String certificateText, User user){
        String home = System.getProperty("user.home");
        String fileName = "Certificate_%s_%s.txt".formatted(user.getName(), user.getLastname());
        String filePath;

        try {
            filePath = Paths.get(home, "Desktop", fileName).toString();
            Files.createDirectories(Paths.get(filePath).getParent());
        } catch(Exception e) {
            filePath = Paths.get(home,fileName).toString();
        }

        try(FileWriter writer = new FileWriter(filePath)) {
            writer.write(certificateText);
            System.out.printf("\nCertificated saved to: %s%n", filePath);
        } catch (IOException e){
            System.out.printf("Error saving the certificate: %s%n", e.getMessage());
        }
    }
}
