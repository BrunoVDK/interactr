package test.usecases;

import interactr.cs.kuleuven.be.ui.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ActivateSubwindow {

    private DiagramWindow diagramWindow = new DiagramWindow();

    @BeforeEach
    void setUp(){
        diagramWindow.setEventHandler(new EventHandler(new DiagramController()));
        diagramWindow.setPaintBoard(new PaintBoard(diagramWindow, diagramWindow.getEventHandler().getDiagramController()));
    }

    /**
     * Adds a new sibwindow, and creates a copy of this subwindow. The copy is now activated (=first)
     * Afterwards click back on the original, this should now be the active subWindow
     */
    @Test
    void activateSubwindow(){
        DiagramWindow.replayRecording("activateSubwindow1.txt",diagramWindow);
        SubWindow first = diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow();
        DiagramWindow.replayRecording("activateSubwindow2.txt",diagramWindow);
        assertNotEquals(first,diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow());
    }

}