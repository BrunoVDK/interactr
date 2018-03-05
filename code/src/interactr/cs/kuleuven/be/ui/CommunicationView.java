package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Party;

public class CommunicationView implements DiagramView {

    @Override
    public void draw(Diagram diagram) {

    }

    @Override
    public Party getPartyAt(Diagram diagram, int x, int y) {
        return null;
    }

    @Override
    public boolean canAddPartyAt(Diagram diagram, int x, int y) {
        return false;
    }

    public String toString() {
        return "Communication View";
    }

}