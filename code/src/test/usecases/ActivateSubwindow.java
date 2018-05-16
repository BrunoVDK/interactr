package test.usecases;

import interactr.cs.kuleuven.be.ui.*;
import interactr.cs.kuleuven.be.ui.control.SubWindow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ActivateSubwindow {

    private Window window = new Window();

    @BeforeEach
    void setUp(){
        window.setEventHandler(new EventHandler(new Controller()));
        window.setPaintBoard(new PaintBoard(window, window.getEventHandler().getController()));
    }

    @Test
    void stepByStep(){
        Controller controller = window.getEventHandler().getController();
        // Precondition
        Window.replayRecording("steps/createNewDiagram.txt", window);
        SubWindow original = controller.getActiveSubwindow();
        assertNotNull(original);
        Window.replayRecording("steps/moveNewDiagram.txt", window);
        assertTrue(0 < controller.getActiveSubwindow().getFrame().getX());
        assertTrue(0 < controller.getActiveSubwindow().getFrame().getY());
        Window.replayRecording("steps/createCopyDiagram.txt", window);
        assertNotEquals(original, controller.getActiveSubwindow());
        Window.replayRecording("steps/selectMovedDiagram.txt", window);
        assertEquals(original, controller.getActiveSubwindow());

    }

    /**
     * Adds a new sibwindow, and creates a copy of this subwindow. The copy is now activated (=first)
     * Afterwards click back on the original, this should now be the active subWindow
     */
    @Test
    void activateSubwindow(){
        Window.replayRecording("activateSubwindow1.txt", window);
        SubWindow first = window.getEventHandler().getController().getActiveSubwindow();
        Window.replayRecording("activateSubwindow2.txt", window);
        assertNotEquals(first, window.getEventHandler().getController().getActiveSubwindow());
    }

}