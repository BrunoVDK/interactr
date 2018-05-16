package test.usecases;

import interactr.cs.kuleuven.be.ui.*;
import interactr.cs.kuleuven.be.ui.control.CommunicationView;
import interactr.cs.kuleuven.be.ui.control.DiagramView;
import interactr.cs.kuleuven.be.ui.control.SequenceView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class SwitchDiagramType {

    private Window window = new Window();

    @BeforeEach
    void setUp(){
        window.setEventHandler(new EventHandler(new Controller()));
        window.setPaintBoard(new PaintBoard(window, window.getEventHandler().getController()));
    }

    @Test
    void stepByStepTest() {
        // Precondition
        Window.replayRecording("steps/createNewDiagram.txt", window);
        assertNotNull(window.getEventHandler().getController().getActiveSubwindow());
        // Steps
        DiagramView activeView = window.getEventHandler().getController().getActiveSubwindow().getActiveView();
        Window.replayRecording("steps/pressTabKey.txt", window);
        DiagramView newView = window.getEventHandler().getController().getActiveSubwindow().getActiveView();
        assertNotEquals(activeView, newView);
    }

    @Test
    void switchSequenceToCommunication(){
        Window.replayRecording("switchSequenceToCommunication.txt", window);
        assertEquals( true , window.getEventHandler().getController().getActiveSubwindow().getActiveView() instanceof CommunicationView);
    }

    @Test
    void switchCommunicationToSequence(){
        Window.replayRecording("switchCommunicationToSequence.txt", window);
        assertEquals( true , window.getEventHandler().getController().getActiveSubwindow().getActiveView() instanceof SequenceView);

    }

}