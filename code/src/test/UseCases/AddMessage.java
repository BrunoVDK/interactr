package UseCases;

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
    }

    @Test
    void addMessageBetweenTwoParties(){
        DiagramWindow.replayRecording("addMessageBetweenTwoParties.txt",diagramWindow);
        assertEquals( 2, diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getMessages().size());
    }

    @Test
    void moveWithMessagesSequence(){
        DiagramWindow.replayRecording("moveWithMessagesSequence.txt",diagramWindow);
        assertEquals( 2, diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getMessages().size());
    }
    @Test
    void addIllegaleMessageStack0110(){
        DiagramWindow.replayRecording("addIllegaleMessageStack0110.txt",diagramWindow);
        assertEquals( 2, diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getMessages().size());
    }

    @Test
    void addLegalMessageStack011221(){
        DiagramWindow.replayRecording("addLegalMessageStack011221.txt",diagramWindow);
        assertEquals( 6, diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getMessages().size());
    }

}
