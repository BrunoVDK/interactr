package test.interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.EventHandler;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

class EventHandlerTest extends TestCase {

    private EventHandler eventHandler;

    @Before
    protected void setUp() {
        eventHandler = new EventHandler(new DiagramController());
    }

    @After
    protected void tearDown() {
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