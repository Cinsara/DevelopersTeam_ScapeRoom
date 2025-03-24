package escapeRoom.gameArea.GameBuilder;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GameBuilderTest {

    @Test
    void createGame() {
        Game newGame = GameBuilder.createGame(2, LocalDate.of(2015,9,26));
        assertEquals(2,newGame.getRoom_id());
        assertEquals(2015,newGame.getDate().getYear());
        newGame.addPlayer(10);
        assertEquals(10, newGame.getPlayers_id().getFirst());
        newGame.calculateResult();
        System.out.println(newGame.toString());
    }

    @Test
    void testGetCaptain(){
        Game newGame = GameBuilder.createGame(2, LocalDate.of(2015,9,26));
        System.out.println(newGame.getCaptain_id());
    }
}