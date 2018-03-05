package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Party;

public abstract interface DiagramView {

    public void draw(Diagram diagram);

    public Party getPartyAt(Diagram diagram, int x, int y);

    public boolean canAddPartyAt(Diagram diagram, int x, int y);


}