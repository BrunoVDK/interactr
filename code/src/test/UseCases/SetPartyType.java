package usecases;

import interactr.cs.kuleuven.be.domain.ActorParty;
import interactr.cs.kuleuven.be.domain.ObjectParty;
import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.DiagramWindow;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetPartyType {

    private DiagramWindow diagramWindow = new DiagramWindow();

    @BeforeEach
    void setUp(){
        diagramWindow.setEventHandler(new EventHandler(new DiagramController()));
        diagramWindow.setPaintBoard(new PaintBoard(diagramWindow, diagramWindow.getEventHandler().getDiagramController()));
    }

    @Test
    void switchObjectToActorSequence(){
        DiagramWindow.replayRecording("switchObjectToActorSequence.txt",diagramWindow);
        assertEquals(true,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getParties().getFirst() instanceof ActorParty);
    }

    @Test
    void switchActorToObjectSequence(){
        DiagramWindow.replayRecording("switchActorToObjectSequence.txt",diagramWindow);
        assertEquals(true,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getParties().getFirst() instanceof ObjectParty);
    }
    @Test
    void switchObjectToActorCommunication(){
        DiagramWindow.replayRecording("switchObjectToActorCommunication.txt",diagramWindow);
        assertEquals(true,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getParties().getFirst() instanceof ActorParty);
    }

    @Test
    void switchActorToObjectCommunication(){
        DiagramWindow.replayRecording("switchActorToObjectCommunication.txt",diagramWindow);
        assertEquals(true,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getParties().getFirst() instanceof ObjectParty);
    }

    @Test
    void switchWhileEditing(){
        DiagramWindow.replayRecording("switchWhileEditing.txt",diagramWindow);
        assertEquals(true,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getParties().getFirst() instanceof ObjectParty);
    }
}
