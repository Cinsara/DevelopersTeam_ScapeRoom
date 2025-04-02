package escapeRoom.CertificateManager;

import escapeRoom.ConnectionManager.ConnectionManager;
import escapeRoom.Service.AssetService.CertificateService;
import escapeRoom.Service.InputService.InputService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CertificateManagerTest {
    private static CertificateManager certificateManager;
    private static ByteArrayOutputStream outputStream;
    private static CertificateService certificateService;
    private static Connection connection;
    private static InputService inputService;

    @BeforeEach
    void setUp() throws Exception {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        connection = ConnectionManager.getConnection();
        certificateService = new CertificateService();
        inputService = new InputService(new Scanner(System.in));
        certificateManager = new CertificateManager(inputService);
    }

    private void setSimulatedInput(String input) throws SQLException {
        InputStream simulatedIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(simulatedIn);
        inputService = new InputService(new Scanner(System.in));
        certificateManager = new CertificateManager(inputService);
    }

    @Test
    void inputsCertificationCreation() throws SQLException {
        String input = "1\n1\n";
        setSimulatedInput(input);

        certificateManager.inputsCertificationCreation();

        String expectedOutput = "Certificated saved to: " + Paths.get(System.getProperty("user.home"), "Desktop", "Certificate_1_1.txt").toString();
        assertTrue(outputStream.toString().contains(expectedOutput));
    }

    @Test
    void validateCertificate_invalidGameId_throwsIllegalArgumentException() {
        int invalidGameId = -1;
        int userId = 1;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            certificateManager.validateCertificate(invalidGameId, userId);
        });

        assertTrue(exception.getMessage().contains("Game not found"));
    }

    @Test
    void validateCertificate() {

    }

    @Test
    void isUserParticipant() {
    }

    @Test
    void createCertificate() {
    }
}