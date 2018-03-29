package interactr.cs.kuleuven.be.UseCases;

import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.DiagramWindow;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
        DiagramWindow.replayRecording("addPartyLegalLabel100X.txt", diagramWindow);

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
    void addIllegaleMessageStack0110(){
        DiagramWindow.replayRecording("addMessage0-100.txt",diagramWindow);
        DiagramWindow.replayRecording("addMessage100-0.txt",diagramWindow);
        assertEquals(2,diagramWindow.getEventHandler().getDiagramController().getDiagram().getMessages().size());

    }
    @Test
    void addLegalMessageStack011221(){
        DiagramWindow.replayRecording("addPartyLegalLabel200X.txt", diagramWindow);
        DiagramWindow.replayRecording("addMessage0-100.txt",diagramWindow);
        DiagramWindow.replayRecording("addMessage100-200.txt",diagramWindow);
        DiagramWindow.replayRecording("addMessage200-100.txt",diagramWindow);
        assertEquals(6,diagramWindow.getEventHandler().getDiagramController().getDiagram().getMessages().size());
    }

    @Test
    void editLabelOfMessage(){
        DiagramWindow.replayRecording("addMessage0-100.txt",diagramWindow);
        DiagramWindow.replayRecording("editLabelMessage0-100.txt",diagramWindow);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DiagramWindow.replayRecording("editLabelMessage0-100.txt",diagramWindow);
        DiagramWindow.replayRecording("writeLabelB.txt",diagramWindow);
        assertEquals("b:B",diagramWindow.getEventHandler().getDiagramController().getDiagram().getMessages().getFirst().getLabel());

    }

    @Test
    void moveWithMessagesCommunication(){
        DiagramWindow.replayRecording("addMessage0-100.txt",diagramWindow);
        DiagramWindow.replayRecording("tabkey.txt", diagramWindow);
        DiagramWindow.replayRecording("moveTill200.txt",diagramWindow);
        assertEquals(2,diagramWindow.getEventHandler().getDiagramController().getDiagram().getMessages().size());
    }

    @Test
    void addMessageInCommunication(){
        DiagramWindow.replayRecording("tabkey.txt", diagramWindow);
        DiagramWindow.replayRecording("addMessage0-100.txt",diagramWindow);
        assertEquals(0,diagramWindow.getEventHandler().getDiagramController().getDiagram().getMessages().size());

    }





}
