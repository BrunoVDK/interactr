package usecases;

import interactr.cs.kuleuven.be.ui.*;
import interactr.cs.kuleuven.be.ui.control.SubWindow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateNewInteraction {

    private Window window = new Window();

    @BeforeEach
    void setUp(){
        window.setEventHandler(new EventHandler(new Controller()));
        window.setPaintBoard(new PaintBoard(window, window.getEventHandler().getController()));
    }
    @Test
    void stepByStep(){
        Controller controller = window.getEventHandler().getController();
        // Precondition
        Window.replayRecording("steps/createNewDiagram.txt", window);
        assertNotNull(controller.getActiveSubwindow());
        SubWindow original = controller.getActiveSubwindow();
        Window.replayRecording("steps/moveNewDiagram.txt", window);
        Window.replayRecording("steps/createCopyDiagram.txt", window);
        assertEquals(original.getDiagram(), controller.getActiveSubwindow().getDiagram());
    }

    @Test
    void createNewSubWindow(){
        Window.replayRecording("createNewSubWindow.txt", window);
        assertNotEquals(null, window.getEventHandler().getController().getActiveSubwindow());
    }

}
