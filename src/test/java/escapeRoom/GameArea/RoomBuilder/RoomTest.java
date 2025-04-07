package escapeRoom.GameArea.RoomBuilder;

import escapeRoom.model.GameArea.RoomBuilder.Difficulty;
import escapeRoom.model.GameArea.RoomBuilder.Room;
import escapeRoom.model.GameArea.RoomBuilder.Theme;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {
    static Room newRoom;
    @BeforeAll
    static void setUp(){
        newRoom = new Room(1,"Acid Fantasy", Theme.FANTASTIC.getDisplayName(), Difficulty.HARD, List.of(1,2,3),List.of(1,2,3));
    }

    @Test
    void getName() {
    }

    @Test
    void setName() {
    }

    @Test
    void get_id() {
    }

    @Test
    void getTheme() {
    }

    @Test
    void getDifficulty() {
    }

    @Test
    void setDifficulty() {
    }

    @Test
    void getClues_id() {
    }

    @Test
    void setClues_id() {
    }

    @Test
    void addClue() {
    }

    @Test
    void removeClue() {
        int[] arr = {1,3};
        newRoom.removeClue(2);
        assertEquals(List.of(1,3),newRoom.getClues_id());

    }

    @Test
    void getProps_id() {
    }

    @Test
    void setProps_id() {
    }

    @Test
    void addProp() {
    }

    @Test
    void removeProp() {
    }

    @Test
    void testToString() {
    }
}