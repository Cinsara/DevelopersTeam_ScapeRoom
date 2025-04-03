package escapeRoom.Controller.GameController;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    static GameController gameController;

    @BeforeAll
    static void setUp(){
        gameController = new GameController();
    }

    @Test
    void bookGame() {
        String simulateInput = "2025 04 06\n3\n3";
        InputStream originalIn = System.in;
        try{
            ByteArrayInputStream testIn = new ByteArrayInputStream(simulateInput.getBytes());
            System.setIn(testIn);
            gameController.bookGame();

        }finally{
            System.setIn(originalIn);
        }
    }
}