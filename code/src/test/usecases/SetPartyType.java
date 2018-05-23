package usecases;

import interactr.cs.kuleuven.be.domain.ActorParty;
import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.ObjectParty;
import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.purecollections.PList;
import interactr.cs.kuleuven.be.ui.Controller;
import interactr.cs.kuleuven.be.ui.Window;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.control.DiagramWindow;
import interactr.cs.kuleuven.be.ui.control.diagram.DiagramView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SetPartyType {

    private Window window = new Window("Test Window");

    @BeforeEach
    void setUp(){
        window.setEventHandler(new EventHandler(new Controller()));
        window.setPaintBoard(new PaintBoard(window, window.getEventHandler().getController()));
    }

    @Test
    void stepByStepTest() {
        Controller controller = window.getEventHandler().getController();
        // Precondition
        Window.replayRecording("steps/createNewDiagram.txt", window);
        assertNotNull(controller.getActiveSubwindow());
        Window.replayRecording("steps/createParty.txt", window);
        assertTrue(getDiagram().getParties().size() > 0);
        // Switch type
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
        // Switch type
        Window.replayRecording("steps/switchPartyType.txt", window);
        Party switchedParty = getDiagram().getParties().get(0);
        assertNotEquals(switchedParty, newParty);
    }

    @Test
    void objectToActorToObjectThroughDialog() {
        Controller controller = window.getEventHandler().getController();
        // Precondition
        Window.replayRecording("steps/createNewDiagram.txt", window);
        assertNotNull(controller.getActiveSubwindow());
        Diagram diagram = ((DiagramWindow) window.getEventHandler().getController().getActiveSubwindow()).getDiagram();
        Window.replayRecording("steps/createParty.txt", window);
        assertTrue(diagram.getParties().size() > 0);
        Party oldParty = diagram.getParties().get(0);
        // create dialog
        Window.replayRecording("steps/createDialog.txt", window);
        // select radioButton for actorParty
        Window.replayRecording("steps/pressTabKey.txt", window);
        Window.replayRecording("steps/pressTabKey.txt", window);
        Window.replayRecording("steps/pressSpace.txt", window);
        Party newParty = diagram.getParties().get(0);
        assertNotEquals(oldParty, newParty);
        assertTrue(newParty instanceof ActorParty);
        Window.replayRecording("steps/pressTabKey.txt", window);
        Window.replayRecording("steps/pressSpace.txt", window);
        Party newestParty = diagram.getParties().get(0);
        assertNotEquals(newParty, newestParty);
        assertTrue(newestParty instanceof ObjectParty);
    }

    @Test
    void switchObjectToActorSequence(){
        Window.replayRecording("switchObjectToActorSequence.txt", window);
        assertEquals(true, getDiagram().getParties().getFirst() instanceof ActorParty);
    }

    @Test
    void switchObjectToActorCommunication(){
        Window.replayRecording("switchObjectToActorCommunication.txt", window);
        assertEquals(true, getDiagram().getParties().getFirst() instanceof ActorParty);
    }

    @Test
    void switchWhileEditing(){
        Window.replayRecording("switchWhileEditing.txt", window);
        assertEquals(true, getDiagram().getParties().getFirst() instanceof ObjectParty);
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
