package interactr.cs.kuleuven.be.UseCases;

import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.*;
import interactr.cs.kuleuven.be.ui.exceptions.InvalidAddPartyException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AddPartyTest {

    private EventHandler eventHandler;

    private DiagramController diagramController;

    private DiagramWindow diagramWindow = new DiagramWindow();

    @Before
    void setUp(){
        diagramController = new DiagramController();
        eventHandler = new EventHandler(diagramController);
        diagramWindow.setEventHandler(new EventHandler(new DiagramController()));
        diagramWindow.setPaintBoard(new PaintBoard(diagramWindow, diagramWindow.getEventHandler().getDiagramController()));

    }

    @Test
    void addPartyToEmptyAreaSequence(){
        DiagramWindow.("doubleClick.txt" , diagramWindow);
        assertEquals(1,diagramController.getDiagram().getParties().size());
    }

    @Test
    void addPartyToOccupiedAreaSequence(){
        DiagramWindow.replayRecording("doubleClick.txt" , diagramWindow);
        Party firstAdded = diagramController.getDiagram().getParties().getFirst();
        try{
            DiagramWindow.replayRecording("doubleClick.txt" , diagramWindow);
            assert(false);
        }catch (InvalidAddPartyException e){
            assertEquals(e.getParty(),firstAdded);
        }
    }

    @Test
    void addPartyToForbiddenAreaSequence(){
        try {
            DiagramWindow.replayRecording("doubleClickMiddle.txt", diagramWindow);
        }catch (InvalidAddPartyException e){
            assertEquals(e.getParty(),null);
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
        DiagramWindow.replayRecording("doubleClick.txt" , diagramWindow);
        Party firstAdded = diagramController.getDiagram().getParties().getFirst();
        try{
            DiagramWindow.replayRecording("doubleClick.txt" , diagramWindow);
            assert(false);
        }catch (InvalidAddPartyException e){
            assertEquals(e.getParty(),firstAdded);
        }
    }
}
