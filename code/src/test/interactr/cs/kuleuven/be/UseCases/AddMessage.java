package interactr.cs.kuleuven.be.UseCases;

import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.DiagramWindow;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddMessage {

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
        DiagramWindow.replayRecording("addPartyLegalLabel.txt", diagramWindow);
        DiagramWindow.replayRecording("addPartyLegalLabel100X.txt", diagramWindow);;

    }

    @Test
    void addMessageBetweenTwoPartiesSequence(){
        DiagramWindow.replayRecording("addMessage0-100.txt",diagramWindow);
        assertEquals(2,diagramWindow.getEventHandler().getDiagramController().getDiagram().getMessages().size());
    }

    @Test
    void moveWithMessagesSequence(){
        DiagramWindow.replayRecording("addMessage0-100.txt",diagramWindow);
        DiagramWindow.replayRecording("moveTill200.txt",diagramWindow);
        assertEquals(2,diagramWindow.getEventHandler().getDiagramController().getDiagram().getMessages().size());
    }

    @Test
    void addIllegaleMessageBetweenTwoParties(){
        DiagramWindow.replayRecording("addMessage0-100.txt",diagramWindow);
        DiagramWindow.replayRecording("addMessage100-0.txt",diagramWindow);
        assertEquals(2,diagramWindow.getEventHandler().getDiagramController().getDiagram().getMessages().size());

    }


}
