package unit.ui;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.ui.CommunicationView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CommunicationViewTest {

    @Test
    void viewNameTest() {
        CommunicationView communicationView = new CommunicationView(new Diagram());
        assertNotNull(communicationView.viewName());
    }

}