package escapeRoom.GameArea.CluePropFactory;

import escapeRoom.model.GameArea.CluePropFactory.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CluePropFactoryTest {

    @Test
    void ClueFactoryTest() {
        GameElementFactory clueFactory = new ClueFactory();
        GameElement clue = clueFactory.createGameElement(ClueType.ENIGMA, 1);

        Assertions.assertNotNull(clue, "Clue should not be null.");
        Assertions.assertTrue(clue instanceof Clue, "Object should be of Clue type");
        Assertions.assertEquals(ClueType.ENIGMA, clue.getType(), "Clue type should be Enigma");
        Assertions.assertEquals(0, clue.getId(), "Default ID should be 0");
        Assertions.assertEquals("0, ENIGMA", clue.printElement(), "Printed output is incorrect");


    }

    @Test
    void PropFactoryTest() {
        GameElementFactory propFactory = new PropFactory();
        GameElement prop = propFactory.createGameElement(PropType.CLOSET, 1);

        Assertions.assertNotNull(prop, "Clue should not be null.");
        Assertions.assertTrue(prop instanceof Prop, "Object should be of Prop type");
        Assertions.assertEquals(PropType.CLOSET, prop.getType(), "Prop type should be CLOSET");
        Assertions.assertEquals(0, prop.getId(), "Default ID should be 0");
        Assertions.assertEquals("0, CLOSET, Value: 140.0", prop.printElement(), "Printed output is incorrect");

    }

    @Test
    void testCustomClueId() {
        Clue clue = new Clue(ClueType.INDICATION, 101);

        Assertions.assertEquals(101, clue.getId(), "ID should be 101");
        Assertions.assertEquals(ClueType.INDICATION, clue.getType(), "Type should be INDICATION");
        Assertions.assertEquals("101, INDICATION", clue.printElement(), "Printed output is incorrect");
    }

    @Test
    void testCustomPropId() {
        Prop prop = new Prop(PropType.SPADE, 202);

        Assertions.assertEquals(202, prop.getId(), "ID should be 202");
        Assertions.assertEquals(PropType.SPADE, prop.getType(), "Type should be SPADE");
        Assertions.assertEquals(20.0, prop.getValue(), 0.01, "Value should be 20.0");
        Assertions.assertEquals("202, SPADE, Value: 20.0", prop.printElement(), "Printed output is incorrect");

    }
}
