package interactr.cs.kuleuven.be.UseCases;

import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.DiagramWindow;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteElement {

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
        DiagramWindow.replayRecording("addPartyLegalLabel100X.txt", diagramWindow);
        DiagramWindow.replayRecording("addPartyLegalLabel200X.txt", diagramWindow);
        DiagramWindow.replayRecording("addMessage0-100.txt",diagramWindow);
        DiagramWindow.replayRecording("addMessage100-200.txt",diagramWindow);
        DiagramWindow.replayRecording("addMessage200-100.txt",diagramWindow);
    }

    @Test
    void deleteMessageCascadeSequence(){
        DiagramWindow.replayRecording("editLabelMessage0-100.txt",diagramWindow);
        DiagramWindow.replayRecording("pressBackspace.txt",diagramWindow);
        assertEquals(0,diagramWindow.getEventHandler().getDiagramController().getDiagram().getMessages().size());
    }

    @Test
    void deletePartyCascadeSequence(){
        DiagramWindow.replayRecording("selectParty100X.txt",diagramWindow);
        DiagramWindow.replayRecording("pressBackspace.txt",diagramWindow);
        assertEquals(0,diagramWindow.getEventHandler().getDiagramController().getDiagram().getMessages().size());
        assertEquals(2,diagramWindow.getEventHandler().getDiagramController().getDiagram().getParties().size());
    }

    @Test
    void deleteMessageCascadeCommunication(){
        DiagramWindow.replayRecording("tabkey.txt",diagramWindow);
        DiagramWindow.replayRecording("mousePressLabelCom.txt",diagramWindow);
        DiagramWindow.replayRecording("pressBackspace.txt",diagramWindow);
        assertEquals(0,diagramWindow.getEventHandler().getDiagramController().getDiagram().getMessages().size());
    }

    @Test
    void deletePartyCascadeCommunication(){
        DiagramWindow.replayRecording("tabkey.txt",diagramWindow);
        DiagramWindow.replayRecording("selectParty100XCom.txt",diagramWindow);
        DiagramWindow.replayRecording("pressBackspace.txt",diagramWindow);
        assertEquals(0,diagramWindow.getEventHandler().getDiagramController().getDiagram().getMessages().size());
        assertEquals(2,diagramWindow.getEventHandler().getDiagramController().getDiagram().getParties().size());

    }

}
