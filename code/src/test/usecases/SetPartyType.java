package test.usecases;

import interactr.cs.kuleuven.be.domain.ActorParty;
import interactr.cs.kuleuven.be.domain.ObjectParty;
import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.DiagramWindow;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SetPartyType {

    private DiagramWindow diagramWindow = new DiagramWindow();

    @BeforeEach
    void setUp(){
        diagramWindow.setEventHandler(new EventHandler(new DiagramController()));
        diagramWindow.setPaintBoard(new PaintBoard(diagramWindow, diagramWindow.getEventHandler().getDiagramController()));
    }

    @Test
    void stepByStepTest() {
        DiagramController controller = diagramWindow.getEventHandler().getDiagramController();
        // Precondition
        DiagramWindow.replayRecording("steps/createNewDiagram.txt",diagramWindow);
        assertNotNull(controller.getActiveSubwindow());
        DiagramWindow.replayRecording("steps/createParty.txt",diagramWindow);
        assertTrue(controller.getActiveSubwindow().getDiagram().getParties().size() > 0);
        // Switch type
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
        // Switch type
        DiagramWindow.replayRecording("steps/switchPartyType.txt",diagramWindow);
        Party switchedParty = controller.getActiveSubwindow().getDiagram().getParties().get(0);
        assertNotEquals(switchedParty, newParty);
    }

    @Test
    void switchObjectToActorSequence(){
        DiagramWindow.replayRecording("switchObjectToActorSequence.txt",diagramWindow);
        assertEquals(true,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getParties().getFirst() instanceof ActorParty);
    }

    @Test
    void switchActorToObjectSequence(){
        DiagramWindow.replayRecording("switchActorToObjectSequence.txt",diagramWindow);
        assertEquals(true,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getParties().getFirst() instanceof ObjectParty);
    }
    @Test
    void switchObjectToActorCommunication(){
        DiagramWindow.replayRecording("switchObjectToActorCommunication.txt",diagramWindow);
        assertEquals(true,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getParties().getFirst() instanceof ActorParty);
    }

    @Test
    void switchActorToObjectCommunication(){
        DiagramWindow.replayRecording("switchActorToObjectCommunication.txt",diagramWindow);
        assertEquals(true,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getParties().getFirst() instanceof ObjectParty);
    }

    @Test
    void switchWhileEditing(){
        DiagramWindow.replayRecording("switchWhileEditing.txt",diagramWindow);
        assertEquals(true,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getParties().getFirst() instanceof ObjectParty);
    }
}
