package test.usecases;

import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.DiagramWindow;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddParty {

    private DiagramWindow diagramWindow = new DiagramWindow();

    @BeforeEach
    void setUp() {
        diagramWindow.setEventHandler(new EventHandler(new DiagramController()));
        diagramWindow.setPaintBoard(new PaintBoard(diagramWindow, diagramWindow.getEventHandler().getDiagramController()));
    }

    @Test
    void stepByStepTest() {
        DiagramController controller = diagramWindow.getEventHandler().getDiagramController();
        // Precondition
        DiagramWindow.replayRecording("steps/createNewDiagram.txt",diagramWindow);
        assertNotNull(controller.getActiveSubwindow());
        // Create party
        DiagramWindow.replayRecording("steps/createParty.txt",diagramWindow);
        assertTrue(controller.getActiveSubwindow().getDiagram().getParties().size() > 0);
        // Assert that it is selected
        Party newParty = controller.getActiveSubwindow().getDiagram().getParties().get(0);
        assertEquals(controller.getActiveSubwindow().getSelectedComponent(), newParty);
        // Type label
        DiagramWindow.replayRecording("steps/typePartyLabela:A.txt",diagramWindow);
        assertEquals(controller.getActiveSubwindow().getSelectedLabel(), "a:A");
        assertEquals(newParty.getLabel(), "a:A");
        // Press enter
        DiagramWindow.replayRecording("steps/pressEnter.txt",diagramWindow);
        assertNull(controller.getActiveSubwindow().getSelectedComponent());
        assertEquals(newParty.getLabel(), "a:A");
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