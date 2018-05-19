package usecases;

import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.Controller;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.Window;
import interactr.cs.kuleuven.be.ui.control.DiagramWindow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddMessage {

    private Window window = new Window("Test Window");

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
        // Add party at 100 x
        Window.replayRecording("steps/createPartyAt100.txt", window);
        assertTrue(((DiagramWindow)controller.getActiveSubwindow()).getDiagram().getParties().size() > 0);
        // Assert that it is selected
        Party newParty = ((DiagramWindow)controller.getActiveSubwindow()).getDiagram().getParties().get(0);
        assertTrue(((DiagramWindow)controller.getActiveSubwindow()).getActiveView().isEditing());
        assertEquals(((DiagramWindow)controller.getActiveSubwindow()).getActiveView().getSelectedComponent(), newParty);
        // Type label
        Window.replayRecording("steps/typePartyLabelaA.txt", window);
        assertEquals(((DiagramWindow)controller.getActiveSubwindow()).getActiveView().getSelectedLabel(), "a:A");
        assertEquals(newParty.getLabel(), "a:A");
        // Press enter
        Window.replayRecording("steps/pressEnter.txt", window);
        assertNull(((DiagramWindow)controller.getActiveSubwindow()).getActiveView().getSelectedComponent());
        assertEquals(newParty.getLabel(), "a:A");
        // Add party at 200 x, insert a label and press enter
        Window.replayRecording("steps/createPartyAt200.txt", window);
        Window.replayRecording("steps/typePartyLabelaA.txt", window);
        Window.replayRecording("steps/pressEnter.txt", window);
        // Adds a message between the parties at 100 and 200
        Window.replayRecording("steps/createMessageBetween100and200.txt", window);
        Window.replayRecording("steps/pressEnter.txt", window);
        assertEquals(2, ((DiagramWindow)controller.getActiveSubwindow()).getDiagram().getMessages().size());
    }

    @Test
    void addMessageBetweenTwoParties(){
        Window.replayRecording("addMessageBetweenTwoParties.txt", window);
        assertEquals( 2, ((DiagramWindow)window.getEventHandler().getController().getActiveSubwindow()).getDiagram().getMessages().size());
    }

    @Test
    void moveWithMessagesSequence(){
        Window.replayRecording("moveWithMessagesSequence.txt", window);
        assertEquals( 2, ((DiagramWindow)window.getEventHandler().getController().getActiveSubwindow()).getDiagram().getMessages().size());
    }
    @Test
    void addIllegaleMessageStack0110(){
        Window.replayRecording("addIllegaleMessageStack0110.txt", window);
        assertEquals( 2, ((DiagramWindow)window.getEventHandler().getController().getActiveSubwindow()).getDiagram().getMessages().size());
    }

}
