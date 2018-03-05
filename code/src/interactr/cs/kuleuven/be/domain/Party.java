package interactr.cs.kuleuven.be.domain;

/**
 * A class of parties, having an x/y coordinate and a flag denoting whether
 *  or not the party is an actor.
 */
public class Party {

    /**
     * Initialize this new party with given x coordinate, y coordinate,
     *  and a flag denoting whether or not the new party is an actor.
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


    /**
     *
     * @param label
     * @return
     */
    public boolean canHaveAsLabel(Label label) {
        return label.getLength() <= 30
                && true;
    }



}