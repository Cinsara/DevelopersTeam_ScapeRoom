package escapeRoom.AssetsArea.AssetBuilder;

import escapeRoom.model.GameArea.RoomBuilder.Difficulty;
import escapeRoom.model.GameArea.RoomBuilder.Theme;
import escapeRoom.model.AssetsArea.AssetBuilder.Asset;
import escapeRoom.model.AssetsArea.AssetBuilder.AssetFactory;
import escapeRoom.model.AssetsArea.AssetBuilder.AssetType;
import escapeRoom.model.PeopleArea.User;
import escapeRoom.model.GameArea.GameBuilder.Game;
import escapeRoom.model.GameArea.RoomBuilder.Room;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class AssetFactoryTest {
    static AssetFactory factory;

    @BeforeAll
    static void setUp(){
        factory = new AssetFactory();
    }
    @Test
    void createAsset() {
        Asset newCertificate = factory.createAsset(AssetType.CERTIFICATE,1,2);
        User newUser = new User("Aurélien","Darbellay","bla@blamail.io","345", LocalDate.of(1983,11,27),false);
        Room newRoom = new Room(1,"Rock", Theme.FANTASTIC, Difficulty.HARD, List.of(1),List.of(2));
        newCertificate.expressAsset(newUser,newRoom,LocalDate.now());
        Asset newReward = factory.createAsset(AssetType.REWARD,1,1);
        Game newGame = new Game(1,1,LocalDate.of(2021,10,27),List.of(1,2),3,true,List.of(5,6),100,List.of(3,4));
        newReward.expressAsset(newUser,newGame,newGame.getDate());
        Asset newTicket = factory.createAsset(AssetType.TICKET,1,1);
        System.out.println(newTicket.getId());
        newTicket.expressAsset(newUser,newGame,newGame.getDate());
    }

    @Test
    void testCreateAsset() {
    }
}