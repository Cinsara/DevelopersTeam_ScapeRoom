package escapeRoom.Controller.CertificateManager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.AssetService.CertificateService;
import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.InputService.InputCollector;
import escapeRoom.Service.InputService.InputService;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CertificateManager {
    private final CertificateService certificateService;
    private final UserService userService;
    private final GameService gameService;
    private final RoomService roomService;
    private final InputService inputService;
    private final GameHasUserService gameHasUserService;
    private final InputCollector inputCollector;
    private final CertificateValidation certificateValidation;

    public CertificateManager(InputService inputService,CertificateService certificateService, UserService userService,
                              GameService gameService,RoomService roomService,GameHasUserService gameHasUserService,
                              InputCollector inputCollector, CertificateValidation certificateValidation) throws SQLException {
        this.inputService = inputService;
        this.certificateService = certificateService;
        this.userService = userService;
        this.gameService = gameService;
        this.roomService = roomService;
        this.gameHasUserService = gameHasUserService;
        this.inputCollector = inputCollector;
        this.certificateValidation = certificateValidation;
    }

    public int selectOptionMenu(){
        return inputService.readInt("Select an option:");
    }

    public void inputsCertificationCreation(){
        try {
            LocalDate gameDate = inputCollector.getDate();
            int userId = inputCollector.getTargetCostumer().getId();
            int roomId = inputCollector.getRoom().getId();
            int gameId = getGameIdByDate(gameDate);
            certificateValidation.validateCertificate(gameId,userId);
            Certificate certificate = new Certificate(userId, gameId);
            certificateService.create(certificate);
            String result = createCertificate(certificate);
            System.out.println(result);
            System.out.println("\nCertificated saved!");
            User user = certificateValidation.getUserService().read(userId).orElseThrow();
            certificateTxt(result,user);
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch (IllegalArgumentException e){
            System.out.println("Validation error: " + e.getMessage());
        }
    }

    public int getGameIdByDate(LocalDate gameDate) throws SQLException,IllegalArgumentException{
        Connection connection = ConnectionManager.getConnection();

        return certificateValidation.getGameService().getAllEntities(connection).stream()
                .filter(g -> g.getDate().equals(gameDate))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Date not valid."))
                .getId();
    }

    public String createCertificate(Certificate certificate) throws SQLException {
        Game game = certificateValidation.getGameService().read(certificate.getGame_id())
                .orElseThrow(() -> new SQLException("Game data not found"));

        User user = certificateValidation.getUserService().read(certificate.getUser_id())
                .orElseThrow(() -> new SQLException("User data not found"));

        Room room = certificateValidation.getRoomService().read(game.getRoom_id())
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
