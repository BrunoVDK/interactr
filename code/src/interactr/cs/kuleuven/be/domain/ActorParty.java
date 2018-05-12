package interactr.cs.kuleuven.be.domain;

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
    ActorParty(Party party) {
        super(party);
    }

    @Override
    public Party switchType(){
        return new ObjectParty(this);
    }

    @Override
    public void acceptVisitor(DiagramVisitor visitor) {
        visitor.visit(this);
    }
}