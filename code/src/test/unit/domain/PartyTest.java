package unit.domain;

import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.exceptions.InvalidLabelException;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PartyTest {

    @Test
    public void createValidTest() {
        Party party = new Party("valid:Label");
        assertEquals(party.getLabel(), "valid:Label");
        party = new Party(":Label");
        assertEquals(party.getLabel(), ":Label");
    }

    @Test(expected = InvalidLabelException.class)
    public void invalidLabelTest1() {
        new Party("Invalid:Label");
    }

    @Test(expected = InvalidLabelException.class)
    public void invalidLabelTest2() {
        new Party(":label");
    }

    @Test(expected = InvalidLabelException.class)
    public void invalidLabelTest3() {
        new Party("invalid:");
    }

    @Test
    public void setValidLabelTest() {
        Party party = Party.createParty();
        party.setLabel("valid:Label");
        assertEquals(party.getLabel(), "valid:Label");
    }

    @Test(expected = InvalidLabelException.class)
    public void setInvalidLabelTest() {
        Party party = Party.createParty();
        party.setLabel("Invalid:Label");
    }

    @Test
    public void canHaveAsLabelTest() {
        Party party = Party.createParty();
        assertTrue(party.canHaveAsLabel("valid:Label"));
        assertFalse(party.canHaveAsLabel("Invalid:Label"));
    }

    @Test
    public void toStringTest() {
        Party party = new Party();
        assertEquals(party.toString(), party.getLabel());
    }

}
