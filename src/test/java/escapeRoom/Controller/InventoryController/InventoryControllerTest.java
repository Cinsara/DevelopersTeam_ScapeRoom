package escapeRoom.Controller.InventoryController;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryControllerTest {

    @Test
    void showInventory() {
        new InventoryController().showInventory();
    }
}