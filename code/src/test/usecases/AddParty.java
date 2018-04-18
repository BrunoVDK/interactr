package usecases;

import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.DiagramWindow;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AddParty {

    private DiagramWindow diagramWindow = new DiagramWindow();

    @BeforeEach
    void setUp() {
        diagramWindow.setEventHandler(new EventHandler(new DiagramController()));
        diagramWindow.setPaintBoard(new PaintBoard(diagramWindow, diagramWindow.getEventHandler().getDiagramController()));
    }

    @Test
    void stepByStepTest() {
        // Precondition
        DiagramWindow.replayRecording("steps/createNewInteraction.txt",diagramWindow);
        assertNotNull(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow());
        // ADD REST OF TESTS HERE
    }

    @Test
    void addPartyValidLabelSequence() {
        DiagramWindow.replayRecording("addPartySequence.txt",diagramWindow);
        assertEquals(1, diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getParties().size());
    }

    @Test
    void addPartyValidLabelCommunication(){
        DiagramWindow.replayRecording("addPartyCommunication.txt",diagramWindow);
        assertEquals(1, diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getParties().size());
    }

    @Test
    void checkLabelSequence(){
        DiagramWindow.replayRecording("addPartySequence.txt",diagramWindow);
        assertEquals("a:A",diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getParties().getFirst().getLabel() );

    }

    @Test
    void checkLabelCommunication(){
        DiagramWindow.replayRecording("addPartyCommunication.txt",diagramWindow);
        assertEquals("a:A",diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getParties().getFirst().getLabel() );

    }

    @Test
    void addPartyInvalidLabelSequence(){
        DiagramWindow.replayRecording("addPartyInvalidLabelSequence.txt",diagramWindow);
        assertEquals(true, diagramWindow.getEventHandler().getDiagramController().isEditing());
    }

    @Test
    void addPartyInvalidLabelCommunication(){
        DiagramWindow.replayRecording("addPartyInvalidLabelCommunication.txt",diagramWindow);
        assertEquals(true, diagramWindow.getEventHandler().getDiagramController().isEditing());
    }

    @Test
    void addPartyIllegalPositionSequence(){
        DiagramWindow.replayRecording("addPartyIllegalPositionSequence.txt",diagramWindow);
        assertEquals(0, diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getParties().size());
    }

}