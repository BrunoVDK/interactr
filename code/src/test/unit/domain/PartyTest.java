package unit.domain;

import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.exceptions.InvalidLabelException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PartyTest {

    @Test
    void createValidTest() {
        Party party = new Party("valid:Label");
        assertEquals(party.getLabel(), "valid:Label");
        party = new Party(":Label");
        assertEquals(party.getLabel(), ":Label");
    }

    @Test
    void invalidLabelTest1() {
        assertThrows(InvalidLabelException.class,
                ()->{new Party("Invalid:Label");});
    }

    @Test
    void invalidLabelTest2() {
        assertThrows(InvalidLabelException.class,
                ()->{new Party(":label");});
    }

    @Test
    void invalidLabelTest3() {
        assertThrows(InvalidLabelException.class,
                ()->{new Party("invalid:");});
    }

    @Test
    void canHaveAsLabelTest() {
        Party party = Party.createParty();
        assertTrue(party.canHaveAsLabel("valid:Label"));
        assertFalse(party.canHaveAsLabel("Invalid:Label"));
    }

    @Test
    void toStringTest() {
        Party party = new Party();
        assertEquals(party.toString(), party.getLabel());
    }

}
