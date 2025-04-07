package escapeRoom.Controller.CertificateManager;

import escapeRoom.Service.AssetService.CertificateService;
import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.InputService.InputCollector;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.ManyToManyService.GameHasUserService;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.Model.AssetsArea.CertificateBuilder.Certificate;
import escapeRoom.Model.GameArea.GameBuilder.Game;
import escapeRoom.Model.GameArea.RoomBuilder.Room;
import escapeRoom.Model.PeopleArea.User;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CertificateManager {
    private final CertificateService certificateService;
    private final UserService userService;
    private final GameService gameService;
    private final RoomService roomService;
    private final GameHasUserService gameHasUserService;
    private final InputCollector inputCollector;

    public CertificateManager(CertificateService certificateService, UserService userService,GameService gameService,RoomService roomService,GameHasUserService gameHasUserService, InputCollector inputCollector) throws SQLException {
        this.certificateService = certificateService;
        this.userService = userService;
        this.gameService = gameService;
        this.roomService = roomService;
        this.gameHasUserService = gameHasUserService;
        this.inputCollector = inputCollector;
    }


    public void inputsCertificationCreation(){
        try {
            LocalDate gameDate = inputCollector.getDate();
            int userId = inputCollector.getTargetCostumer().getId();
            int roomId = inputCollector.getRoom().getId();
            int gameId = getGameIdByDate(gameDate,roomId);
            validateCertificate(gameId,userId);
            Certificate certificate = new Certificate(userId, gameId);
            certificateService.create(certificate);
            String result = createCertificate(certificate);
            System.out.println(result);
            System.out.println("\nCertificated saved!");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch (IllegalArgumentException e){
            System.out.println("Validation error: " + e.getMessage());
        }
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
        return gameService.getAllEntities(gameService.getConnection()).stream()
                .filter(g -> g.getDate().equals(gameDate))
                .filter(g->g.getRoom_id() == roomId)
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
        String certificateText = "\n------" +
                "CERTIFICATE" +
                "------" +
                "\nCongratulations! This certificates that the user " + user.getName() + " " + user.getLastname() +
                " successfully completed the escape room " + room.getName() + ".\n- Theme: " +
                room.getTheme() + "\n- Difficulty: " + room.getDifficulty() + ".\n- Date: " +
                game.getDate().format(dateFormatter) + "\n- Completion time: " + minutes + " minutes.";

        String home = System.getProperty("user.home");
        String fileName = "Certificate_" + user.getName() + "_" + user.getLastname() + ".txt";
        String filePath;

        try {
            filePath = Paths.get(home, "Desktop", fileName).toString();
            Files.createDirectories(Paths.get(filePath).getParent());
        } catch(Exception e) {
            filePath = Paths.get(home,fileName).toString();
        }

        try(FileWriter writer = new FileWriter(filePath)) {
            writer.write(certificateText);
            System.out.println("\nCertificated saved to: " + filePath);
        } catch (IOException e){
            System.out.println("Error saving the certificate: " + e.getMessage());
        }
        return certificateText;
    }
}
