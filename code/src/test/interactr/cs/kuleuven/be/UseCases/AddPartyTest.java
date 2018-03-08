package interactr.cs.kuleuven.be.UseCases;

import interactr.cs.kuleuven.be.ui.*;
import interactr.cs.kuleuven.be.ui.exceptions.InvalidAddPartyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AddPartyTest {

    private DiagramWindow diagramWindow = new DiagramWindow();


    @BeforeEach
    void setUp(){
        diagramWindow.setEventHandler(new EventHandler(new DiagramController()));
        diagramWindow.setPaintBoard(new PaintBoard(diagramWindow, diagramWindow.getEventHandler().getDiagramController()));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    void addPartyToEmptyAreaSequence(){
        DiagramWindow.replayRecording("doubleClick.txt", diagramWindow);
        assertEquals(1,diagramWindow.getEventHandler().getDiagramController().getDiagram().getParties().size());
    }


    @Test
    void addPartyToEmptyAreaCommunication(){
        diagramWindow.getEventHandler().getDiagramController().nextView();
        DiagramWindow.replayRecording("doubleClick.txt" , diagramWindow);
        assertEquals(1,diagramWindow.getEventHandler().getDiagramController().getDiagram().getParties().size());

    }

    @Test
    void addPartyWithLegalLabel(){
        DiagramWindow.replayRecording("addPartyLegalLabel.txt",diagramWindow);
        assertEquals(diagramWindow.getEventHandler().getDiagramController().getSelectionManager().getActiveComponent(),null);
    }

    @Test
    void addPartyWithIllegalLabel(){
        DiagramWindow.replayRecording("addPartyIllegalLabel.txt",diagramWindow);
        if(diagramWindow.getEventHandler().getDiagramController().getSelectionManager().getActiveComponent() != null) assert true;
        else assert false;
    }

}
