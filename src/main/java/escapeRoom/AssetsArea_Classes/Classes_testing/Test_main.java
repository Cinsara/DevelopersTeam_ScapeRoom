package escapeRoom.AssetsArea_Classes.Classes_testing;

import escapeRoom.AssetsArea_Classes.AssetBuilder.Asset;
import escapeRoom.AssetsArea_Classes.AssetBuilder.AssetFactory;
import escapeRoom.AssetsArea_Classes.AssetBuilder.AssetManager;
import escapeRoom.AssetsArea_Classes.Classes_testing.PersonClasses.User;

import java.time.LocalDate;

public class Test_main {
    public static void testStart(){

        //Creación usuarios y game

        User user1 = new User(
                1,
                "Laura",
                "Martinez",
                "gmail.com",
                "123445543",
                LocalDate.of(1993,5,4),
                true
        );

        Game game1 = new Game(
                "Alice Game",
                "Fantasy",
                2025,
                50.99
        );

        AssetFactory factory = new AssetFactory();
        AssetManager assetManager = new AssetManager();
        Asset certificate = factory.createAsset("certificate", 1,2);
        certificate.expressAsset(user1,game1, LocalDate.now());
        String certResult = assetManager.generateTxt(certificate, LocalDate.now(),1,1);
        System.out.println(certResult);

    }
}
