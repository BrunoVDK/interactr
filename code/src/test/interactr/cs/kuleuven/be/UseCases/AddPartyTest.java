package interactr.cs.kuleuven.be.UseCases;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.DiagramWindow;
import interactr.cs.kuleuven.be.ui.EventHandler;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.Before;
import org.junit.Test;

public class AddPartyTest {

    private EventHandler eventHandler;

    private DiagramController diagramController;

    private DiagramWindow diagramWindow = new DiagramWindow();

    @Before
    void setUp(){
        diagramController = new DiagramController();
        eventHandler = new EventHandler(diagramController);
        diagramWindow.setEventHandler(new EventHandler(new DiagramController()));
        diagramWindow.setPaintBoard(new PaintBoard(diagramWindow, diagramWindow.getEventHandler().getDiagramController()));
        diagra
    }

    @Test
    void addPartyToEmptyAreaSequence(){

    }

    @Test
    void addPartyToOccupiedAreaSequence(){

    }

    @Test
    void addPartyToEmptyAreaCommunication(){

    }

    @Test
    void addPartyToOccupiedAreaCommunication(){

    }
}
