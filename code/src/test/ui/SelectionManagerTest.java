package ui;

import interactr.cs.kuleuven.be.domain.DiagramComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class SelectionManagerTest {

    private SelectionManager selectionManager;
    private DiagramComponent diagramComponent;


    @BeforeEach
    void setUp() {
        selectionManager = new SelectionManager();
        diagramComponent = mock(DiagramComponent.class);
    }

    @Test
    void setAndGetActiveComponent() {
        selectionManager.setActiveComponent(diagramComponent);
        assert(selectionManager.getActiveComponent() == diagramComponent);
    }

    @Test
    void addSelectionTest() {
        selectionManager.addToSelection(diagramComponent);
        assert(selectionManager.isSelected(diagramComponent));
        assert(selectionManager.getActiveComponent() != diagramComponent);
        selectionManager.addToSelection(diagramComponent);
        assert(selectionManager.getActiveComponent() == diagramComponent);
    }

    @Test
    void removeSelectedTest() {
        selectionManager.addToSelection(diagramComponent);
        selectionManager.unselectAll();
        assert(!selectionManager.isSelected(diagramComponent));
    }


}
