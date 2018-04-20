package unit.ui;

import interactr.cs.kuleuven.be.ui.DiagramWindow;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiagramWindowTest {

    @Test
    void getSizeTest() {
        DiagramWindow window = new DiagramWindow();
        java.awt.EventQueue.invokeLater(() -> {
            window.show();
            assertTrue(window.getWidth() > 1);
            assertTrue(window.getHeight() > 1);
        });
    }

}