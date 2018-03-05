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
     * @param x The x coordinate for this new party.
     * @param y The y coordinate for this new party.
     */
    public Party(boolean isActor, int x, int y) {
        setIsActor(isActor);
        setX(x);
    }

    /**
     * Returns the x coordinate for this party.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Set the x coordinate of this party to the given one.
     *
     * @param x The new x coordinate for this party.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * The x coordinate of this party.
     */
    private int x;

    /**
     * Returns the y coordinate for this party.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Set the y coordinate of this party to the given one.
     *
     * @param y The new y coordinate for this party.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * The x coordinate of this party.
     */
    private int y;

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


    public boolean canHaveAsLabel(Label label) {
        return label.getLength() <= 30
                && true;
    }

}