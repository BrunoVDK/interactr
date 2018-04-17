package interactr.cs.kuleuven.be.domain;

import interactr.cs.kuleuven.be.ui.geometry.Figure;
import interactr.cs.kuleuven.be.ui.geometry.StickFigure;

/**
 * A class of actors.
 *
 * @author Team 25
 * @version 1.0
 */
public class ActorParty extends Party {

    /**
     * Initialize this new party with a default label.
     */
    public ActorParty() {
        super();
    }

    /**
     * Initialize this new actor with the given label.
     *
     * @param label The label for this new actor.
     */
    public ActorParty(String label) {
        super(label);
    }

    /**
     * Initialize this new actor party with the given party.
     *
     * @param party The party to initialize this actor party with.
     */
    public ActorParty(Party party) {
        super(party);
    }


    public Party switchType(){
        return new ObjectParty(this);
    }

    /**
     * A proposed type of figures for drawing this actor.
     */
    public Figure proposedFigure() {
        return new StickFigure();
    }

}