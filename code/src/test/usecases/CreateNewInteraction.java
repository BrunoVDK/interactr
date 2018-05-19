package usecases;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.ui.Controller;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.Window;
import interactr.cs.kuleuven.be.ui.control.DiagramWindow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateNewInteraction {

    private Window window = new Window("Test Window");

    @BeforeEach
    void setUp(){
        window.setEventHandler(new EventHandler(new Controller()));
        window.setPaintBoard(new PaintBoard(window, window.getEventHandler().getController()));
    }
    @Test
    void stepByStep(){
        // Precondition - create new diagram
        Window.replayRecording("steps/createNewDiagram.txt", window);
        assertNotNull(getActiveDiagramWindow());
        DiagramWindow original = getActiveDiagramWindow();
        // Move the currently active diagram window aside
        Window.replayRecording("steps/moveNewDiagram.txt", window);
        // Create a duplicate of the diagram window
        Window.replayRecording("steps/createCopyDiagram.txt", window);
        assertEquals(original.getDiagram(), getDiagram());
    }

    @Test
    void createNewSubWindow(){
        Window.replayRecording("createNewSubWindow.txt", window);
        assertNotEquals(null, window.getEventHandler().getController().getActiveSubwindow());
    }

    // Returns the diagram for the currently active subwindow
    //  Convenience method
    private Diagram getDiagram() {
        return ((DiagramWindow)window.getEventHandler().getController().getActiveSubwindow()).getDiagram();
    }

    // Returns the currently active diagram window for the scene
    //  Convenience method
    private DiagramWindow getActiveDiagramWindow() {
        return ((DiagramWindow)window.getEventHandler().getController().getActiveSubwindow());
    }

}
