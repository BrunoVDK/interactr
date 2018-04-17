package unit.ui.geometry;

import interactr.cs.kuleuven.be.ui.geometry.Figure;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FigureTest {

    @Test
    void cloneTest() {
        Figure figure = new Figure();
        Figure figure2 = figure.clone();
        assertEquals(figure.getX(), figure2.getX());
        assertEquals(figure.getY(), figure2.getY());
        assertEquals(figure.getWidth(), figure2.getWidth());
        assertEquals(figure.getHeight(), figure2.getHeight());
    }

}
