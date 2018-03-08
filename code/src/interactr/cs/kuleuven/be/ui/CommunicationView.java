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

    /**
     * The override method of the display, the display for every view is different
     *
     * @param paintBoard The paintboard to use when displaying the view.
     * @param diagram The diagram that is to be displayed in this view.
     * @param selectionManager
     */
    @Override
    public void display(PaintBoard paintBoard, Diagram diagram,SelectionManager selectionManager) {
        for (Party party : diagram.getParties()) {
            Figure partyFigure = figures.get(party);
            partyFigure.draw(paintBoard);
        }
    }

    /**
     * A method that returns  diagram component at the given coordinate
     * @param diagram The diagram whose components are to be considered.
     * @param x The x coordinate to look at.
     * @param y The y coordinate to look at.
     * @return
     */
    //TODO
    @Override
    public DiagramComponent selectableComponentAt(Diagram diagram, int x, int y) {
        return null;
    }

    /**
     * Returns the name of the view
     * @return "Communication View"
     */
    @Override
    public String viewName() {
        return "Communication View";
    }

}