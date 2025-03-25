package escapeRoom.service.GameService;

import escapeRoom.gameArea.GameBuilder.Game;
import escapeRoom.gameArea.GameBuilder.GameMaker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {
    static GameMaker gameMaker;
    static GameService service;

    @BeforeAll
    static void setUp() throws SQLException {
        service = new GameService();
        gameMaker = new GameMaker();
    }
    @Test
    void getTableName() {
    }

    @Test
    void mapResultSetToEntity() {

    }

    @Test
    void create() throws SQLException {
        GameMaker.GameBuilder builder = gameMaker.createGameBuilder(1,LocalDate.now());
        Game newGame = builder.build();
        Game returnedGame = service.create(newGame);
        assertEquals(5,returnedGame.getId());
        assertEquals(newGame.getDate(),returnedGame.getDate());
    }

    @Test
    void read() throws SQLException {
        Optional<Game> newGame = service.read(1);
        assertTrue(newGame.isPresent());
        Game game = newGame.get();
        assertEquals(1,game.getId());
        assertTrue(game.isSuccess());
        assertEquals(2024, game.getDate().getYear());
    }

    @Test
    void update() throws SQLException {
        GameMaker.GameBuilder newBuilder = gameMaker.createGameBuilder(4,LocalDate.now());
        Game newGame = newBuilder.set_id(4).setSuccess(true).setCaptain_id(2).setEllapsedTimeInSeconds(4800).build();
        service.update(newGame);
    }

    @Test
    void delete() throws SQLException {
        assertFalse(service.delete(2));
    }
    @Test
    void getAll() throws SQLException {
        List<Game> games = service.getAllEntities(service.getConnection());
        games.forEach(game -> System.out.println(game.toString()));
    }
}