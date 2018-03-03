package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.*;

public abstract class DiagramView {

    public DiagramView(Diagram diagram) {
        setDiagram(diagram);
    }

    public Diagram getDiagram() {
        return this.diagram;
    }

    public void setDiagram(Diagram diagram) {
        this.diagram = diagram;
    }

    private Diagram diagram;

    public abstract void draw(Diagram diagram);
    public abstract void getPartyAt(int x, int y);

}