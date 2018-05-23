package usecases;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.DiagramComponent;
import interactr.cs.kuleuven.be.ui.Controller;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.Window;
import interactr.cs.kuleuven.be.ui.control.DiagramWindow;
import interactr.cs.kuleuven.be.ui.control.diagram.DiagramView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateNewDiagram {

    private Window window = new Window("Test Window");

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
        assertNotNull(controller.getActiveSubwindow());
    }

    /**
     * Adds a new subwindow, does control D, moves the new window to the right
     * After that select the onther subwindow and check if the diagrams are the same
     */
    @Test
    void createNewEmptyDiagram(){
        Window.replayRecording("createNewEmptyDiagram1.txt", window);
        Diagram diagram = getDiagram();
        Window.replayRecording("createNewEmptyDiagram2.txt", window);
        assertEquals(diagram, getDiagram() );
    }

    /**
     * Adds a new subwindow, a copy, and a new party in the first subwindow,
     * Checks whether the party exists in the copied subwindow.
     */
    @Test
    void createNewDiagramWithParty(){
        Window.replayRecording("createNewDiagramWithParty01.txt", window);
        assertEquals(1, getDiagram().getParties().size());
    }

    /**
     * Creates a subwindow and adds a party, then copies the subwindow.
     * Checks that the party is at the same relative position in both subwindows
     */
    @Test
    void copyDiagramWithParty() {
        Window.replayRecording("copyDiagramWithParty.txt", window);
        DiagramWindow win1 = getActiveDiagramWindow();
        window.getEventHandler().getController().activateSubWindow(10,10);
        DiagramWindow win2 = getActiveDiagramWindow();
        assertNotEquals(win1, win2, "Recording is broken: Selected the same subwindow twice");
        DiagramComponent comp1 = win1.getActiveView().getParty(166,66);
        DiagramComponent comp2 = win2.getActiveView().getParty(166,66);
        assertEquals(comp1, comp2);
    }

    // Returns the currently active diagram window for the scene
    //  Convenience method
    private DiagramWindow getActiveDiagramWindow() {
        return ((DiagramWindow)window.getEventHandler().getController().getActiveSubwindow());
    }

    // Returns the currently active view for the scene
    //  Convenience method
    private DiagramView getActiveView() {
        return ((DiagramWindow)window.getEventHandler().getController().getActiveSubwindow()).getActiveView();
    }

    // Returns the diagram for the currently active subwindow
    //  Convenience method
    private Diagram getDiagram() {
        return ((DiagramWindow)window.getEventHandler().getController().getActiveSubwindow()).getDiagram();
    }

}
