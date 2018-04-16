package UseCases;

import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.DiagramWindow;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditLabel {
    private DiagramWindow diagramWindow = new DiagramWindow();

    @BeforeEach
    void setUp(){
        diagramWindow.setEventHandler(new EventHandler(new DiagramController()));
        diagramWindow.setPaintBoard(new PaintBoard(diagramWindow, diagramWindow.getEventHandler().getDiagramController()));
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

}
