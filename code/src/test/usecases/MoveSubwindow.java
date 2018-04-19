package usecases;

import interactr.cs.kuleuven.be.ui.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoveSubwindow {

    private DiagramWindow diagramWindow = new DiagramWindow();

    @BeforeEach
    void setUp(){
        diagramWindow.setEventHandler(new EventHandler(new DiagramController()));
        diagramWindow.setPaintBoard(new PaintBoard(diagramWindow, diagramWindow.getEventHandler().getDiagramController()));
    }

    @Test
    void stepByStep(){
        DiagramController controller = diagramWindow.getEventHandler().getDiagramController();
        // Precondition
        DiagramWindow.replayRecording("steps/createNewDiagram.txt",diagramWindow);
        SubWindow original = controller.getActiveSubwindow();
        assertNotNull(original);
        DiagramWindow.replayRecording("steps/moveNewDiagram.txt",diagramWindow);
        assertTrue(0 < controller.getActiveSubwindow().getFrame().getX());
        assertTrue(0 < controller.getActiveSubwindow().getFrame().getY());
    }

    @Test
    void moveSubwindow(){
        DiagramWindow.replayRecording("moveSubwindow.txt",diagramWindow);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getX() > 0);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getY() > 0);
    }
}
