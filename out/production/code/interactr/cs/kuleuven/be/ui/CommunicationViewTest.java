package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.Party;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class CommunicationViewTest {

    private CommunicationView communicationView;
    private Party party;
    private Diagram diagram;

    @BeforeEach
    public void setup() {
        communicationView = new CommunicationView();
        party = mock(Party.class);
        diagram = mock(Diagram.class);
    }

    @Test
    public void addPartyTest() {
        communicationView.addParty(diagram,party,5,5);
        assert(communicationView.getPartyAt(5,5) == party);
    }

    @Test
    public void registerPartyTest() {
        communicationView.registerParty(party,5,5);
        assert(communicationView.getPartyAt(5,5) == party);
    }

}
