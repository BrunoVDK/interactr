package test.usecases;

import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.DiagramWindow;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CloseWindow {
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
        assertNotNull(controller.getActiveSubwindow());
        DiagramWindow.replayRecording("steps/pressCloseButton.txt",diagramWindow);
        assertNull(controller.getActiveSubwindow());
    }

    @Test
    void closeWindow(){
        DiagramWindow.replayRecording("closeWindow.txt",diagramWindow);
        assertEquals(null, diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow());
    }
}
