package interactr.cs.kuleuven.be.ui.geometry;

import interactr.cs.kuleuven.be.ui.PaintBoard;

/**
 * A class of links.
 *
 * @author Team 25
 * @version 1.0
 */
public class Link {

    /**
     * Initialize this new figure with zero coordinates and a width and height of 20.
     */
    public Link() {
        this(0, 0, 20, 20);
    }

    /**
     * Initialize this new link with given start coordinate and end coordinate.
     *
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     */
    public Link(int startX, int startY, int endX, int endY) {
        setStartX(startX);
        setStartY(startY);
        setEndX(endX);
        setEndY(endY);
        setLabel("");
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

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    private int startX;

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    private int startY;

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    private int endX;

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

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