package usecases;

import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.Window;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CloseWindow {
    private Window window = new Window();

    @BeforeEach
    void setUp(){
        window.setEventHandler(new EventHandler(new DiagramController()));
        window.setPaintBoard(new PaintBoard(window, window.getEventHandler().getDiagramController()));
    }

    @Test
    void stepByStep(){
        DiagramController controller = window.getEventHandler().getDiagramController();
        // Precondition
        Window.replayRecording("steps/createNewDiagram.txt", window);
        assertNotNull(controller.getActiveSubwindow());
        Window.replayRecording("steps/pressCloseButton.txt", window);
        assertNull(controller.getActiveSubwindow());
    }

    @Test
    void closeWindow(){
        Window.replayRecording("closeWindow.txt", window);
        assertEquals(null, window.getEventHandler().getDiagramController().getActiveSubwindow());
    }
}
