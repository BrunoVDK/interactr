package ui;

import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.EventHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class EventHandlerTest {

    private EventHandler eventHandler;

    @BeforeEach
    void setUp() {
        eventHandler = new EventHandler(new DiagramController());
    }


    @Test
    void handleMouseEventWithNegativeX() {
        try {
            eventHandler.handleMouseEvent( 0, - 1, 1, 1);
        } catch(IllegalArgumentException e) {
            assert(false);
        }
    }

    @Test
    void handMouseEventWithNegativeY() {
        try {
           eventHandler.handleMouseEvent( 0, - 1, 1, 1);
        } catch(IllegalArgumentException e) {
            assert(false);
        }
    }


    @Test
    void setAndGetDiagramController() {
        DiagramController diagramController = mock(DiagramController.class);
        eventHandler.setDiagramController(diagramController);
        assert(eventHandler.getDiagramController() == diagramController);
    }

}