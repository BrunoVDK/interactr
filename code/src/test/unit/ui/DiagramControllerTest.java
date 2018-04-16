package unit.ui;

import interactr.cs.kuleuven.be.exceptions.NoSuchWindowException;
import interactr.cs.kuleuven.be.ui.DiagramController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DiagramControllerTest {

    @Test
    void activateSubWindowTest() {
        DiagramController controller = new DiagramController();
        assertThrows(NoSuchWindowException.class,
                ()->{controller.activateSubWindow(0,0);});
    }

}
