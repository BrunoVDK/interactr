package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.DiagramComponent;
import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.exceptions.InvalidAddPartyException;
import interactr.cs.kuleuven.be.ui.geometry.Figure;

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

    /**
     * The override method of the display, the display for every view is different
     * @param paintBoard The paintboard to use when displaying the view.
     * @param diagram The diagram that is to be displayed in this view.
     * @param selectionManager
     */
    @Override
    public void display(PaintBoard paintBoard, Diagram diagram, SelectionManager selectionManager) {
        for (Party party : diagram.getParties()) {
            Figure partyFigure = figures.get(party);
            partyFigure.draw(paintBoard);
            paintBoard.drawLine(partyFigure.getX() + partyFigure.getWidth() / 2,
                    PARTY_ROW_HEIGHT,
                    partyFigure.getX() + partyFigure.getWidth() / 2,
                    paintBoard.getHeight());
        }
        paintBoard.drawLine(0, PARTY_ROW_HEIGHT, paintBoard.getWidth(), PARTY_ROW_HEIGHT);
    }

    /**
     * The override method of addParty from DiagramView
     * @param diagram The diagram the party is gonna be addded to.
     * @param party The party that is to be added.
     *
     * @param x The x coordinate of the new party.
     *
     * @param y The y coordinate of the new party.
     *             this parameter is not used,
     * @throws InvalidAddPartyException
     */
    @Override
    public void addParty(Diagram diagram, Party party, int x, int y) throws InvalidAddPartyException {
        if (y >= PARTY_ROW_HEIGHT)
            throw new InvalidAddPartyException(null);
        super.addParty(diagram, party, x, 5);
    }

    /**
     * A method to add a party that is already added to the active diagram
     * @param party The party to register.
     * @param x The x coordinate of the party that is to be registered.
     * @param y The y coordinate of the party that is to be registered.
     */
    @Override
    public void registerParty(Party party, int x, int y) {
        super.registerParty(party, x, 5);
    }

    //TODO
    @Override
    public DiagramComponent selectableComponentAt(Diagram diagram, int x, int y) {
        return null;
    }

    /**
     * Returnes the name of the view
     * @return "Sequence View"
     */
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

    /**
     * A mehtod that moves the given party to the given coordinates
     *
     * @param party The party that has te be moved
     * @param x The new absolute x coordinate of the given Party
     * @param y The new absolut y coordinate of the given Party
     */
    @Override
    public void moveParty(Party party, int x, int y){
        figures.get(party).setX(x);
    }

}