package escapeRoom.AssetsArea_Classes.AssetBuilder;

import escapeRoom.AssetsArea_Classes.CertificateBuilder.Certificate;
import escapeRoom.AssetsArea_Classes.Classes_testing.Game;
import escapeRoom.AssetsArea_Classes.Classes_testing.PersonClasses.User;
import escapeRoom.AssetsArea_Classes.Classes_testing.Room_Classes.Difficulty;
import escapeRoom.AssetsArea_Classes.Classes_testing.Room_Classes.Room;
import escapeRoom.AssetsArea_Classes.Classes_testing.Room_Classes.Theme;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AssetFactoryTest {
    static AssetFactory  factory;

    @BeforeAll
    static void setUp(){
        factory = new AssetFactory();
    }
    @Test
    void createAsset() {
        Asset newCertificate = factory.createAsset("certificate",1,2);
        User newUser = new User("Aurélien","Darbellay","bla@blamail.io","345", LocalDate.of(1983,11,27),false);
        Room newRoom = new Room("Rock", Theme.FANTASTIC, Difficulty.HARD, List.of(1),List.of(2));
        Game newGame new Game()
        newCertificate.expressAsset(newUser,newRoom,LocalDate.now());
    }

    @Test
    void testCreateAsset() {
    }
}