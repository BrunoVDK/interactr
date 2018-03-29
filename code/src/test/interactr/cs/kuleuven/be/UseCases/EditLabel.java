package interactr.cs.kuleuven.be.UseCases;

import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.DiagramWindow;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditLabel {

    private DiagramWindow diagramWindow = new DiagramWindow();

    @BeforeEach
    void setUp() throws Exception{
        diagramWindow.setEventHandler(new EventHandler(new DiagramController()));
        diagramWindow.setPaintBoard(new PaintBoard(diagramWindow, diagramWindow.getEventHandler().getDiagramController()));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DiagramWindow.replayRecording("addPartyLegalLabel.txt", diagramWindow);

    }

    @Test
    void editLabelOfPartySequence(){
        DiagramWindow.replayRecording("mousePressOnLabel.txt",diagramWindow);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DiagramWindow.replayRecording("mousePressOnLabel.txt",diagramWindow);
        DiagramWindow.replayRecording("writeLabelB.txt",diagramWindow);
        assertEquals("b:B",diagramWindow.getEventHandler().getDiagramController().getActiveView().getPartyAt(10,10).getLabel());
    }
}
