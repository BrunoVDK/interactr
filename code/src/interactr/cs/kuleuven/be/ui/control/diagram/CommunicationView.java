package interactr.cs.kuleuven.be.ui.control.diagram;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

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
     * Initialize this new diagram view with the given diagram and given frame.
     *
     * @param diagram The diagram to associate this diagram view with.
     * @param frame The frame for this new view.
     * @throws IllegalArgumentException If the given diagram is null.
     */
    public CommunicationView(Diagram diagram, Rectangle frame) {
        super(diagram, frame);
    }

    @Override
    public String toString() {
        return super.toString() + " - Communication View";
    }

}