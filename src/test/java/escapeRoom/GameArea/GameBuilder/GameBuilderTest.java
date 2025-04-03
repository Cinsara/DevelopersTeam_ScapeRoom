package escapeRoom.GameArea.GameBuilder;

import escapeRoom.model.GameArea.GameBuilder.Game;
import escapeRoom.model.GameArea.GameBuilder.GameBuilder;
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
        newGame.addPlayer(10);
        assertEquals(10, newGame.getPlayers_id().getFirst());
        newGame.calculateResult(List.of(1,5,6));
        System.out.println(newGame.toString());
    }

    @Test
    void testGetCaptain(){
        GameBuilder newGameBuilder = new GameBuilder(2, LocalDate.of(2015,9,26));
        Game newGame = newGameBuilder.build();
        newGame.addPlayer(10);
        newGame.addPlayer(11);
        newGame.addPlayer(12);
        newGame.setCaptain(11);
        System.out.println(newGame.getCaptainId());
    }
}