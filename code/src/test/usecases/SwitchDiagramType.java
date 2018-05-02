package usecases;

import interactr.cs.kuleuven.be.ui.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class SwitchDiagramType {

    private Window window = new Window();

    @BeforeEach
    void setUp(){
        window.setEventHandler(new EventHandler(new DiagramController()));
        window.setPaintBoard(new PaintBoard(window, window.getEventHandler().getDiagramController()));
    }

    @Test
    void stepByStepTest() {
        // Precondition
        Window.replayRecording("steps/createNewDiagram.txt", window);
        assertNotNull(window.getEventHandler().getDiagramController().getActiveSubwindow());
        // Steps
        DiagramView activeView = window.getEventHandler().getDiagramController().getActiveSubwindow().getActiveView();
        Window.replayRecording("steps/pressTabKey.txt", window);
        DiagramView newView = window.getEventHandler().getDiagramController().getActiveSubwindow().getActiveView();
        assertNotEquals(activeView, newView);
    }

    @Test
    void switchSequenceToCommunication(){
        Window.replayRecording("switchSequenceToCommunication.txt", window);
        assertEquals( true , window.getEventHandler().getDiagramController().getActiveSubwindow().getActiveView() instanceof CommunicationView );
    }

    @Test
    void switchCommunicationToSequence(){
        Window.replayRecording("switchCommunicationToSequence.txt", window);
        assertEquals( true , window.getEventHandler().getDiagramController().getActiveSubwindow().getActiveView() instanceof SequenceView );

    }

}