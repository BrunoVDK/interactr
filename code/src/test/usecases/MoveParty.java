package test.usecases;

import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.Controller;
import interactr.cs.kuleuven.be.ui.Window;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoveParty {

    private Window window = new Window();

    @BeforeEach
    void setUp(){
        window.setEventHandler(new EventHandler(new Controller()));
        window.setPaintBoard(new PaintBoard(window, window.getEventHandler().getController()));
    }

    @Test
    void stepByStep(){
        Controller controller = window.getEventHandler().getController();
        // Precondition
        Window.replayRecording("steps/createNewDiagram.txt", window);
        //At party at 100 x
        Window.replayRecording("steps/createPartyAt100.txt", window);
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

        Window.replayRecording("steps/moveParty100to200.txt", window);
        assertEquals(newParty, controller.getActiveSubwindow().getActiveView().getParty(200,30));
    }

    @Test
    void movePartySequence(){
        Window.replayRecording("movePartySequence.txt", window);
        // Party added on  x = 6  y = 31 and moved to x = 135 y = 41
        assertNotEquals(null, window.getEventHandler().getController().getActiveSubwindow().getActiveView().getSelectableComponent(135,41));
    }

    @Test
    void movePartyCommunication(){
        Window.replayRecording("movePartyCommunication.txt", window);
        // Party added on  x = 8  y = 30 and moved to x = 234 y = 40
        assertNotEquals(null, window.getEventHandler().getController().getActiveSubwindow().getActiveView().getSelectableComponent(234,40));
    }

    @Test
    void movePartyWhileEditingSequence(){
        Window.replayRecording("movePartyWhileEditingSequence.txt", window);
        // Party added on  x = 6  y = 29 and moved to x = 38 y = 289
        assertEquals(null, window.getEventHandler().getController().getActiveSubwindow().getActiveView().getSelectableComponent(38,289));

    }

    @Test
    void movePartySequenceIllegalPosition(){
        Window.replayRecording("movePartyWhileEditingSequence.txt", window);
        // Party added on  x = 6  y = 29 and moved to x = 38 y = 289
        assertEquals(null, window.getEventHandler().getController().getActiveSubwindow().getActiveView().getSelectableComponent(38,289));
    }

}
