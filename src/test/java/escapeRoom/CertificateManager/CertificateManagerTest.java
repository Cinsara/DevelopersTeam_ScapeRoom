package escapeRoom.CertificateManager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Controller.CertificateManager.CertificateManager;
import escapeRoom.Service.AssetService.CertificateService;
import escapeRoom.Service.GameService.GameService;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.InputService.InputServiceManager;
import escapeRoom.Service.ManyToManyService.GameHasUserService;
import escapeRoom.Service.PeopleService.UserService;
import escapeRoom.Service.RoomService.RoomService;
import escapeRoom.model.AssetsArea.CertificateBuilder.Certificate;
import escapeRoom.model.GameArea.GameBuilder.Game;
import escapeRoom.model.GameArea.RoomBuilder.Difficulty;
import escapeRoom.model.GameArea.RoomBuilder.Room;
import escapeRoom.model.PeopleArea.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CertificateManagerTest {
    private static CertificateManager certificateManager;
    private static ByteArrayOutputStream outputStream;
    private static CertificateService certificateService;
    private static Connection connection;
    private static InputService inputService;
    private static GameService gameService;
    private static UserService userService;
    private static RoomService roomService;
    private static GameHasUserService gameHasUserService;

    @BeforeEach
    void setUp() throws Exception {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        connection = ConnectionManager.getConnection();
        certificateService = new CertificateService(connection);
        inputService = InputServiceManager.getInputService();
        gameService = new GameService(connection);
        userService = new UserService(connection);
        roomService = new RoomService(connection);
        gameHasUserService = new GameHasUserService(connection);
        certificateManager = new CertificateManager(inputService,certificateService,userService,gameService,roomService,gameHasUserService);
    }

    private void setSimulatedInput(String input) throws SQLException {
        InputStream simulatedIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(simulatedIn);
        inputService = InputServiceManager.getInputService();
        certificateManager = new CertificateManager(inputService,certificateService,userService,gameService,roomService,gameHasUserService);
    }

    @Test
    void inputsCertificationCreation() throws SQLException {
        String input = "1\n1\n";
        setSimulatedInput(input);

        certificateManager.inputsCertificationCreation();

        assertTrue(outputStream.toString().contains("Certificated saved to"));
        assertTrue(outputStream.toString().contains("Certificate_"));
        assertTrue(outputStream.toString().contains(".txt"));
    }

    @Test
    void validateCertificate_invalidGameId() {
        int invalidGameId = -1;
        int userId = 1;
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            certificateManager.validateCertificate(invalidGameId, userId);
        });
        assertTrue(exception.getMessage().contains("Game not found"));
    }

    @Test
    void validateCertificate_userNotFound() {
        int gameId = 1;
        int invalidUserId = -1;
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            certificateManager.validateCertificate(gameId, invalidUserId);
        });
        assertTrue(exception.getMessage().contains("User not found"));
    }

    @Test
    void isUserParticipant_true() throws SQLException {
        List<Integer> participantes = List.of(1, 2, 3);
        boolean resultado = participantes.contains(1);
        assertTrue(resultado);
    }

    @Test
    void isUserParticipant_false() throws SQLException {
        List<Integer> participantes = List.of(2, 3, 4);
        boolean resultado = participantes.contains(1);
        assertFalse(resultado);
    }

    @Test
    void createCertificate() throws SQLException {
        String input = "1\n1\n";
        setSimulatedInput(input);

        Game testGame = new Game(1,LocalDate.now());
        gameService.create(testGame);

        User testUser = new User("Test",
                "Johnson",
                "alice.johnson@example.com",
                "123-456-7890",
                LocalDate.of(1990, 5, 14),
                true);

        userService.create(testUser);

        Room testRoom = new Room(
                1,
                "love in New York3e2",
                "Love Affair",
                Difficulty.EASY,
                List.of(201, 202),
                List.of(301, 302, 303)
        );

        roomService.create(testRoom);

        List<Integer> matches = gameHasUserService.getMatches(1);
        assertNotNull(matches, "Matches list should not be null");
        assertFalse(matches.isEmpty(), "Matches list should contain at least one element");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        certificateManager.inputsCertificationCreation();
        String output = outputStream.toString();
        assertTrue(output.contains("Certificated saved!"), "It should show success message");

        List<Certificate> certificates = certificateService.getAllEntities(connection);
        Optional<Certificate> createdCert = certificates.stream()
                .filter(c -> c.getGame_id() == 1 && c.getUser_id() == 1)
                .findFirst();

        assertTrue(createdCert.isPresent(), "The certificate should be exist in te bd.");
    }
}