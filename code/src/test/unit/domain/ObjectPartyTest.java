package unit.domain;

import interactr.cs.kuleuven.be.domain.ObjectParty;
import interactr.cs.kuleuven.be.exceptions.InvalidLabelException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectPartyTest {

    @Test
    void createValidTest() {
        ObjectParty party = new ObjectParty("valid:Label");
        assertEquals(party.getLabel(), "valid:Label");
        party = new ObjectParty(":Label");
        assertEquals(party.getLabel(), ":Label");
    }

    @Test
    void invalidLabelTest1() {
        assertThrows(InvalidLabelException.class,
                ()->{new ObjectParty("Invalid:Label");});
    }

    @Test
    void invalidLabelTest2() {
        assertThrows(InvalidLabelException.class,
                ()->{new ObjectParty(":label");});
    }

    @Test
    void invalidLabelTest3() {
        assertThrows(InvalidLabelException.class,
                ()->{new ObjectParty("invalid:");});
    }

    @Test
    void toStringTest() {
        ObjectParty party = new ObjectParty();
        assertEquals(party.toString(), party.getLabel());
    }

    @Test
    void proposedFigureTest() {
        ObjectParty party = new ObjectParty();
        assertNotNull(party.proposedFigure());
    }

   /* @Test
    void proposedReplacementTest() {
        ObjectParty party = new ObjectParty();
        assertNotNull(party.proposedReplacement());
    }
*/
}