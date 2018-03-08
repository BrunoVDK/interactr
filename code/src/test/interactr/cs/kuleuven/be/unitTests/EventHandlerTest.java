package interactr.cs.kuleuven.be.unitTests;

import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.EventHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventHandlerTest {

    private EventHandler eventHandler;


    @BeforeEach
    void setUp() {
        eventHandler = new EventHandler(new DiagramController());
    }

    @AfterEach
    void tearDown() {
    }

    @Test(expected=IllegalArgumentException.class)
    void handleMouseEventWithNegativeX() throws Exception {
        eventHandler.handleMouseEvent( 0, - 1, 1, 1);
    }

    @Test(expected=IllegalArgumentException.class)
    void handMouseEventWithNegativeY() throws Exception{
        eventHandler.handleMouseEvent( 0, - 1, 1, 1);
    }


}