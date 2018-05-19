package usecases;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.Controller;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.Window;
import interactr.cs.kuleuven.be.ui.control.DiagramWindow;
import interactr.cs.kuleuven.be.ui.control.diagram.DiagramView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddParty {

    private Window window = new Window("Test Window");

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
        assertTrue(getDiagram().getParties().size() > 0);
        // Assert that it is selected
        Party newParty = getDiagram().getParties().get(0);
        assertEquals(getActiveView().getSelectedComponent(), newParty);
        // Type label
        Window.replayRecording("steps/typePartyLabelaA.txt", window);
        assertEquals(getActiveView().getSelectedLabel(), "a:A");
        assertEquals(newParty.getLabel(), "a:A");
        // Press enter
        Window.replayRecording("steps/pressEnter.txt", window);
        assertNull(getActiveView().getSelectedComponent());
        assertEquals(newParty.getLabel(), "a:A");
    }

    @Test
    void addPartyValidLabelSequence() {
        Window.replayRecording("addPartySequence.txt", window);
        assertEquals(1, getDiagram().getParties().size());
    }

    @Test
    void addPartyValidLabelCommunication(){
        Window.replayRecording("addPartyCommunication.txt", window);
        assertEquals(1, getDiagram().getParties().size());
    }

    @Test
    void checkLabelSequence(){
        Window.replayRecording("addPartySequence.txt", window);
        assertEquals("a:A", getDiagram().getParties().getFirst().getLabel() );

    }

    @Test
    void checkLabelCommunication(){
        Window.replayRecording("addPartyCommunication.txt", window);
        assertEquals("a:A", getDiagram().getParties().getFirst().getLabel() );

    }

    @Test
    void addPartyInvalidLabelSequence(){
        Window.replayRecording("addPartyInvalidLabelSequence.txt", window);
        assertEquals(true, getActiveView().isEditing());
    }

    @Test
    void addPartyInvalidLabelCommunication(){
        Window.replayRecording("addPartyInvalidLabelCommunication.txt", window);
        assertEquals(true, getActiveView().isEditing());
    }

    @Test
    void addPartyIllegalPositionSequence(){
        Window.replayRecording("addPartyIllegalPositionSequence.txt", window);
        assertEquals(0, getDiagram().getParties().size());
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