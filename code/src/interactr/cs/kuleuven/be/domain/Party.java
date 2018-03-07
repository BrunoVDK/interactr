package interactr.cs.kuleuven.be.domain;

import interactr.cs.kuleuven.be.ui.geometry.Box;

/**
 * A class of parties.
 *
 * @author Team 25
 * @version 1.0
 */
public class Party extends DiagramComponent {

    /**
     * Initialize this new party with a label of ':Class'.
     */
    public Party() {
        super(":Class");
    }

    /**
     * Initialize this new party with the given party.
     *
     * @param party The party to initialize this party with.
     */
    public Party(Party party) {
        super(party.getLabel());
    }

    /**
     * Returns a proposal for how this component should be drawn, by returning a mock-up figure.
     *
     * @return A mock-up for how this component should be drawn.
     */
    public Class proposedFigure() {
        return Box.class;
    }

}