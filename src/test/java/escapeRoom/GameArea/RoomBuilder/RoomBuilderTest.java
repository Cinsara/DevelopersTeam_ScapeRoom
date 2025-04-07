package escapeRoom.GameArea.RoomBuilder;

import escapeRoom.model.GameArea.RoomBuilder.Difficulty;
import escapeRoom.model.GameArea.RoomBuilder.Room;
import escapeRoom.model.GameArea.RoomBuilder.RoomBuilder;
import escapeRoom.model.GameArea.RoomBuilder.Theme;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoomBuilderTest {

    @Test
    void setId() {
        RoomBuilder builder = new RoomBuilder();
        Room room = builder.setId(2).build();
        assertTrue(room != null);
        assertEquals(2, room.getId());
    }

    @Test
    void setRoomName() {
        RoomBuilder builder = new RoomBuilder();
        Room room = builder.setRoomName("Hola").build();
        assertTrue(room != null);
        assertEquals("Hola", room.getName());
    }

    @Test
    void setRoomTheme() {
        RoomBuilder builder = new RoomBuilder();
        Room room = builder.setRoomTheme(Theme.FANTASTIC.getDisplayName()).build();
        assertTrue(room != null);
        assertEquals(Theme.FANTASTIC,room.getTheme());
    }

    @Test
    void setRoomDifficulty() {
        RoomBuilder builder = new RoomBuilder();
        Room room = builder.setRoomDifficulty(Difficulty.EASY).build();
        assertTrue(room != null);
        assertEquals(Difficulty.EASY,room.getDifficulty());
    }

    @Test
    void setRoomClues() {
        RoomBuilder builder = new RoomBuilder();
        Room room = builder.setRoomClues(List.of(1,2)).build();
        assertTrue(room != null);
        assertEquals(List.of(1,2),room.getClues_id());
    }

    @Test
    void setRoomProps() {
        RoomBuilder builder = new RoomBuilder();
        Room room = builder.setRoomProps(List.of(1,2)).build();
        assertTrue(room != null);
        assertEquals(List.of(1,2),room.getProps_id());
    }

    @Test
    void build() {
    }
}