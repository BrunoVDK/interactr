package usecases;

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
    }

    /**
     * Add 1 party to sequence and delete it afterwards
     */
    @Test
    void deletePartySequence(){
        DiagramWindow.replayRecording("deletePartySequence.txt",diagramWindow);
        assertEquals(0, diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getParties().size());
    }

    /**
     * Add a party switch to com view and delete it there
     */
    @Test
    void deletePartyCommunication(){
        DiagramWindow.replayRecording("deletePartyCommunication.txt",diagramWindow);
        assertEquals(0, diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getParties().size());
    }

    /**
     * Adds two parties and a message betweent them which gets deleted
     */
    @Test
    void deleteMessageSequence(){
        DiagramWindow.replayRecording("deleteMessageSequence.txt",diagramWindow);
        assertEquals(0, diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getMessages().size());
    }

    /**
     * Adds two parties and a message between them, after that the view is switched to delete the message
     */
    @Test
    void deleteMessageCommunication(){
        DiagramWindow.replayRecording("deleteMessageCommunication.txt",diagramWindow);
        assertEquals(0, diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getMessages().size());
    }









}
