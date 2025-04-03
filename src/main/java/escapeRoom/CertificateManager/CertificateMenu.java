package escapeRoom.CertificateManager;

public class CertificateMenu {
    private final CertificateManager certificateManager;

    public CertificateMenu(CertificateManager certificateManager) {
        this.certificateManager = certificateManager;
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
                case 0 -> System.out.println("Returning to the main menu.");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while(option != 0);
    }
}
