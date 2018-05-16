package test.usecases;

import interactr.cs.kuleuven.be.ui.*;
import interactr.cs.kuleuven.be.ui.control.SubWindow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ResizeSubwindow {
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
        SubWindow original = controller.getActiveSubwindow();
        assertNotNull(original);
        Window.replayRecording("steps/resizeSubWindow.txt", window);
        assertTrue(controller.getActiveSubwindow().getFrame().getWidth() > 400);
        assertTrue(controller.getActiveSubwindow().getFrame().getHeight() > 400);
    }

    @Test
    void resizeNordLarger(){
        Window.replayRecording("resizeNordLarger.txt", window);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getHeight() > 400);
        //In the txt file the subwindow is first moved down till y = 198
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getY() < 198);

    }

    @Test
    void resizeNordSmaller(){
        Window.replayRecording("resizeNordSmaller.txt", window);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getHeight() < 400);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getY() > 0);



    }

    @Test
    void resizeNordEastLarger(){
        Window.replayRecording("resizeNordEastLarger.txt", window);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getHeight() > 400);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getWidth() > 400);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getY() < 164);
    }

    @Test
    void resizeNordEastSmaller(){
        Window.replayRecording("resizeNordEastSmaller.txt", window);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getHeight() < 400);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getWidth() < 400);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getY() > 0);

    }

    @Test
    void resizeEastLarger(){
        Window.replayRecording("resizeEastLarger.txt", window);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getWidth() > 400);
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
    @Test
    void resizeNordWestLarger(){
        Window.replayRecording("resizeNordWestLarger.txt", window);
        //322 126
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getHeight() > 400);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getWidth() > 400);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getX() < 322);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getY() < 126);
    }

    @Test
    void resizeNordWestSmaller(){
        Window.replayRecording("resizeNordWestSmaller.txt", window);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getHeight() < 400);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getWidth() < 400);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getX() > 0);
        assertTrue(window.getEventHandler().getController().getActiveSubwindow().getFrame().getY() > 0);
    }


}
