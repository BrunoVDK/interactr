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

public class EditLabel {
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
        DiagramWindow.replayRecording("steps/selectPartyAt100.txt",diagramWindow);

        DiagramWindow.replayRecording("steps/typePartyLabelb:B.txt",diagramWindow);
        DiagramWindow.replayRecording("steps/pressEnter.txt",diagramWindow);
        DiagramWindow.replayRecording("steps/selectPartyAt100.txt",diagramWindow);
        assertEquals(controller.getActiveSubwindow().getSelectedComponent().getLabel(), "b:B");

    }

    @Test
    /**
     * The partys label is changedfrom a:A to b:B
     */
    void editLabelParty(){
        DiagramWindow.replayRecording("editLabelParty.txt",diagramWindow);
        assertEquals("b:B", diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getSelectedComponent().getLabel());
    }

    @Test
    /**
     * The partys label is changedfrom a:A to b
     */
    void editLabelPartyIllegalLabel(){
        DiagramWindow.replayRecording("editLabelPartyIllegalLabel.txt",diagramWindow);
        assertEquals("a:A", diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getSelectedComponent().getLabel());
    }

    /**
     * Change a invocation message label from "c" to "d"
     */
    @Test
    void editLabelInvocationMessage(){
        DiagramWindow.replayRecording("editLabelInvocationMessage.txt",diagramWindow);
        assertEquals("d", diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getMessages().getFirst().getLabel());
    }



    /**
     * Change a result message label to "d"
     */
    @Test
    void editLabelResultMessage(){
        DiagramWindow.replayRecording("editLabelResultMessage01.txt",diagramWindow);
        assertEquals("d", diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getDiagram().getMessages().get(1).getLabel());
    }


}
