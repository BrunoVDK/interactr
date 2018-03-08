package interactr.cs.kuleuven.be.UseCases;

import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.*;
import interactr.cs.kuleuven.be.ui.exceptions.InvalidAddPartyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AddPartyTest {

    private DiagramController diagramController;

    private DiagramWindow diagramWindow = new DiagramWindow();

    @BeforeEach
    void setUp(){
        diagramController = new DiagramController();
        diagramWindow.setEventHandler(new EventHandler(new DiagramController()));
        diagramWindow.setPaintBoard(new PaintBoard(diagramWindow, diagramWindow.getEventHandler().getDiagramController()));

    }

    @Test
    void addPartyToEmptyAreaSequence(){
        DiagramWindow.replayRecording("doubleClick.txt", diagramWindow);
        assertEquals(1,diagramController.getDiagram().getParties().size());
    }

    @Test
    void addPartyToOccupiedAreaSequence(){
        try{
            DiagramWindow.replayRecording("doubleClickTwice.txt" , diagramWindow);
            assert(false);
        }catch (InvalidAddPartyException e){
            assert true;
        }

    }


    @Test
    void addPartyToEmptyAreaCommunication(){
        diagramController.nextView();
        DiagramWindow.replayRecording("doubleClick.txt" , diagramWindow);
        assertEquals(1,diagramController.getDiagram().getParties().size());

    }

    @Test
    void addPartyToOccupiedAreaCommunication(){
        diagramController.nextView();
        try{
            DiagramWindow.replayRecording("doubleClickTwice.txt" , diagramWindow);
            assert(false);
        }catch (InvalidAddPartyException e){
            assert true;
        }
    }
}
