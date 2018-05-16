package test.usecases;

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

public class AddMessage {

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

        //At party at 200 x, insert a label and press enter
        Window.replayRecording("steps/createPartyAt200.txt", window);
        Window.replayRecording("steps/typePartyLabela:A.txt", window);
        Window.replayRecording("steps/pressEnter.txt", window);


        //adds a message between the party's at 100 and 200
        Window.replayRecording("steps/createMessageBetween100and200.txt", window);
        Window.replayRecording("steps/pressEnter.txt", window);

        assertEquals(2, controller.getActiveSubwindow().getDiagram().getMessages().size());



    }

    @Test
    void addMessageBetweenTwoParties(){
        Window.replayRecording("addMessageBetweenTwoParties.txt", window);
        assertEquals( 2, window.getEventHandler().getController().getActiveSubwindow().getDiagram().getMessages().size());
    }

    @Test
    void moveWithMessagesSequence(){
        Window.replayRecording("moveWithMessagesSequence.txt", window);
        assertEquals( 2, window.getEventHandler().getController().getActiveSubwindow().getDiagram().getMessages().size());
    }
    @Test
    void addIllegaleMessageStack0110(){
        Window.replayRecording("addIllegaleMessageStack0110.txt", window);
        assertEquals( 2, window.getEventHandler().getController().getActiveSubwindow().getDiagram().getMessages().size());
    }

    @Test
    void addLegalMessageStack011221(){
        Window.replayRecording("addLegalMessageStack011221.txt", window);
        assertEquals( 6, window.getEventHandler().getController().getActiveSubwindow().getDiagram().getMessages().size());
    }

}
