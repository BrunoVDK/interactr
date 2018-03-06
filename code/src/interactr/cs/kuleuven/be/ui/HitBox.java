package interactr.cs.kuleuven.be.ui;

/**
 * A class of boxes having a minimum x, maximum x, minimum y and maximum y coordinate.
 */
public class HitBox {


    public final static int width = 100;

    public final static int heigth = 50;

    /**
     * Initialize this new box with given minimum x coordinate, maximum x coordinate,
     *  minimum y coordinate and maximum y coordinate.
     *
     * @param xMin The minimum x coordinate for this new box.
     * @param yMin The minimum y coordinate for this new box.
     */
    public HitBox(int xMin, int yMin) {
        this.xMin = xMin;
        this.yMin = yMin;

    }


    /**
     * The minimum x coordinate for this box.
     */
    private final int xMin;

    public int getxMin() {
        return xMin;
    }

    public int getyMin() {
        return yMin;
    }
    /**
     * The minimum y coordinate for this box.
     */
    private final int yMin;


    /**
     * A method that checks if the given point is enclosed by this box
     *
     * @param x The x coordinate of the point to check with.
     * @param y The x coordinate of the point to check with.
     * @return True if and only if this box encloses the given point.
     */
    public boolean encloses(int x, int y){
        return (x > xMin && x < xMin+width && y < yMin + heigth && y > yMin);
    }

}
