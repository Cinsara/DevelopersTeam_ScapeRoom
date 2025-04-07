package escapeRoom.Service.GameService;

import escapeRoom.model.GameArea.GameBuilder.Game;
import escapeRoom.model.GameArea.GameBuilder.GameBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {
    //    static GameMaker gameMaker;
    static GameService service;

    @BeforeAll
    static void setUp() throws SQLException {
        service = new GameService();
//        gameMaker = new GameMaker();
    }
    @Test
    void getTableName() {
    }

    @Test
    void mapResultSetToEntity() {

    }

    @Test
    void create() throws SQLException {
        GameBuilder builder = new GameBuilder(1,LocalDate.of(2024,3,16));
        Game newGame = builder.build();
        Game returnedGame = service.create(newGame);
        assertEquals(12,returnedGame.getId());
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
        GameBuilder newBuilder = new GameBuilder(4,LocalDate.now());
        Game newGame = newBuilder.setId(4).setSuccess(true).setCaptainId(2).setEllapsedTimeInSeconds(4800).build();
        service.update(newGame);
    }

    @Test
    void delete() throws SQLException {
        assertTrue(service.delete(3));
    }

    @Test
    void getAll() throws SQLException {
        List<Game> games = service.getAllEntities(service.getConnection());
        games.forEach(game -> System.out.println(game.toString()));
    }
}
