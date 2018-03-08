package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.DiagramComponent;
import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.exceptions.InvalidAddPartyException;
import interactr.cs.kuleuven.be.ui.geometry.Figure;

/**
 * A class of communication views. Communication views display diagrams
 *  as a composite of parties with messages sent between them. Parties
 *  can be located at any coordinate.
 *
 * @author Team 25
 * @version 1.0
 */
public class CommunicationView extends DiagramView {

    @Override
    public void display(PaintBoard paintBoard, Diagram diagram,SelectionManager selectionManager) {
        for (Party party : diagram.getParties()) {
            Figure partyFigure = figures.get(party);
            partyFigure.draw(paintBoard);
        }
    }

    @Override
    public DiagramComponent selectableComponentAt(Diagram diagram, int x, int y) {
        return null;
    }

    @Override
    public String viewName() {
        return "Communication View";
    }

    public boolean canAddMessage(Party sender, Party receiver, int y){return false;}


}