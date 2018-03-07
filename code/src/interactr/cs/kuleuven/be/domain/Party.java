package interactr.cs.kuleuven.be.domain;

/**
 * A class of parties, with a flag denoting whether
 *  or not the party is an actor.
 *
 * @author Team 25
 * @version 1.0
 */
public class Party extends DiagramComponent {

    /**
     * Initialize this new party with given flag denoting whether or not the new party is an actor.
     *
     * @param isActor Flag denoting whether or not the new party is an actor.
     */
    public Party(boolean isActor) {
        setIsActor(isActor);
    }

    /**
     * Returns whether or not this party is an actor.
     */
    public boolean isActor() {
        return this.isActor;
    }

    /**
     * Set whether or not this party is an actor.
     *
     * @param isActor True if this party should become an actor.
     */
    public void setIsActor(boolean isActor) {
        this.isActor = isActor;
    }

    /**
     * Flag denoting whether or not this party is an actor.
     */
    private boolean isActor;

}