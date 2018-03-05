package interactr.cs.kuleuven.be.ui;

/**
 * A class of boxes having a minimum x, maximum x, minimum y and maximum y coordinate.
 */
public class Box {

    /**
     * Initialize this new box with given minimum x coordinate, maximum x coordinate,
     *  minimum y coordinate and maximum y coordinate.
     *
     * @param xMin The minimum x coordinate for this new box.
     * @param xMax The maximum x coordinate for this new box.
     * @param yMin The minimum y coordinate for this new box.
     * @param yMax The maximum y coordinate for this new box.
     */
    public Box(int xMin, int xMax, int yMin, int yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }

    /**
     * The minimum x coordinate for this box.
     */
    private final int xMin;

    /**
     * The maximum x coordinate for this box.
     */
    private final int xMax;

    /**
     * The minimum y coordinate for this box.
     */
    private final int yMin;

    /**
     * The maximum y coordinate for this box.
     */
    private final int yMax;

    /**
     * A method that checks if the given point is enclosed by this box
     *
     * @param x The x coordinate of the point to check with.
     * @param y The x coordinate of the point to check with.
     * @return True if and only if this box encloses the given point.
     */
    public boolean encloses(int x, int y){
        return (x > xMin && x < xMax && y > yMin && y < yMax);
    }


}
