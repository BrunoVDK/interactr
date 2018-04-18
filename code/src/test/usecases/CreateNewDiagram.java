package usecases;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.DiagramComponent;
import interactr.cs.kuleuven.be.ui.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CreateNewDiagram {

    private DiagramWindow diagramWindow = new DiagramWindow();

    @BeforeEach
    void setUp(){
        diagramWindow.setEventHandler(new EventHandler(new DiagramController()));
        diagramWindow.setPaintBoard(new PaintBoard(diagramWindow, diagramWindow.getEventHandler().getDiagramController()));
    }

    /**
     * Adds a new subwindow, does control D, moves the new window to the right
     * After that select the onther subwindow and check if the diagrams are the same
     */
    @Test
    void createNewEmptyDiagram(){
        DiagramWindow.replayRecording("createNewEmptyDiagram1.txt",diagramWindow);
        Diagram diagram = diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram();
        DiagramWindow.replayRecording("createNewEmptyDiagram2.txt",diagramWindow);
        assertEquals(diagram,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram() );
    }

    /**
     * Adds a new subwindow, a copy, and a new party in the first subwindow,
     * Checks whether the party exists in the copied subwindow.
     */
    @Test
    void createNewDiagramWithParty(){
        DiagramWindow.replayRecording("createNewDiagramWithParty01.txt",diagramWindow);
        assertEquals(1,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getParties().size());
    }

    /**
     * Creates a subwindow and adds a party, then copies the subwindow.
     * Checks that the party is at the same relative position in both subwindows
     */
    @Test
    void copyDiagramWithParty() {
        DiagramWindow.replayRecording("copyDiagramWithParty.txt",diagramWindow);
        SubWindow win1 = diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow();
        diagramWindow.getEventHandler().getDiagramController().activateSubWindow(10,10);
        SubWindow win2 = diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow();
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
        DiagramWindow.replayRecording("createNewDiagramEditInvalidLabel01.txt",diagramWindow);
        assertEquals("a:A", diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getSelectedComponent().getLabel());
    }

}
