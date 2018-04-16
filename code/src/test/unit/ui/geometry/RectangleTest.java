package unit.ui.geometry;

import interactr.cs.kuleuven.be.ui.geometry.Rectangle;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {

    @Test
    void invalidSizeTest() {
        assertThrows(IllegalArgumentException.class,
                ()->{new Rectangle(0, 0, -1, 10);});
        assertThrows(IllegalArgumentException.class,
                ()->{new Rectangle(0, 0, 10, -1);});
    }

    @Test
    void toStringTest() {
        Rectangle rectangle = new Rectangle(0, 0, 10, 10);
        assertNotNull(rectangle.toString());
    }

}
