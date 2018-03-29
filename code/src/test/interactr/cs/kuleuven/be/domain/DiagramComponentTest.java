package interactr.cs.kuleuven.be.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiagramComponentTest {

    private DiagramComponent diagramComponent;

    @BeforeEach
    void setUp() {
        diagramComponent = new DiagramComponent() {
            @Override
            public void delete(Diagram diagram) {}
        };
    }

    @Test
    void canHaveAsLabelTest() {
        assert(diagramComponent.canHaveAsLabel("bluh"));
        assert(!diagramComponent.canHaveAsLabel("0123456789012345678901234567890123"));
    }

    @Test
    void setAndGetLabelTest() {
        diagramComponent.setLabel("bluh");
        assert(diagramComponent.getLabel().equals("bluh"));
    }

}
