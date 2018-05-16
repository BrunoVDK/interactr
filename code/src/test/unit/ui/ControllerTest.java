package test.unit.ui;

import interactr.cs.kuleuven.be.exceptions.NoSuchWindowException;
import interactr.cs.kuleuven.be.ui.Controller;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ControllerTest {

    @Test
    void activateSubWindowTest() {
        Controller controller = new Controller();
        assertThrows(NoSuchWindowException.class,
                ()->{controller.activateSubWindow(0,0);});
    }

}
