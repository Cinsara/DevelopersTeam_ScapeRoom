package escapeRoom.Menus;

import escapeRoom.Controller.CertificateManager.CertificateController;
import escapeRoom.Service.InputService.BackToSecondaryMenuException;
import escapeRoom.Service.InputService.InputService;

public class CertificateMenu {
    private CertificateController certificateController;
    private InputService inputService;

    public CertificateMenu(InputService inputService, CertificateController certificateController) {
        this.inputService = inputService;
        this.certificateController = certificateController;
    }

    public int principalCertificateMenu(){
        String menu = """
                ------
                Certificate menu:
                ------
                1. Create certification.
                0. Back to the main menu.
                ------""";
        try{
            return inputService.readInt(menu);
        } catch (BackToSecondaryMenuException e) {
            return 0;
        }
    }
    public void startCertificationMenu(){
        int option;
        do {
            try{
                option = principalCertificateMenu();
                switch(option){
                    case 1 -> certificateController.inputsCertificationCreation();
                    case 0 -> {
                        System.out.println("Returning to the main menu.");
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }catch(BackToSecondaryMenuException e){}
        } while(true);
    }
}
