package usecases;

import interactr.cs.kuleuven.be.ui.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ResizeSubwindow {
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
        SubWindow original = controller.getActiveSubwindow();
        assertNotNull(original);
        DiagramWindow.replayRecording("steps/resizeSubWindow.txt",diagramWindow);
        assertTrue(controller.getActiveSubwindow().getFrame().getWidth() > 400);
        assertTrue(controller.getActiveSubwindow().getFrame().getHeight() > 400);
    }

    @Test
    void resizeNordLarger(){
        DiagramWindow.replayRecording("resizeNordLarger.txt",diagramWindow);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getHeight() > 400);
        //In the txt file the subwindow is first moved down till y = 198
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getY() < 198);

    }

    @Test
    void resizeNordSmaller(){
        DiagramWindow.replayRecording("resizeNordSmaller.txt",diagramWindow);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getHeight() < 400);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getY() > 0);



    }

    @Test
    void resizeNordEastLarger(){
        DiagramWindow.replayRecording("resizeNordEastLarger.txt",diagramWindow);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getHeight() > 400);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getWidth() > 400);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getY() < 164);
    }

    @Test
    void resizeNordEastSmaller(){
        DiagramWindow.replayRecording("resizeNordEastSmaller.txt",diagramWindow);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getHeight() < 400);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getWidth() < 400);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getY() > 0);

    }

    @Test
    void resizeEastLarger(){
        DiagramWindow.replayRecording("resizeEastLarger.txt",diagramWindow);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getWidth() > 400);
    }

    @Test
    void resizeEastSmaller(){
        DiagramWindow.replayRecording("resizeEastSmaller.txt",diagramWindow);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getWidth() < 400);
    }
    @Test
    void resizeSouthEastLarger(){
        DiagramWindow.replayRecording("resizeSouthEastLarger.txt",diagramWindow);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getWidth() > 400);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getHeight() > 400);
    }

    @Test
    void resizeSouthEastSmaller(){
        DiagramWindow.replayRecording("resizeSouthEastSmaller.txt",diagramWindow);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getWidth() < 400);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getHeight() < 400);
    }

    @Test
    void resizeSouthLarger(){
        DiagramWindow.replayRecording("resizeSouthLarger.txt",diagramWindow);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getHeight() > 400);
    }

    @Test
    void resizeSouthSmaller(){
        DiagramWindow.replayRecording("resizeSouthSmaller.txt",diagramWindow);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getHeight() < 400);
    }

    @Test
    void resizeSouthWestLarger(){
        DiagramWindow.replayRecording("resizeSouthWestLarger.txt",diagramWindow);
        //496 74
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getHeight() > 400);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getWidth() > 400);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getX() < 496);
    }

    @Test
    void resizeSouthWestSmaller(){
        DiagramWindow.replayRecording("resizeSouthWestSmaller.txt",diagramWindow);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getHeight() < 400);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getWidth() < 400);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getX() > 0);
    }

    @Test
    void resizeWestLarger(){
        DiagramWindow.replayRecording("resizeWestLarger.txt",diagramWindow);
        //533 156
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getWidth() > 400);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getX() < 533);
    }

    @Test
    void resizeWestSmaller(){
        DiagramWindow.replayRecording("resizeWestSmaller.txt",diagramWindow);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getWidth() < 400);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getX() > 3);

    }
    @Test
    void resizeNordWestLarger(){
        DiagramWindow.replayRecording("resizeNordWestLarger.txt",diagramWindow);
        //322 126
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getHeight() > 400);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getWidth() > 400);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getX() < 322);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getY() < 126);
    }

    @Test
    void resizeNordWestSmaller(){
        DiagramWindow.replayRecording("resizeNordWestSmaller.txt",diagramWindow);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getHeight() < 400);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getWidth() < 400);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getX() > 0);
        assertTrue(diagramWindow.getEventHandler().getDiagramController().getActiveSubwindow().getFrame().getY() > 0);
    }


}
