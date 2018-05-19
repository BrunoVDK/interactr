package usecases;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.exceptions.NoSuchComponentException;
import interactr.cs.kuleuven.be.ui.Controller;
import interactr.cs.kuleuven.be.ui.Window;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.control.DiagramWindow;
import interactr.cs.kuleuven.be.ui.control.diagram.DiagramView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveParty {

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
        // Move party at 100 x to 200 x
        Window.replayRecording("steps/moveParty100to200.txt", window);
        assertEquals(newParty, getActiveView().getParty(200,30));
    }

    @Test
    void movePartySequence(){
        Window.replayRecording("movePartySequence.txt", window);
        // Party added on  x = 6  y = 31 and moved to x = 135 y = 41
        assertNotEquals(null, getActiveView().getSelectableComponent(135,41));
    }

    @Test
    void movePartyWhileEditingSequence(){
        Window.replayRecording("movePartyWhileEditingSequence.txt", window);
        // Party added on  x = 6  y = 29 and moved to x = 38 y = 289
        assertThrows(NoSuchComponentException.class,()->{getActiveView().getSelectableComponent(38,289);});

    }

    @Test
    void movePartySequenceIllegalPosition(){
        Window.replayRecording("movePartyWhileEditingSequence.txt", window);
        // Party added on  x = 6  y = 29 and moved to x = 38 y = 289
        assertThrows(NoSuchComponentException.class, ()->{getActiveView().getSelectableComponent(38,289);});
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
