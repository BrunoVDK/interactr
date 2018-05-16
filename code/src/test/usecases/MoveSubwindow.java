package test.usecases;

import interactr.cs.kuleuven.be.ui.*;
import interactr.cs.kuleuven.be.ui.control.SubWindow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoveSubwindow {

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
    }

    @Test
    void moveSubwindow(){
        Window.replayRecording("moveSubwindow.txt", window);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getX() > 0);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getY() > 0);
    }
}
