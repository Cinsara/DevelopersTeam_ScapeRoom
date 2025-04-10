package escapeRoom.Controller.CertificateManager;

import escapeRoom.Service.InputService.BackToSecondaryMenuException;
import escapeRoom.Service.InputService.InputCollector;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Model.AssetsArea.CertificateBuilder.Certificate;
import escapeRoom.Model.PeopleArea.User;

import java.sql.SQLException;
import java.time.LocalDate;

public class CertificateController {

    private final InputService inputService;
    private final InputCollector inputCollector;
    private final CertificateManager certificateManager;

    public CertificateController(InputService inputService, InputCollector inputCollector,
                                 CertificateManager certificateManager) throws SQLException {
        this.inputService = inputService;
        this.inputCollector = inputCollector;
        this.certificateManager = certificateManager;
    }

    public void inputsCertificationCreation() throws BackToSecondaryMenuException{
        try {
            LocalDate gameDate = inputCollector.getDate();
            int userId = inputCollector.getTargetCostumer().getId();
            int roomId = inputCollector.getRoom().getId();
            int gameId = certificateManager.getGameIdByDate(gameDate, roomId);
            certificateManager.validateCertificate(gameId,userId);
            Certificate certificate = new Certificate(userId, gameId);
            certificateManager.getCertificateService().create(certificate);
            String result = certificateManager.createCertificate(certificate);
            System.out.println(result);
            System.out.println("\nCertificated saved!");
            User user = certificateManager.getUserService().read(userId).orElseThrow();
            certificateManager.certificateTxt(result,user);
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch (IllegalArgumentException e){
            System.out.println("Validation error: " + e.getMessage());
        }
    }
}