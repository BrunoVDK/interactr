package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.DiagramComponent;
import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.exceptions.InvalidAddException;
import interactr.cs.kuleuven.be.ui.geometry.Figure;
import interactr.cs.kuleuven.be.ui.geometry.Point;

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
    public void display(PaintBoard paintBoard, Diagram diagram) {
        for (Party party : diagram.getParties()) {
            Figure partyFigure = party.getProposedFigure();
            Point partyCoordinate = getCoordinate(party);
            partyFigure.setX(partyCoordinate.getX());
            partyFigure.setY(partyCoordinate.getY());
            partyFigure.draw(paintBoard);
        }
    }

    @Override
    public void addParty(Diagram diagram, Party party, int x, int y) throws InvalidAddException {
        for (Party p : coordinates.keySet())
            if (p.getProposedFigure().encloses(x,y))
                throw new InvalidAddException(p);
        coordinates = coordinates.plus(party, new Point(x,y));
    }

    @Override
    public DiagramComponent selectableComponentAt(Diagram diagram, int x, int y) {
        return null;
    }

    @Override
    public String viewName() {
        return "Communication View";
    }

}