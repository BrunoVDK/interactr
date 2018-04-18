package usecases;

import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.DiagramWindow;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoveSubwindow {

    private DiagramWindow diagramWindow = new DiagramWindow();

    @BeforeEach
    void setUp(){
        diagramWindow.setEventHandler(new EventHandler(new DiagramController()));
        diagramWindow.setPaintBoard(new PaintBoard(diagramWindow, diagramWindow.getEventHandler().getDiagramController()));
    }

    @Test
    void moveSubwindow(){
        DiagramWindow.replayRecording("moveSubwindow.txt",diagramWindow);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getX() > 0);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getY() > 0);
    }
}
