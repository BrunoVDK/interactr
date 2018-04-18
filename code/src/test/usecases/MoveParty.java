package usecases;

import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.DiagramWindow;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoveParty {

    private DiagramWindow diagramWindow = new DiagramWindow();

    @BeforeEach
    void setUp(){
        diagramWindow.setEventHandler(new EventHandler(new DiagramController()));
        diagramWindow.setPaintBoard(new PaintBoard(diagramWindow, diagramWindow.getEventHandler().getDiagramController()));
    }

    @Test
    void movePartySequence(){
        DiagramWindow.replayRecording("movePartySequence.txt",diagramWindow);
        // Party added on  x = 6  y = 31 and moved to x = 135 y = 41
        assertNotEquals(null,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getActiveView().getSelectableComponent(135,41));
    }

    @Test
    void movePartyCommunication(){
        DiagramWindow.replayRecording("movePartyCommunication.txt",diagramWindow);
        // Party added on  x = 8  y = 30 and moved to x = 234 y = 40
        assertNotEquals(null,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getActiveView().getSelectableComponent(234,40));
    }

    @Test
    void movePartyWhileEditingSequence(){
        DiagramWindow.replayRecording("movePartyWhileEditingSequence.txt",diagramWindow);
        // Party added on  x = 6  y = 29 and moved to x = 38 y = 289
        assertEquals(null,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getActiveView().getSelectableComponent(38,289));

    }

    @Test
    void movePartySequenceIllegalPosition(){
        DiagramWindow.replayRecording("movePartyWhileEditingSequence.txt",diagramWindow);
        // Party added on  x = 6  y = 29 and moved to x = 38 y = 289
        assertEquals(null,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getActiveView().getSelectableComponent(38,289));
    }

}
