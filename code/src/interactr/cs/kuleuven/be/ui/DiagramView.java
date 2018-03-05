package interactr.cs.kuleuven.be.ui;

public abstract interface DiagramView {

    public void draw(Diagram diagram);

    public void getComponentAt(Diagram diagram, int x, int y);

}