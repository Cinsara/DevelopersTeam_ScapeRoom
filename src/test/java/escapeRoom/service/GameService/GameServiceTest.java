package escapeRoom.service.GameService;

import escapeRoom.gameArea.GameBuilder.Game;
import escapeRoom.gameArea.GameBuilder.GameBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {
    static GameService service;

    @BeforeAll
    static void setUp() throws SQLException {
        service = new GameService();
    }
    @Test
    void getTableName() {
    }

    @Test
    void mapResultSetToEntity() {
    }

    @Test
    void create() throws SQLException {
        Game newGame = GameBuilder.createGame(1, LocalDate.now());
        Game returnedGame = service.create(newGame);
        //assertEquals(9,returnedGame.get_id());
        assertEquals(newGame.getDate(),returnedGame.getDate());
    }

    @Test
    void read() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}