package interactr.cs.kuleuven.be.ui.geometry;

import interactr.cs.kuleuven.be.ui.PaintBoard;

/**
 * A class of links.
 *
 * @author Team 25
 * @version 1.0
 */
public class Link extends Model {

    /**
     * Initialize this new figure with zero coordinates and a width and height of 20.
     */
    public Link() {
        this(0, 0, 20, 20);
    }

    /**
     * Initialize this new link with given start coordinate and end coordinate.
     *
     * @param startX The start x coordinate for this new linke.
     * @param startY The start y coordinate for this new linke.
     * @param endX The end x coordinate for this new linke.
     * @param endY The end y coordinate for this new linke.
     */
    public Link(int startX, int startY, int endX, int endY) {
        super("");
        setStartX(startX);
        setStartY(startY);
        setEndX(endX);
        setEndY(endY);
    }

    /**
     * Draw this figure in the given paintboard.
     *
     * @param paintBoard The paint board on which to draw.
     */
    public void draw(PaintBoard paintBoard) {

    }

    /**
     * Checks whether or not the given coordinate 'hits' this figure.
     *
     * @param x The x coordinate to check with.
     * @param y The y coordinate to check with.
     * @return True if and only if the given coordinate lies within the confines of this
     *  figure's bounds.
     */
    public boolean isHit(int x, int y) {
        return isLabelHit(x, y);
    }

    /**
     * Returns the startX of this class
     * @return start X
     */
    public int getStartX() {
        return startX;
    }

    /**
     * Sets the startX of this class to the given int
     * @param startX the new x coordinate
     */
    public void setStartX(int startX) {
        this.startX = startX;
    }

    /**
     * The x coordinate where the links starts
     */
    private int startX;

    /**
     * Returns the startY of this class
     * @return startY
     */
    public int getStartY() {
        return startY;
    }

    /**
     * Sets the startY of this class to the given int
     * @param startY the new y coordinate
     */
    public void setStartY(int startY) {
        this.startY = startY;
    }

    /**
     * The y coordinate where the links starts
     */
    private int startY;

    /**
     * Returns the endX of this class
     * @return endX
     */
    public int getEndX() {
        return endX;
    }

    /**
     * Sets the endX of this class to the given int
     * @param endX the new x coordinate
     */
    public void setEndX(int endX) {
        this.endX = endX;
    }

    /**
     * The x coordinate where the link ends
     */
    private int endX;

    /**
     * Returns the endY of this class
     * @return endY
     */
    public int getEndY() {
        return endY;
    }

    /**
     * Sets the endY of this class to the given int
     * @param endY the new y coordinate
     */
    public void setEndY(int endY) {
        this.endY = endY;
    }

    /**
     * The x coordinate where the link ends
     */
    private int endY;

    /**
     * Checks whether the given coordinate 'hits' this figure's label.
     *
     * @param x The x coordinate to check with.
     * @param y The y coordinate to check with.
     * @return True if and only if the given coordinate is enclosed by this figure's label.
     */
    public boolean isLabelHit(int x, int y) {
        return false;
    }

    /**
     * Returns the label of this figure.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label of this figure to the given value.
     *
     * @param label The new label value for this figure.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Registers the label for this figure.
     */
    private String label;

}