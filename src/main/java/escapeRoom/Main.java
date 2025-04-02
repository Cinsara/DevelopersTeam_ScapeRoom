package escapeRoom;

import escapeRoom.CertificateManager.CertificateManager;
import escapeRoom.CertificateManager.CertificateMenu;
import escapeRoom.Service.InputService.InputService;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner input = new Scanner(System.in);
        InputService inputService =new InputService(input);
        CertificateManager certificateManager = new CertificateManager(inputService);
        CertificateMenu certificateMenu = new CertificateMenu(certificateManager);
        certificateMenu.startCertificationMenu();
    }
}