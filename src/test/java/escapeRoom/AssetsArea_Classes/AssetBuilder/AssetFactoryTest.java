package escapeRoom.AssetsArea_Classes.AssetBuilder;

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
        Asset newCertificate = factory.createAsset(AssetType.CERTIFICATE,1,2);
        User newUser = new User("Aurélien","Darbellay","bla@blamail.io","345", LocalDate.of(1983,11,27),false);
        Room newRoom = new Room("Rock", Theme.FANTASTIC, Difficulty.HARD, List.of(1),List.of(2));
        newCertificate.expressAsset(newUser,newRoom,LocalDate.now());
        Asset newReward = factory.createAsset(AssetType.REWARD,1,1);
        Game newGame = new Game(1,1,LocalDate.of(2021,10,27),List.of(1,2),3,true,List.of(5,6),100,List.of(3,4));
        newReward.expressAsset(newUser,newGame,newGame.getDate());
        Asset newTicket = factory.createAsset(AssetType.TICKET,1,1);
        newTicket.expressAsset(newUser,newGame,newGame.getDate());
    }

    @Test
    void testCreateAsset() {
    }
}