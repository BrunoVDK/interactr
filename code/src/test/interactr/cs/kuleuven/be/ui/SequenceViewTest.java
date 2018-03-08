package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.Party;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class SequenceViewTest {

    private SequenceView sequenceView;
    private Party party;
    private Diagram diagram;

    @BeforeEach
    public void setup() {
        sequenceView = new SequenceView();
        party = mock(Party.class);
        diagram = mock(Diagram.class);
    }

    @Test
    public void addPartyTest() {
        sequenceView.addParty(diagram,party,5,5);
        assert(sequenceView.getPartyAt(5,5) == party);
    }

    @Test
    public void selectableTest() {
        sequenceView.addParty(diagram,party,5,5);
        assert(sequenceView.selectableComponentAt(diagram,5,5) == party);
    }

    @Test
    public void registerPartyTest() {
        sequenceView.registerParty(party,5,5);
        assert(sequenceView.getPartyAt(5,5) == party);
    }

}
