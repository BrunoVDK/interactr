package interactr.cs.kuleuven.be.UseCases;

import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.DiagramWindow;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveParty {

    private DiagramWindow diagramWindow = new DiagramWindow();

    @BeforeEach
    void setUp() throws Exception{
        diagramWindow.setEventHandler(new EventHandler(new DiagramController()));
        diagramWindow.setPaintBoard(new PaintBoard(diagramWindow, diagramWindow.getEventHandler().getDiagramController()));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DiagramWindow.replayRecording("addPartyLegalLabel.txt", diagramWindow);

    }

    @Test
    void movePartyToALegalPositionSequence() {
        DiagramWindow.replayRecording("moveTill100XY.txt", diagramWindow);
        assert(diagramWindow.getEventHandler().getDiagramController().getActiveView().getPartyAt(100,10) != null);
    }

    @Test
    void movePartyToALegalPostitionCommunication(){
        diagramWindow.getEventHandler().getDiagramController().nextView();
        DiagramWindow.replayRecording("moveTill100XY.txt", diagramWindow);
        assert(diagramWindow.getEventHandler().getDiagramController().getActiveView().getPartyAt(100,100) != null);
    }

    @Test
    void movePartyToIllegalPositionSequence(){
        DiagramWindow.replayRecording("addPartyLegalLabel100X.txt", diagramWindow);
        DiagramWindow.replayRecording("moveTill100XY.txt", diagramWindow);
        assertEquals("b:B", diagramWindow.getEventHandler().getDiagramController().getActiveView().getPartyAt(100,10).getLabel());
        assertEquals("a:A", diagramWindow.getEventHandler().getDiagramController().getActiveView().getPartyAt(50,10).getLabel());
    }

    @Test
    void movePartyToIllegalPositionCommunication(){
        diagramWindow.getEventHandler().getDiagramController().nextView();
        DiagramWindow.replayRecording("addPartyLegalLabel100X.txt", diagramWindow);
        DiagramWindow.replayRecording("moveTill100X.txt", diagramWindow);
        try{Thread.sleep(5000);}catch(Exception e){}
        assertEquals("b:B",diagramWindow.getEventHandler().getDiagramController().getActiveView().getPartyAt(100,10).getLabel());
    }

}
