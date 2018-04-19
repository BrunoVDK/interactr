package test.usecases;

import interactr.cs.kuleuven.be.ui.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateNewInteraction {

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
        SubWindow original = controller.getActiveSubwindow();
        DiagramWindow.replayRecording("steps/moveNewDiagram.txt",diagramWindow);
        DiagramWindow.replayRecording("steps/createCopyDiagram.txt",diagramWindow);
        assertEquals(original.getDiagram(), controller.getActiveSubwindow().getDiagram());
    }

    @Test
    void createNewSubWindow(){
        DiagramWindow.replayRecording("createNewSubWindow.txt",diagramWindow);
        assertNotEquals(null, diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow());
    }

}
