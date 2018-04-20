package unit.ui;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.ui.SequenceView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SequenceViewTest {

    @Test
    void viewNameTest() {
        SequenceView communicationView = new SequenceView(new Diagram());
        assertNotNull(communicationView.viewName());
    }

}