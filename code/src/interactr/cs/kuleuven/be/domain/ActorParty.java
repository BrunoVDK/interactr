package interactr.cs.kuleuven.be.domain;

import interactr.cs.kuleuven.be.ui.geometry.StickFigure;

/**
 * A class of actors.
 *
 * @author Team 25
 * @version 1.0
 */
public class ActorParty extends Party {

    /**
     * Initialize this new party with a label of ':Class'.
     */
    public ActorParty() {
        super();
    }

    /**
     * Initialize this new actor party with the given party.
     *
     * @param party The party to initialize this actor party with.
     */
    public ActorParty(Party party) {

    }

    public Class proposedFigure() {
        return StickFigure.class;
    }

}
