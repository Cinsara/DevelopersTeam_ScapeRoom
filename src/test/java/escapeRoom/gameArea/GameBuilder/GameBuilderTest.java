package escapeRoom.gameArea.GameBuilder;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GameBuilderTest {
    static GameMaker gameMaker;

    @BeforeAll
    static void setUp(){
        gameMaker = new GameMaker();
    }
    @Test
    void createGame() {
        GameMaker.GameBuilder newGameBuilder = gameMaker.createGameBuilder(2, LocalDate.of(2015,9,26));
        Game newGame = newGameBuilder.build();
        assertEquals(2,newGame.getRoom_id());
        assertEquals(2015,newGame.getDate().getYear());
        newGame.addPlayer(10);
        assertEquals(10, newGame.getPlayers_id().getFirst());
        newGame.calculateResult();
        System.out.println(newGame.toString());
    }

    @Test
    void testGetCaptain(){
        GameMaker.GameBuilder newGameBuilder = gameMaker.createGameBuilder(2, LocalDate.of(2015,9,26));
        Game newGame = newGameBuilder.build();
        System.out.println(newGame.getCaptain_id());
    }
}