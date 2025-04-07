package escapeRoom.GameArea.GameBuilder;

import escapeRoom.model.GameArea.GameBuilder.Game;
import escapeRoom.model.GameArea.GameBuilder.GameBuilder;
import escapeRoom.model.PeopleArea.User;
import escapeRoom.model.PeopleArea.UserBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameBuilderTest {

    @Test
    void createGame() {
        GameBuilder newGameBuilder = new GameBuilder(2, LocalDate.of(2015,9,26));
        Game newGame = newGameBuilder.build();
        assertEquals(2,newGame.getRoom_id());
        assertEquals(2015,newGame.getDate().getYear());
        User newPlayer = new UserBuilder("Aure","Dardar").setId(10).build();
        newGame.addPlayer(newPlayer);
        assertEquals(10, newGame.getPlayers().getFirst().getId());
        System.out.println(newGame.toString());
    }

    @Test
    void testGetCaptain(){
        GameBuilder newGameBuilder = new GameBuilder(2, LocalDate.of(2015,9,26));
        Game newGame = newGameBuilder.build();
        newGame.addPlayer(new UserBuilder("Aure","Dardar").setId(10).build());
        newGame.addPlayer(new UserBuilder("Aure","Dardar").setId(11).build());
        newGame.addPlayer(new UserBuilder("Aure","Dardar").setId(12).build());
        newGame.setCaptain(new UserBuilder("Aure","Dardar").setId(13).build());
        System.out.println(newGame.getCaptainId());
        assertEquals(4, newGame.getPlayers().size());
        assertTrue(newGame.getPlayers().stream().map(User::getId).toList().contains(13));
    }
}