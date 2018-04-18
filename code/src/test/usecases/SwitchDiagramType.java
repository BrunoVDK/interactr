package test.usecases;

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
