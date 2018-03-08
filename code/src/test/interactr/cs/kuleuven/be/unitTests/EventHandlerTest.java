package interactr.cs.kuleuven.be.unitTests;

import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.EventHandler;
import org.junit.Before;
import org.junit.Test;


class EventHandlerTest{

    private EventHandler eventHandler;
    @Before
    void setUp() {
        eventHandler = new EventHandler(new DiagramController());
    }

    @Test(expected = IllegalArgumentException.class)
    void handleMouseEventWithNegativeX() {
        eventHandler.handleMouseEvent(0, -1, 1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    void handMouseEventWithNegativeY(){
        eventHandler.handleMouseEvent( 0, 1, -1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    void handMouseEventWithNegativeClickCount(){
        eventHandler.handleMouseEvent( 0, 1, 1, -1);
    }

}