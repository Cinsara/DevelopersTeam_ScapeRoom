package escapeRoom.Controller.CertificateManager;

import escapeRoom.Controller.GeneralMenu;
import escapeRoom.Service.InputService.InputService;

import java.sql.SQLException;

public class CertificateMenu {
    private CertificateManager certificateManager;

    public CertificateMenu(InputService inputService) {
        try{
            this.certificateManager = new CertificateManager(inputService);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void principalCertificateMenu(){
        String menu = """
                ------
                Certificate menu:
                ------
                1. Create certification.
                0. Back to the main menu.
                ------""";
        System.out.println(menu);
    }

    public void startCertificationMenu(){
        int option;
        do {
            principalCertificateMenu();
            option = certificateManager.selectOptionMenu();

            switch(option){
                case 1 -> certificateManager.inputsCertificationCreation();
                case 0 -> {
                    System.out.println("Returning to the main menu.");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while(true);
    }
}
