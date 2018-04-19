package usecases;

import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.DiagramWindow;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteElement {

    private DiagramWindow diagramWindow = new DiagramWindow();

    @BeforeEach
    void setUp(){
        diagramWindow.setEventHandler(new EventHandler(new DiagramController()));
        diagramWindow.setPaintBoard(new PaintBoard(diagramWindow, diagramWindow.getEventHandler().getDiagramController()));
    }

    @Test
    void stepByStep(){
        DiagramController controller = diagramWindow.getEventHandler().getDiagramController();
        // Precondition
        DiagramWindow.replayRecording("steps/createNewDiagram.txt",diagramWindow);
        //At party at 100 x
        DiagramWindow.replayRecording("steps/createPartyAt100.txt",diagramWindow);
        assertTrue(controller.getActiveSubwindow().getDiagram().getParties().size() > 0);
        // Assert that it is selected
        Party newParty = controller.getActiveSubwindow().getDiagram().getParties().get(0);
        assertEquals(controller.getActiveSubwindow().getSelectedComponent(), newParty);
        // Type label
        DiagramWindow.replayRecording("steps/typePartyLabela:A.txt",diagramWindow);
        assertEquals(controller.getActiveSubwindow().getSelectedLabel(), "a:A");
        assertEquals(newParty.getLabel(), "a:A");
        // Press enter
        DiagramWindow.replayRecording("steps/pressEnter.txt",diagramWindow);
        assertNull(controller.getActiveSubwindow().getSelectedComponent());
        assertEquals(newParty.getLabel(), "a:A");

        DiagramWindow.replayRecording("steps/selectPartyAt100.txt",diagramWindow);
        DiagramWindow.replayRecording("steps/pressBackSpace.txt",diagramWindow);

        assertEquals(0, controller.getActiveSubwindow().getDiagram().getParties().size());

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

    /**
     * Adds 3 messages between 3 parties (011220) and deletes the middle party
     */
    @Test
    void deletePartyAvalanche(){
        DiagramWindow.replayRecording("deletePartyMessageAvalanche.txt",diagramWindow);
        assertEquals(0, diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getMessages().size());
        assertEquals(2, diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getParties().size());
    }

    /**
     * dds 3 messages between 3 parties (01 12 20) and deletes the second message
     */
    @Test
    void deleteMessageAvalanche(){
        DiagramWindow.replayRecording("deleteMessageAvalanche.txt",diagramWindow);
        assertEquals(2, diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getMessages().size());
        assertEquals(3, diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getParties().size());
    }









}
