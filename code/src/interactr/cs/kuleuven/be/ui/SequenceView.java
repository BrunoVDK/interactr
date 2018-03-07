package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.DiagramComponent;
import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.exceptions.InvalidAddException;
import interactr.cs.kuleuven.be.ui.geometry.Figure;
import interactr.cs.kuleuven.be.ui.geometry.Point;

/**
 * A class of sequence diagram views. Sequence diagram views display diagrams
 *  as a composite of parties with messages sent between them.
 *  The parties are drawn in a top row, while the messages are drawn in the rows
 *  underneath this top row.
 *  Below each party, a lifeline is drawn.
 *
 * @author Team 25
 * @version 1.0
 */
public class SequenceView extends DiagramView {

    @Override
    public void display(PaintBoard paintBoard, Diagram diagram) {
        for (Party party : diagram.getParties()) {
            Figure partyFigure = party.getProposedFigure();
            partyFigure.draw(paintBoard);
            paintBoard.drawString(":Class",
                    partyFigure.getX() + partyFigure.getWidth()/2 - paintBoard.getWidthForString(":Class")/2,
                    partyFigure.getY() + partyFigure.getHeight() + 15);
            paintBoard.drawLine(partyFigure.getX() + partyFigure.getWidth() / 2,
                    PARTY_ROW_HEIGHT,
                    partyFigure.getX() + partyFigure.getWidth() / 2,
                    paintBoard.getHeight());
            party.getProposedFigure().draw(paintBoard);
        }
        paintBoard.drawLine(0,PARTY_ROW_HEIGHT,paintBoard.getWidth(),PARTY_ROW_HEIGHT);
    }

    @Override
    public void addParty(Diagram diagram, Party party, int x, int y) throws InvalidAddException {
        if (y >= PARTY_ROW_HEIGHT)
            throw new InvalidAddException(null);
        for (Party p : coordinates.keySet())
            if (p.getProposedFigure().encloses(x,y))
                throw new InvalidAddException(p);
        coordinates = coordinates.plus(party, new Point(x,5));
    }

    @Override
    public void registerParty(Party party, int x, int y) {
        coordinates = coordinates.plus(party, new Point(x,5));
    }

    @Override
    public DiagramComponent selectableComponentAt(Diagram diagram, int x, int y) {
        return null;
    }

    @Override
    protected Point getDefaultCoordinate() {
        return new Point(5, 5);
    }

    @Override
    public String viewName() {
        return "Sequence View";
    }

    /**
     * The height of the party row.
     */
    private static int PARTY_ROW_HEIGHT = 75;

    /**
     * The height of the party row.
     */
    private static int MESSAGE_ROW_HEIGHT = 30;

}