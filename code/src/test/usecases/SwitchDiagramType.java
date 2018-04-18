package usecases;

import interactr.cs.kuleuven.be.ui.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class SwitchDiagramType {

    private DiagramWindow diagramWindow = new DiagramWindow();

    @BeforeEach
    void setUp(){
        diagramWindow.setEventHandler(new EventHandler(new DiagramController()));
        diagramWindow.setPaintBoard(new PaintBoard(diagramWindow, diagramWindow.getEventHandler().getDiagramController()));
    }

    @Test
    void stepByStepTest() {
        // Precondition
        DiagramWindow.replayRecording("steps/createNewDiagram.txt",diagramWindow);
        assertNotNull(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow());
        // Steps
        DiagramView activeView = diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getActiveView();
        DiagramWindow.replayRecording("steps/pressTabKey.txt",diagramWindow);
        DiagramView newView = diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getActiveView();
        assertNotEquals(activeView, newView);
    }

    @Test
    void switchSequenceToCommunication(){
        DiagramWindow.replayRecording("switchSequenceToCommunication.txt",diagramWindow);
        assertEquals( true ,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getActiveView() instanceof CommunicationView );
    }

    @Test
    void switchCommunicationToSequence(){
        DiagramWindow.replayRecording("switchCommunicationToSequence.txt",diagramWindow);
        assertEquals( true ,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getActiveView() instanceof SequenceView );

    }

}