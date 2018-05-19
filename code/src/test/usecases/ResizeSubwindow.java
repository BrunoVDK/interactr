package usecases;

import interactr.cs.kuleuven.be.ui.*;
import interactr.cs.kuleuven.be.ui.control.SubWindow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResizeSubwindow {
    private Window window = new Window("Test Window");

    @BeforeEach
    void setUp(){
        window.setEventHandler(new EventHandler(new Controller()));
        window.setPaintBoard(new PaintBoard(window, window.getEventHandler().getController()));
    }

    @Test
    void stepByStep(){
        Controller controller = window.getEventHandler().getController();
        // Precondition - create new subwindow and ensure it is there
        Window.replayRecording("steps/createNewDiagram.txt", window);
        SubWindow original = controller.getActiveSubwindow();
        assertNotNull(original);
        // Resize the new subwindow
        Window.replayRecording("steps/resizeSubWindow.txt", window);
        assertTrue(controller.getActiveSubwindow().getFrame().getWidth() > 400);
        assertTrue(controller.getActiveSubwindow().getFrame().getHeight() > 400);
    }

    @Test
    void resizeEastSmaller(){
        Window.replayRecording("resizeEastSmaller.txt", window);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getWidth() < 400);
    }
    @Test
    void resizeSouthEastLarger(){
        Window.replayRecording("resizeSouthEastLarger.txt", window);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getWidth() > 400);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getHeight() > 400);
    }

    @Test
    void resizeSouthEastSmaller(){
        Window.replayRecording("resizeSouthEastSmaller.txt", window);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getWidth() < 400);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getHeight() < 400);
    }

    @Test
    void resizeSouthLarger(){
        Window.replayRecording("resizeSouthLarger.txt", window);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getHeight() > 400);
    }

    @Test
    void resizeSouthSmaller(){
        Window.replayRecording("resizeSouthSmaller.txt", window);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getHeight() < 400);
    }

    @Test
    void resizeSouthWestLarger(){
        Window.replayRecording("resizeSouthWestLarger.txt", window);
        //496 74
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getHeight() > 400);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getWidth() > 400);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getX() < 496);
    }

    @Test
    void resizeSouthWestSmaller(){
        Window.replayRecording("resizeSouthWestSmaller.txt", window);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getHeight() < 400);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getWidth() < 400);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getX() > 0);
    }

    @Test
    void resizeWestLarger(){
        Window.replayRecording("resizeWestLarger.txt", window);
        //533 156
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getWidth() > 400);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getX() < 533);
    }

    @Test
    void resizeWestSmaller(){
        Window.replayRecording("resizeWestSmaller.txt", window);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getWidth() < 400);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getX() > 3);

    }

}
