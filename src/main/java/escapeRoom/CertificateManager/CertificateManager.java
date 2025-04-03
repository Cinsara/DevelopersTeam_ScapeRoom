package escapeRoom.CertificateManager;

import escapeRoom.Service.AssetService.CertificateService;
import escapeRoom.Service.GameService.GameService;
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
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class CertificateManager {
    private final CertificateService certificateService;
    private final UserService userService;
    private final GameService gameService;
    private final RoomService roomService;
    private final InputService inputService;
    private final GameHasUserService gameHasUserService;

    public CertificateManager(InputService inputService) throws SQLException {
        this.inputService = inputService;
        this.certificateService = new CertificateService();
        this.userService = new UserService();
        this.gameService = new GameService();
        this.roomService = new RoomService();
        this.gameHasUserService = new GameHasUserService();
    }

    public int selectOptionMenu(){
        return inputService.readInt("Select an option:");
    }

    public void inputsCertificationCreation(){
        try {
            int gameId = inputService.readInt("Enter the Game ID: ");
            int userId = inputService.readInt("Enter the User ID:" );
            validateCertificate(gameId,userId);

            Certificate certificate = new Certificate(userId, gameId);
            certificateService.create(certificate);
            String result = createCertificate(certificate);
            System.out.println(result);

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch (IllegalArgumentException e){
            System.out.println("Validation error: " + e.getMessage());
        }
        System.out.println("Certificated saved!");
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
        String filePath = Paths.get(home, "Desktop", "Certificate_" + user.getName() + "_" + user.getLastname() + ".txt").toString();

        try(FileWriter writer = new FileWriter(filePath)) {
            writer.write(certificateText);
            System.out.println("\nCertificated saved to: " + filePath);
        } catch (IOException e){
            System.out.println("Error saving the certificate: " + e.getMessage());
        }
        return certificateText;
    }
}
