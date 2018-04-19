package test.usecases;

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

public class AddMessage {

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

        //At party at 200 x, insert a label and press enter
        DiagramWindow.replayRecording("steps/createPartyAt200.txt",diagramWindow);
        DiagramWindow.replayRecording("steps/typePartyLabela:A.txt",diagramWindow);
        DiagramWindow.replayRecording("steps/pressEnter.txt",diagramWindow);


        //adds a message between the party's at 100 and 200
        DiagramWindow.replayRecording("steps/createMessageBetween100and200.txt",diagramWindow);
        DiagramWindow.replayRecording("steps/pressEnter.txt",diagramWindow);

        assertEquals(2, controller.getActiveSubwindow().getDiagram().getMessages().size());



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
