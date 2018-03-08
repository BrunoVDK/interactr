package interactr.cs.kuleuven.be.UseCases;

import interactr.cs.kuleuven.be.domain.ActorParty;
import interactr.cs.kuleuven.be.domain.ObjectParty;
import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.DiagramWindow;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class SwitchPartyType {

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
        DiagramWindow.replayRecording("doubleClick.txt", diagramWindow);

    }

    @Test
    void switchFromObjectToActorSequence(){
        DiagramWindow.replayRecording("doubleClick.txt", diagramWindow);
        assert (diagramWindow.getEventHandler().getDiagramController().getDiagram().getParties().getFirst() instanceof ActorParty);
    }


    @Test
    void SwitchFromActorToObjectSequence(){
        DiagramWindow.replayRecording("doubleClickTwice.txt", diagramWindow);
        assert (diagramWindow.getEventHandler().getDiagramController().getDiagram().getParties().getFirst() instanceof ObjectParty);

    }

}

