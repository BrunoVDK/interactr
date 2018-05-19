package unit.ui;

import interactr.cs.kuleuven.be.ui.control.DiagramWindow;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DiagramWindowTest {

    @Test
    void createValidTest() {
        DiagramWindow window = new DiagramWindow();
        assertNotNull(window.getDiagram());
        assertNotNull(window.getFrame());
    }

}