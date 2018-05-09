package usecases;

import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.Controller;
import interactr.cs.kuleuven.be.ui.Window;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditLabel {
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

        Window.replayRecording("steps/selectPartyAt100.txt", window);
        Window.replayRecording("steps/selectPartyAt100.txt", window);

        Window.replayRecording("steps/typePartyLabelb:B.txt", window);
        Window.replayRecording("steps/pressEnter.txt", window);
        Window.replayRecording("steps/selectPartyAt100.txt", window);
        assertEquals(controller.getActiveSubwindow().getSelectedComponent().getLabel(), "b:B");

    }

    @Test
    /**
     * The partys label is changedfrom a:A to b:B
     */
    void editLabelParty(){
        Window.replayRecording("editLabelParty.txt", window);
        assertEquals("b:B", window.getEventHandler().getController().getActiveSubwindow().getSelectedComponent().getLabel());
    }

    @Test
    /**
     * The partys label is changedfrom a:A to b
     */
    void editLabelPartyIllegalLabel(){
        Window.replayRecording("editLabelPartyIllegalLabel.txt", window);
        assertEquals("a:A", window.getEventHandler().getController().getActiveSubwindow().getSelectedComponent().getLabel());
    }

    /**
     * Change a invocation message label from "c" to "d"
     */
    @Test
    void editLabelInvocationMessage(){
        Window.replayRecording("editLabelInvocationMessage.txt", window);
        assertEquals("d", window.getEventHandler().getController().getActiveSubwindow().getDiagram().getMessages().getFirst().getLabel());
    }



    /**
     * Change a result message label to "d"
     */
    @Test
    void editLabelResultMessage(){
        Window.replayRecording("editLabelResultMessage01.txt", window);
        assertEquals("d", window.getEventHandler().getController().getActiveSubwindow().getDiagram().getMessages().get(1).getLabel());
    }


}
