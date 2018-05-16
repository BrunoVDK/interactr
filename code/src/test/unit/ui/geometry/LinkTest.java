package test.unit.ui.geometry;

import interactr.cs.kuleuven.be.ui.design.Link;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LinkTest {

    @Test
    void cloneTest() {
        Link link = new Link();
        Link link2 = link.clone();
        assertEquals(link.getStartX(), link2.getStartX());
        assertEquals(link.getStartY(), link2.getStartY());
        assertEquals(link.getEndX(), link2.getEndX());
        assertEquals(link.getEndY(), link2.getEndY());
    }

    @Test
    void crossTest() {
        Link link = new Link(0, 0, 10, 10);
        Link link2 = new Link(10, 0, 0, 10);
        assertTrue(link.crosses(link2));
        link = new Link(0, 0, 10, 10);
        link2 = new Link(0, 0, 0, 10);
        assertTrue(link.crosses(link2));
    }

}
