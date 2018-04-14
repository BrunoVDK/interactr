package UseCasesNormal;

import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.DiagramWindow;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SwitchDiagramTypeNormal {

    private DiagramWindow diagramWindow = new DiagramWindow();

    @BeforeEach
    void setUp(){
        diagramWindow.setEventHandler(new EventHandler(new DiagramController()));
        diagramWindow.setPaintBoard(new PaintBoard(diagramWindow, diagramWindow.getEventHandler().getDiagramController()));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    void switchFromObjectToActor(){
    }

    @Test
    void switchFromActorToObject(){
    }



}
