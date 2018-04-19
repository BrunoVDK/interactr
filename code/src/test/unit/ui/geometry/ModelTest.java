package unit.ui.geometry;

import interactr.cs.kuleuven.be.ui.geometry.Model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ModelTest {

    @Test
    void invalidLabelTest() {
        assertThrows(IllegalArgumentException.class,
                ()->{Model model = new Model(null);});
    }

}
