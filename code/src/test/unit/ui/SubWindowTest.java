package test.unit.ui;

import interactr.cs.kuleuven.be.exceptions.IllegalWindowFrameException;
import interactr.cs.kuleuven.be.ui.SubWindow;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubWindowTest {

    @Test
    void createValidTest() {
        SubWindow window = new SubWindow();
        assertNotNull(window.getActiveView());
        assertNotNull(window.getDiagram());
        assertNotNull(window.getFrame());
    }

    @Test
    void setValidSizeTest() {
        SubWindow window = new SubWindow();
        window.setSize(100, 100);
        assertEquals(window.getFrame().getWidth(), 100);
        assertEquals(window.getFrame().getHeight(), 100);
    }

    @Test
    void setInvalidSizeTest() {
        SubWindow window = new SubWindow();
        assertThrows(IllegalWindowFrameException.class,
                ()->{window.setSize(1, 1);});
    }

}