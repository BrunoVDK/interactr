package usecases;

import interactr.cs.kuleuven.be.ui.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoveSubwindow {

    private Window window = new Window();

    @BeforeEach
    void setUp(){
        window.setEventHandler(new EventHandler(new DiagramController()));
        window.setPaintBoard(new PaintBoard(window, window.getEventHandler().getDiagramController()));
    }

    @Test
    void stepByStep(){
        DiagramController controller = window.getEventHandler().getDiagramController();
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
        assertTrue(window.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getX() > 0);
        assertTrue(window.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getY() > 0);
    }
}
