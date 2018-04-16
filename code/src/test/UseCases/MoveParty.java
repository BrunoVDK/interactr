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
        //DiagramWindow.replayRecording("movePartySequence.txt",diagramWindow);
        //diagramWindow.getEventHandler().getDiagramController().selectComponent(390,25);
        //assertNotEquals(null,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getSelectedComponent());

    }

    @Test
    void movePartyCommunication(){

    }

    @Test
    void movePartyWhileEditing(){

    }

}
