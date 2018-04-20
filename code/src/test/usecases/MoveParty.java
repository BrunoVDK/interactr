package usecases;

import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.DiagramWindow;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoveParty {

    private DiagramWindow diagramWindow = new DiagramWindow();

    @BeforeEach
    void setUp(){
        diagramWindow.setEventHandler(new EventHandler(new DiagramController()));
        diagramWindow.setPaintBoard(new PaintBoard(diagramWindow, diagramWindow.getEventHandler().getDiagramController()));
    }

    @Test
    void stepByStep(){
        DiagramController controller = diagramWindow.getEventHandler().getDiagramController();
        // Precondition
        DiagramWindow.replayRecording("steps/createNewDiagram.txt",diagramWindow);
        //At party at 100 x
        DiagramWindow.replayRecording("steps/createPartyAt100.txt",diagramWindow);
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

        DiagramWindow.replayRecording("steps/moveParty100to200.txt",diagramWindow);
        assertEquals(newParty, controller.getActiveSubwindow().getActiveView().getParty(200,30));
    }

    @Test
    void movePartySequence(){
        DiagramWindow.replayRecording("movePartySequence.txt",diagramWindow);
        // Party added on  x = 6  y = 31 and moved to x = 135 y = 41
        assertNotEquals(null,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getActiveView().getSelectableComponent(135,41));
    }

    @Test
    void movePartyCommunication(){
        DiagramWindow.replayRecording("movePartyCommunication.txt",diagramWindow);
        // Party added on  x = 8  y = 30 and moved to x = 234 y = 40
        assertNotEquals(null,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getActiveView().getSelectableComponent(234,40));
    }

    @Test
    void movePartyWhileEditingSequence(){
        DiagramWindow.replayRecording("movePartyWhileEditingSequence.txt",diagramWindow);
        // Party added on  x = 6  y = 29 and moved to x = 38 y = 289
        assertEquals(null,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getActiveView().getSelectableComponent(38,289));

    }

    @Test
    void movePartySequenceIllegalPosition(){
        DiagramWindow.replayRecording("movePartyWhileEditingSequence.txt",diagramWindow);
        // Party added on  x = 6  y = 29 and moved to x = 38 y = 289
        assertEquals(null,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getActiveView().getSelectableComponent(38,289));
    }

}
