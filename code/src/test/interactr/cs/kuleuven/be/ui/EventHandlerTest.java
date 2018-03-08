package test.interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.EventHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class EventHandlerTest {

    private EventHandler eventHandler;

    @BeforeEach
    protected void setUp() {
        eventHandler = new EventHandler(new DiagramController());
    }

    @AfterEach
    protected void tearDown() {
    }

    @Test
    void handleMouseEventWithNegativeX() throws Exception {
        assertThrows(IllegalArgumentException.class, () ->
        eventHandler.handleMouseEvent( 0, - 1, 1, 1));
    }

    @Test
    void handMouseEventWithNegativeY() throws Exception{
        assertThrows(IllegalArgumentException.class, () ->
        eventHandler.handleMouseEvent( 0, - 1, 1, 1));
    }


}