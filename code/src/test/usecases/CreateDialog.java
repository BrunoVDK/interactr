package usecases;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.Controller;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.Window;
import interactr.cs.kuleuven.be.ui.control.DiagramWindow;
import interactr.cs.kuleuven.be.ui.control.diagram.DiagramView;
import interactr.cs.kuleuven.be.ui.control.dialog.DialogDiagram;
import interactr.cs.kuleuven.be.ui.control.dialog.DialogInvocationMessage;
import interactr.cs.kuleuven.be.ui.control.dialog.DialogParty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A class of tests for the creation of dialogs.
 *
 * @author Team 25
 * @version 1.0
 */
class CreateDialog {

    private Window window = new Window("Test Window");

    @BeforeEach
    void setUp(){
        window.setEventHandler(new EventHandler(new Controller()));
        window.setPaintBoard(new PaintBoard(window, window.getEventHandler().getController()));
    }

    @Test
    void stepByStepParty() {
        Controller controller = window.getEventHandler().getController();
        // Precondition
        Window.replayRecording("steps/createNewDiagram.txt", window);
        // Add party at 100 x
        Window.replayRecording("steps/createPartyAt100.txt", window);
        assertTrue(getDiagram().getParties().size() > 0);
        Party party = getDiagram().getParties().get(0);
        // Create dialog
        Window.replayRecording("steps/createDialog.txt", window);
        assertTrue(controller.getActiveSubwindow() instanceof DialogParty);
    }

    @Test
    void stepByStepDiagram() {
        Controller controller = window.getEventHandler().getController();
        // Precondition
        Window.replayRecording("steps/createNewDiagram.txt", window);
        // Create dialog
        Window.replayRecording("steps/createDialog.txt", window);
        assertTrue(controller.getActiveSubwindow() instanceof DialogDiagram);
    }

    @Test
    void stepByStepResultMessage() {
        Controller controller = window.getEventHandler().getController();
        // Precondition
        spawnTwoPartiesAndMessage();
        //  dialog
        Window.replayRecording("steps/createDialog.txt", window);
        assertTrue(controller.getActiveSubwindow() instanceof DialogInvocationMessage);
    }

    private void spawnTwoPartiesAndMessage() {
        Window.replayRecording("steps/createNewDiagram.txt", window);
        Window.replayRecording("steps/createPartyAt100.txt", window);
        Window.replayRecording("steps/typePartyLabelaA.txt", window);
        Window.replayRecording("steps/pressEnter.txt", window);
        Window.replayRecording("steps/createPartyAt200.txt", window);
        Window.replayRecording("steps/typePartyLabelbB.txt", window);
        Window.replayRecording("steps/pressEnter.txt", window);
        Window.replayRecording("steps/createMessageBetween100and200.txt", window);
        Window.replayRecording("steps/typeb.txt", window);
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