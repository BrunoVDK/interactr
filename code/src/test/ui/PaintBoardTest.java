package ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class PaintBoardTest {

    private PaintBoard paintBoard;
    private DiagramWindow diagramWindow;
    private DiagramController diagramController;


    @BeforeEach
    void setUp() {
        diagramController = mock(DiagramController.class);
        diagramWindow = mock(DiagramWindow.class);
        paintBoard = new PaintBoard(diagramWindow, diagramController);
    }

    @Test
    void invalidConstructorTest() {
        assertThrows(IllegalArgumentException.class,
                () -> new PaintBoard(null, null));
    }



}
