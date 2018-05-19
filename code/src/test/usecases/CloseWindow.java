package usecases;

import interactr.cs.kuleuven.be.ui.Controller;
import interactr.cs.kuleuven.be.ui.Window;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CloseWindow {

    private Window window = new Window("Test Window");

    @BeforeEach
    void setUp(){
        window.setEventHandler(new EventHandler(new Controller()));
        window.setPaintBoard(new PaintBoard(window, window.getEventHandler().getController()));
    }

    @Test
    void stepByStep() {
        Controller controller = window.getEventHandler().getController();
        // Precondition - create new diagram window
        Window.replayRecording("steps/createNewDiagram.txt", window);
        assertNotNull(controller.getActiveSubwindow());
        // Click on the subwindow's close button
        Window.replayRecording("steps/pressCloseButton.txt", window);
        assertTrue(controller.getActiveSubwindow().isClosed());
    }

    @Test
    void closeWindow() {
        Window.replayRecording("closeWindow.txt", window);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().isClosed());
    }

}
