package usecases;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.DiagramWindow;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateNewDiagram {

    private DiagramWindow diagramWindow = new DiagramWindow();

    @BeforeEach
    void setUp(){
        diagramWindow.setEventHandler(new EventHandler(new DiagramController()));
        diagramWindow.setPaintBoard(new PaintBoard(diagramWindow, diagramWindow.getEventHandler().getDiagramController()));
    }

    /**
     * Adds a new subwindow, does control D, moves the new window to the right
     * After that selct the onther subwindow and check if the diagrams are the same
     */
    @Test
    void createNewEmptyDiagram(){
        DiagramWindow.replayRecording("createNewEmptyDiagram1.txt",diagramWindow);
        Diagram diagram = diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram();
        DiagramWindow.replayRecording("createNewEmptyDiagram2.txt",diagramWindow);
        assertEquals(diagram,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram() );
    }

    /**
     * Adds a new subwindow, a new party at the location:
     * After that a new Copy is created and checked if there is a party on the same relative position.
     */
    @Test
    void createNewDiagramWithParty(){
        DiagramWindow.replayRecording("createNewDiagramWithParty1.txt",diagramWindow);
        assertEquals(1,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getParties().size());
    }

    /**
     * Adds a new subwindow with a party with the labal "a:A" then creates a copy
     * Change the label of the party to "iets" and check if the copy has still
     * the old label.
     */
    @Test
    void createNewDiagramEditInvalidLabel(){
        DiagramWindow.replayRecording("createNewDiagramEditInvalidLabel.txt",diagramWindow);
        assertEquals("a:A", diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getSelectedComponent().getLabel());

    }

}
