package escapeRoom.Service.ManyToManyService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MTMServiceTest {

    static GameHasUserService gameHasUserService;
    static GameUsesClueService gameUsesClueService;

    @BeforeAll
    static void setUp() throws SQLException {
        gameHasUserService = new GameHasUserService();
        gameUsesClueService = new GameUsesClueService();
    }

    @Test
    void getMatches() throws SQLException {
        List<Integer> clueMatches = gameUsesClueService.getMatches(1);
        List<Integer> userMatches = gameHasUserService.getMatches(4);
        assertEquals(2,clueMatches.getFirst());
        assertEquals(2,userMatches.getFirst());
    }

    @Test
    void createMatch() throws SQLException {
        gameUsesClueService.createMatch(3,3);
        gameHasUserService.createMatch(4,3);
    }

    @Test
    void deleteMatch() throws SQLException {
        assertTrue(gameUsesClueService.deleteMatch(3,3));
        assertTrue(gameHasUserService.deleteMatch(4,3));

    }

    @Test
    void getMatchesReverse() throws SQLException {
        List<Integer> gamesMatches = gameHasUserService.getMatchesReverse(2);
        assertEquals(3, gamesMatches.size());
        assertEquals(1,gamesMatches.getFirst());

    }
}