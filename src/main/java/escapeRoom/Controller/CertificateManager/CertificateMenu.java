package escapeRoom.Controller.CertificateManager;

import escapeRoom.Service.InputService.InputService;

import java.sql.SQLException;

public class CertificateMenu {
    private CertificateController certificateController;
    private InputService inputService;

    public CertificateMenu(InputService inputService, CertificateController certificateController) {
        this.inputService = inputService;
        this.certificateController = certificateController;
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
            option = inputService.readInt("Select an option:");

            switch(option){
                case 1 -> certificateController.inputsCertificationCreation();
                case 0 -> {
                    System.out.println("Returning to the main menu.");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while(true);
    }
}
