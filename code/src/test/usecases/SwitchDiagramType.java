package usecases;

import interactr.cs.kuleuven.be.ui.Controller;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.Window;
import interactr.cs.kuleuven.be.ui.control.DiagramWindow;
import interactr.cs.kuleuven.be.ui.control.diagram.CommunicationView;
import interactr.cs.kuleuven.be.ui.control.diagram.DiagramView;
import interactr.cs.kuleuven.be.ui.control.diagram.SequenceView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class SwitchDiagramType {

    private Window window = new Window("Test Window");

    @BeforeEach
    void setUp(){
        window.setEventHandler(new EventHandler(new Controller()));
        window.setPaintBoard(new PaintBoard(window, window.getEventHandler().getController()));
    }

    @Test
    void stepByStepTest() {
        // Precondition (creates new diagram)
        Window.replayRecording("steps/createNewDiagram.txt", window);
        assertNotNull(window.getEventHandler().getController().getActiveSubwindow());
        // Get the currently active view
        DiagramView activeView = getActiveView();
        // Press tab key
        Window.replayRecording("steps/pressTabKey.txt", window);
        // Get the active view
        DiagramView newView = getActiveView();
        // Compare the newly active view with the previously active view
        assertNotEquals(activeView, newView);
    }

    @Test
    void switchSequenceToCommunicationThroughDialogMouse() {
        // Precondition (creates new diagram)
        Window.replayRecording("steps/createNewDiagram.txt", window);
        DiagramWindow sw = (DiagramWindow) window.getEventHandler().getController().getActiveSubwindow();
        assertNotNull(sw);
        // Get the currently active view
        DiagramView activeView = sw.getActiveView();
        // Create Dialog
        Window.replayRecording("steps/createDialog.txt", window);
        // Click radioButton
        Window.replayRecording("steps/dialogAt0x0SecondButton.txt", window);
        // Get the active view
        DiagramView newView = sw.getActiveView();
        // Compare the newly active view with the previously active view
        assertNotEquals(activeView, newView);
    }


    @Test
    void switchSequenceToCommunicationThroughDialogKeyboard() {
        // Precondition (creates new diagram)
        Window.replayRecording("steps/createNewDiagram.txt", window);
        DiagramWindow sw = (DiagramWindow) window.getEventHandler().getController().getActiveSubwindow();
        assertNotNull(sw);
        // Get the currently active view
        DiagramView activeView = sw.getActiveView();
        // Create Dialog
        Window.replayRecording("steps/createDialog.txt", window);
        // Select radioButton for communicationView
        Window.replayRecording("steps/pressTabKey.txt", window);
        Window.replayRecording("steps/pressSpace.txt", window);
        // Get the active view
        DiagramView newView = sw.getActiveView();
        // Compare the newly active view with the previously active view
        assertNotEquals(activeView, newView);
    }

    @Test
    void switchSequenceToCommunication(){
        Window.replayRecording("switchSequenceToCommunication.txt", window);
        assertEquals( true , getActiveView() instanceof CommunicationView);
    }

    @Test
    void switchCommunicationToSequence(){
        Window.replayRecording("switchCommunicationToSequence.txt", window);
        assertEquals( true , getActiveView() instanceof SequenceView);

    }

    // Returns the currently active view for the scene
    //  Convenience method
    private DiagramView getActiveView() {
        return ((DiagramWindow)window.getEventHandler().getController().getActiveSubwindow()).getActiveView();
    }

}