package usecases;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.DiagramComponent;
import interactr.cs.kuleuven.be.ui.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateNewDiagram {

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
    }

    /**
     * Adds a new subwindow, does control D, moves the new window to the right
     * After that select the onther subwindow and check if the diagrams are the same
     */
    @Test
    void createNewEmptyDiagram(){
        Window.replayRecording("createNewEmptyDiagram1.txt", window);
        Diagram diagram = window.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram();
        Window.replayRecording("createNewEmptyDiagram2.txt", window);
        assertEquals(diagram, window.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram() );
    }

    /**
     * Adds a new subwindow, a copy, and a new party in the first subwindow,
     * Checks whether the party exists in the copied subwindow.
     */
    @Test
    void createNewDiagramWithParty(){
        Window.replayRecording("createNewDiagramWithParty01.txt", window);
        assertEquals(1, window.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getParties().size());
    }

    /**
     * Creates a subwindow and adds a party, then copies the subwindow.
     * Checks that the party is at the same relative position in both subwindows
     */
    @Test
    void copyDiagramWithParty() {
        Window.replayRecording("copyDiagramWithParty.txt", window);
        SubWindow win1 = window.getEventHandler().getDiagramController().getActiveSubwindow();
        window.getEventHandler().getDiagramController().activateSubWindow(10,10);
        SubWindow win2 = window.getEventHandler().getDiagramController().getActiveSubwindow();
        assertNotEquals(win1, win2, "Recording is broken: Selected the same subwindow twice");
        DiagramComponent comp1 = win1.getActiveView().getComponent(166,66);
        DiagramComponent comp2 = win2.getActiveView().getComponent(166,66);
        assertEquals(comp1, comp2);
    }

    /**
     * Adds a new subwindow with a party with the labal "a:A" then creates a copy
     * Change the label of the party to "iets" and check if the copy has still
     * the old label.
     */
    @Test
    void createNewDiagramEditInvalidLabel(){
        Window.replayRecording("createNewDiagramEditInvalidLabel01.txt", window);
        assertEquals("a:A", window.getEventHandler().getDiagramController().getActiveSubwindow().getSelectedComponent().getLabel());
    }

}
