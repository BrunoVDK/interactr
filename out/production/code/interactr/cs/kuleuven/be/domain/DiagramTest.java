package interactr.cs.kuleuven.be.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class DiagramTest {

    private Diagram diagram;
    private Party party;

    @BeforeEach
    void setUp() {
        party = mock(Party.class);
        diagram = new Diagram();
    }

    @Test
    void addPartyTest() {
        diagram.addParty(party);
        assert(diagram.getParties().contains(party));
    }

    @Test
    void replacePartyTest() {
        diagram.addParty(party);
        Party otherParty = mock(Party.class);
        diagram.replaceParty(party,otherParty);
        assert(diagram.getParties().contains(otherParty));
        assert(!diagram.getParties().contains(party));
    }
}
