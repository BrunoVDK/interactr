package test.usecases;

import interactr.cs.kuleuven.be.domain.ActorParty;
import interactr.cs.kuleuven.be.domain.ObjectParty;
import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.Controller;
import interactr.cs.kuleuven.be.ui.Window;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SetPartyType {

    private Window window = new Window();

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
        assertTrue(controller.getActiveSubwindow().getDiagram().getParties().size() > 0);
        // Switch type
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
        // Switch type
        Window.replayRecording("steps/switchPartyType.txt", window);
        Party switchedParty = controller.getActiveSubwindow().getDiagram().getParties().get(0);
        assertNotEquals(switchedParty, newParty);
    }

    @Test
    void switchObjectToActorSequence(){
        Window.replayRecording("switchObjectToActorSequence.txt", window);
        assertEquals(true, window.getEventHandler().getController().getActiveSubwindow().getDiagram().getParties().getFirst() instanceof ActorParty);
    }

    @Test
    void switchActorToObjectSequence(){
        Window.replayRecording("switchActorToObjectSequence.txt", window);
        assertEquals(true, window.getEventHandler().getController().getActiveSubwindow().getDiagram().getParties().getFirst() instanceof ObjectParty);
    }
    @Test
    void switchObjectToActorCommunication(){
        Window.replayRecording("switchObjectToActorCommunication.txt", window);
        assertEquals(true, window.getEventHandler().getController().getActiveSubwindow().getDiagram().getParties().getFirst() instanceof ActorParty);
    }

    @Test
    void switchActorToObjectCommunication(){
        Window.replayRecording("switchActorToObjectCommunication.txt", window);
        assertEquals(true, window.getEventHandler().getController().getActiveSubwindow().getDiagram().getParties().getFirst() instanceof ObjectParty);
    }

    @Test
    void switchWhileEditing(){
        Window.replayRecording("switchWhileEditing.txt", window);
        assertEquals(true, window.getEventHandler().getController().getActiveSubwindow().getDiagram().getParties().getFirst() instanceof ObjectParty);
    }
}
