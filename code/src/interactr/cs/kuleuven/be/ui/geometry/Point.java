package interactr.cs.kuleuven.be.ui.geometry;

/**
 * A class of points, having an x and an y coordinate.
 *
 * @author Team 25
 * @version 1.0
 */
public class Point {

    /**
     * Initialize this new point with given x & y coordinates.
     *
     * @param x The x coordinate for this new point.
     * @param y The y coordinate for this new point.
     */
    public Point(int x, int y) {
        setX(x);
        setY(y);
    }

    /**
     * Returns the x coordinate of this point.
     */
    public int getX() {
        return x;
    }

    /**
     * Set the x coordinate for this point to the given one.
     *
     * @param x The new x coordinate for this point.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the y coordinate of this point.
     */
    public int getY() {
        return y;
    }

    /**
     * Set the y coordinate for this point to the given one.
     *
     * @param y The new y coordinate for this point.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * The x coordinate for this point.
     */
    private int x;

    /**
     * The y coordinate for this point.
     */
    private int y;

}