package test.unit.domain;

import interactr.cs.kuleuven.be.domain.ActorParty;
import interactr.cs.kuleuven.be.exceptions.InvalidLabelException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorPartyTest {

    @Test
    void createValidTest() {
        ActorParty party = new ActorParty("valid:Label");
        assertEquals(party.getLabel(), "valid:Label");
        party = new ActorParty(":Label");
        assertEquals(party.getLabel(), ":Label");
    }

    @Test
    void invalidLabelTest1() {
        assertThrows(InvalidLabelException.class,
                ()->{new ActorParty("Invalid:Label");});
    }

    @Test
    void invalidLabelTest2() {
        assertThrows(InvalidLabelException.class,
                ()->{new ActorParty(":label");});
    }

    @Test
    void invalidLabelTest3() {
        assertThrows(InvalidLabelException.class,
                ()->{new ActorParty("invalid:");});
    }

    @Test
    void toStringTest() {
        ActorParty party = new ActorParty();
        assertEquals(party.toString(), party.getLabel());
    }

    @Test
    void proposedFigureTest() {
        ActorParty party = new ActorParty();
        assertNotNull(party.proposedFigure());
    }

}
