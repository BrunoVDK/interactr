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

public class DeleteElement {

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
        Window.replayRecording("steps/pressBackSpace.txt", window);

        assertEquals(0, controller.getActiveSubwindow().getDiagram().getParties().size());

    }

    /**
     * Add 1 party to sequence and delete it afterwards
     */
    @Test
    void deletePartySequence(){
        Window.replayRecording("deletePartySequence.txt", window);
        assertEquals(0, window.getEventHandler().getController().getActiveSubwindow().getDiagram().getParties().size());
    }

    /**
     * Add a party switch to com view and delete it there
     */
    @Test
    void deletePartyCommunication(){
        Window.replayRecording("deletePartyCommunication.txt", window);
        assertEquals(0, window.getEventHandler().getController().getActiveSubwindow().getDiagram().getParties().size());
    }

    /**
     * Adds two parties and a message betweent them which gets deleted
     */
    @Test
    void deleteMessageSequence(){
        Window.replayRecording("deleteMessageSequence.txt", window);
        assertEquals(0, window.getEventHandler().getController().getActiveSubwindow().getDiagram().getMessages().size());
    }

    /**
     * Adds two parties and a message between them, after that the view is switched to delete the message
     */
    @Test
    void deleteMessageCommunication(){
        Window.replayRecording("deleteMessageCommunication.txt", window);
        assertEquals(0, window.getEventHandler().getController().getActiveSubwindow().getDiagram().getMessages().size());
    }

    /**
     * Adds 3 messages between 3 parties (011220) and deletes the middle party
     */
    @Test
    void deletePartyAvalanche(){
        Window.replayRecording("deletePartyMessageAvalanche.txt", window);
        assertEquals(0, window.getEventHandler().getController().getActiveSubwindow().getDiagram().getMessages().size());
        assertEquals(2, window.getEventHandler().getController().getActiveSubwindow().getDiagram().getParties().size());
    }

    /**
     * dds 3 messages between 3 parties (01 12 20) and deletes the second message
     */
    @Test
    void deleteMessageAvalanche(){
        Window.replayRecording("deleteMessageAvalanche.txt", window);
        assertEquals(2, window.getEventHandler().getController().getActiveSubwindow().getDiagram().getMessages().size());
        assertEquals(3, window.getEventHandler().getController().getActiveSubwindow().getDiagram().getParties().size());
    }

}
