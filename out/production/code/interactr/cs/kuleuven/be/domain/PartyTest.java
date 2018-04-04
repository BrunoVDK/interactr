package interactr.cs.kuleuven.be.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PartyTest {

    private Party party;

    @BeforeEach
    void setUp() {
        party = new Party("valid:Party");
    }

    @Test
    void canHaveAsLabelTest() {
        assert(party.canHaveAsLabel("valid:Label"));
        assert(!party.canHaveAsLabel("Invalid:Label"));
    }

    @Test
    void deleteTest() {
        Diagram diagram = new Diagram();
        diagram.addParty(party);
        party.delete(diagram);
        assert(!diagram.getParties().contains(party));
    }

}
