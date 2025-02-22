package interactr.cs.kuleuven.be.domain;

/**
 * A class of objects.
 *
 * @author Team 25
 * @version 1.0
 */
public class ObjectParty extends Party {

    /**
     * Initialize this new party with a default label.
     */
    public ObjectParty() {
        super();
    }

    /**
     * Initialize this new object party with the given party.
     *
     * @param party The party to initialize this object party with.
     */
    ObjectParty(Party party) {
        super(party);
    }

    /**
     * Initialize this new object with the given label.
     *
     * @param label The label for this new object.
     */
    public ObjectParty(String label) {
        super(label);
    }

    @Override
    public Party switchType(){
        return new ActorParty(this);
    }

    @Override
    public void acceptVisitor(DiagramVisitor visitor) {
        visitor.visit(this);
    }
}
