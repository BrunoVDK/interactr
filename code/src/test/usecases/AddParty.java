package test.usecases;

import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.Controller;
import interactr.cs.kuleuven.be.ui.Window;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddParty {

    private Window window = new Window();

    @BeforeEach
    void setUp() {
        window.setEventHandler(new EventHandler(new Controller()));
        window.setPaintBoard(new PaintBoard(window, window.getEventHandler().getController()));
    }

    @Test
    void stepByStepTest() {
        Controller controller = window.getEventHandler().getController();
        // Precondition
        Window.replayRecording("steps/createNewDiagram.txt", window);
        assertNotNull(controller.getActiveSubwindow());
        // Create party
        Window.replayRecording("steps/createParty.txt", window);
        assertTrue(controller.getActiveSubwindow().getDiagram().getParties().size() > 0);
        // Assert that it is selected
        Party newParty = controller.getActiveSubwindow().getDiagram().getParties().get(0);
        assertEquals(controller.getActiveSubwindow().getSelectedComponent(), newParty);
        // Type label
        Window.replayRecording("steps/typePartyLabela:A.txt", window);
        assertEquals(controller.getActiveSubwindow().getSelectedLabel(), "a:A");
        assertEquals(newParty.getLabel(), "a:A");
        // Press enter
        Window.replayRecording("steps/pressEnter.txt", window);
        assertNull(controller.getActiveSubwindow().getSelectedComponent());
        assertEquals(newParty.getLabel(), "a:A");
    }

    @Test
    void addPartyValidLabelSequence() {
        Window.replayRecording("addPartySequence.txt", window);
        assertEquals(1, window.getEventHandler().getController().getActiveSubwindow().getDiagram().getParties().size());
    }

    @Test
    void addPartyValidLabelCommunication(){
        Window.replayRecording("addPartyCommunication.txt", window);
        assertEquals(1, window.getEventHandler().getController().getActiveSubwindow().getDiagram().getParties().size());
    }

    @Test
    void checkLabelSequence(){
        Window.replayRecording("addPartySequence.txt", window);
        assertEquals("a:A", window.getEventHandler().getController().getActiveSubwindow().getDiagram().getParties().getFirst().getLabel() );

    }

    @Test
    void checkLabelCommunication(){
        Window.replayRecording("addPartyCommunication.txt", window);
        assertEquals("a:A", window.getEventHandler().getController().getActiveSubwindow().getDiagram().getParties().getFirst().getLabel() );

    }

    @Test
    void addPartyInvalidLabelSequence(){
        Window.replayRecording("addPartyInvalidLabelSequence.txt", window);
        assertEquals(true, window.getEventHandler().getController().isEditing());
    }

    @Test
    void addPartyInvalidLabelCommunication(){
        Window.replayRecording("addPartyInvalidLabelCommunication.txt", window);
        assertEquals(true, window.getEventHandler().getController().isEditing());
    }

    @Test
    void addPartyIllegalPositionSequence(){
        Window.replayRecording("addPartyIllegalPositionSequence.txt", window);
        assertEquals(0, window.getEventHandler().getController().getActiveSubwindow().getDiagram().getParties().size());
    }

}