package interactr.cs.kuleuven.be.UseCases;

import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.DiagramWindow;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;


public class SwitchViewTest {

    private DiagramWindow diagramWindow = new DiagramWindow();

    @Test
    public void switchViewTest() throws NoSuchFieldException, IllegalAccessException {
        diagramWindow.setEventHandler(new EventHandler(new DiagramController()));
        diagramWindow.setPaintBoard(new PaintBoard(diagramWindow, diagramWindow.getEventHandler().getDiagramController()));
        DiagramWindow.replayRecording("tabkey.txt", diagramWindow);
        Field f = diagramWindow.getEventHandler().getDiagramController().getClass().getDeclaredField("activeViewIndex"); //NoSuchFieldException
        f.setAccessible(true);
        int currentView = (int) f.get(diagramWindow.getEventHandler().getDiagramController());
        System.out.println(currentView);
        assertEquals(1,currentView);
    }


}
