package interactr.cs.kuleuven.be.ui;

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

    public abstract void getComponentAt(Diagram diagram, int x, int y);

}