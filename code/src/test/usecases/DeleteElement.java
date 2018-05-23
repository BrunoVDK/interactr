package usecases;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.Controller;
import interactr.cs.kuleuven.be.ui.Window;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.control.DiagramWindow;
import interactr.cs.kuleuven.be.ui.control.diagram.DiagramView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteElement {

    private Window window = new Window("Test Window");

    @BeforeEach
    void setUp(){
        window.setEventHandler(new EventHandler(new Controller()));
        window.setPaintBoard(new PaintBoard(window, window.getEventHandler().getController()));
    }

    @Test
    void stepByStep(){
        // Precondition
        Window.replayRecording("steps/createNewDiagram.txt", window);
        // Add party at 100 x
        Window.replayRecording("steps/createPartyAt100.txt", window);
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
        // Select the party at 100 x
        Window.replayRecording("steps/selectPartyAt100.txt", window);
        // Delete the party at 100 x
        Window.replayRecording("steps/pressDelete.txt", window);
        assertEquals(0, getDiagram().getParties().size());
    }

    /**
     * Add 1 party to sequence and delete it afterwards
     */
    @Test
    void deletePartySequence(){
        Window.replayRecording("deletePartySequence.txt", window);
        assertEquals(0, getDiagram().getParties().size());
    }

    /**
     * Add a party switch to com view and delete it there
     */
    @Test
    void deletePartyCommunication(){
        Window.replayRecording("deletePartyCommunication.txt", window);
        assertEquals(0, getDiagram().getParties().size());
    }

    /**
     * Adds two parties and a message betweent them which gets deleted
     */
    @Test
    void deleteMessageSequence(){
        Window.replayRecording("deleteMessageSequence.txt", window);
        assertEquals(0, getDiagram().getMessages().size());
    }

    /**
     * dds 3 messages between 3 parties (01 12 20) and deletes the second message
     */
    @Test
    void deleteMessageAvalanche(){
        Window.replayRecording("deleteMessageAvalanche.txt", window);
        assertEquals(2, getDiagram().getMessages().size());
        assertEquals(3, getDiagram().getParties().size());
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
