package interactr.cs.kuleuven.be.exceptions;

/**
 * A class of exceptions denoting that a given coordinate does not have a party.
 *
 * @author Team 25
 * @version 1.0
 */
public class NoSuchPartyException extends RuntimeException {

    /**
     * Initialize this new exception with given x and y coordinate.
     *
     * @param x The x coordinate for this exception.
     * @param y The y coordinate for this exception.
     */
    public NoSuchPartyException(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x coordinate associated with this exception.
     */
    public int getX() {
        return this.x;
    }

    /**
     * The x coordinate associated with this exception.
     */
    private int x;

    /**
     * Returns the y coordinate associated with this exception.
     */
    public int getY() {
        return this.y;
    }

    /**
     * The y coordinate associated with this exception.
     */
    private int y;

}