/*
package interactr.cs.kuleuven.be.unitTests;


import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.ui.DiagramController;
import interactr.cs.kuleuven.be.ui.DiagramView;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class DiagramControllerTest {

    private DiagramController diagramController;
    private Diagram diagram;
    private ArrayList<DiagramView> views;
    private PaintBoard paintBoard;

    @BeforeEach
    void setup() {
        diagram = mock(Diagram.class);
        views = new ArrayList<>();
        views.add(mock(DiagramView.class));
        views.add(mock(DiagramView.class));
        paintBoard = mock(PaintBoard.class);
        diagramController = new DiagramController(diagram, views);
        diagramController.setPaintBoard(paintBoard);
    }

    @Test
    void testSetPaintBoard() {
        PaintBoard paintBoard = mock(PaintBoard.class);
        diagramController.setPaintBoard(paintBoard);
        assert(diagramController.getPaintBoard() == paintBoard);
    }

    @Test
    void testDiagram() {
        assert(diagramController.getDiagram() == diagram);
    }

    @Test
    void testView() {
        diagramController.nextView();
        diagramController.displayView();
        verify(views.get(1)).display(diagramController.getPaintBoard(),diagram);
    }

    @Test
    void testAddParty() {
        diagramController.addPartyAt(5,5);
        verify(views.get(0)).addParty(eq(diagram),argThat(x -> true),eq(5),eq(5));
        verify(views.get(1)).registerParty(argThat(x -> true),eq(5),eq(5));
        verify(diagram).addParty(argThat(x -> true));
    }

}
*/
