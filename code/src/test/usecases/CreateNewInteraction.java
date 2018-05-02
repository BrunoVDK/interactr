package usecases;

import interactr.cs.kuleuven.be.ui.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateNewInteraction {

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
        assertNotNull(controller.getActiveSubwindow());
        SubWindow original = controller.getActiveSubwindow();
        Window.replayRecording("steps/moveNewDiagram.txt", window);
        Window.replayRecording("steps/createCopyDiagram.txt", window);
        assertEquals(original.getDiagram(), controller.getActiveSubwindow().getDiagram());
    }

    @Test
    void createNewSubWindow(){
        Window.replayRecording("createNewSubWindow.txt", window);
        assertNotEquals(null, window.getEventHandler().getDiagramController().getActiveSubwindow());
    }

}
