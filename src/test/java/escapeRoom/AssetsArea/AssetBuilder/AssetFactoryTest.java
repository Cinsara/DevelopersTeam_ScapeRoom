package escapeRoom.AssetsArea.AssetBuilder;

import escapeRoom.Model.AssetsArea.RewardBuilder.Reward;
import escapeRoom.Model.GameArea.CluePropFactory.Clue;
import escapeRoom.Model.GameArea.CluePropFactory.ClueFactory;
import escapeRoom.Model.GameArea.CluePropFactory.ClueType;
import escapeRoom.Model.GameArea.RoomBuilder.Difficulty;
import escapeRoom.Model.GameArea.RoomBuilder.Theme;
import escapeRoom.Model.AssetsArea.AssetBuilder.Asset;
import escapeRoom.Model.AssetsArea.AssetBuilder.AssetFactory;
import escapeRoom.Model.AssetsArea.AssetBuilder.AssetType;
import escapeRoom.Model.PeopleArea.User;
import escapeRoom.Model.GameArea.GameBuilder.Game;
import escapeRoom.Model.GameArea.RoomBuilder.Room;

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
        Room newRoom = new Room(1,"Rock", Theme.FANTASTIC.getDisplayName(), Difficulty.HARD, List.of(1),List.of(2));
        newCertificate.expressAsset(newUser,newRoom,LocalDate.now());
        Reward newReward = (Reward) factory.createAsset(AssetType.REWARD,1,1);
        Clue newClue = (Clue) new ClueFactory().createGameElement(ClueType.ENIGMA,1);
        Game newGame = new Game(1,1,LocalDate.of(2021,10,27),List.of(new User("ariel","sharon","lolo@gmail.com","0033", LocalDate.of(1983,11,26),false)),3,true,List.of(newClue),100,List.of(newReward));
        newReward.expressAsset(newUser,newGame,newGame.getDate());
        Asset newTicket = factory.createAsset(AssetType.TICKET,1,1);
        System.out.println(newTicket.getId());
        System.out.println(newTicket.getUser_id());
        newTicket.expressAsset(newUser,newGame,newGame.getDate());
    }

    @Test
    void testCreateAsset() {
    }
}