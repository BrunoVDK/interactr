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
        this(50, 200, 100, 400);
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

    @Override
    public boolean isHit(int x, int y) {
        return isLabelHit(x, y);
    }

    /**
     * Returns the start x coordinate of this link.
     */
    public int getStartX() {
        return startX;
    }

    /**
     * Sets the start x coordinate of this link to the given int.
     *
     * @param startX the new start x coordinate for this link.
     */
    public void setStartX(int startX) {
        this.startX = startX;
    }

    /**
     * Registers the x coordinate where the link starts.
     */
    private int startX;

    /**
     * Returns the start y coordinate of this link.
     */
    public int getStartY() {
        return startY;
    }

    /**
     * Sets the start y coordinate of this link to the given int.
     *
     * @param startY the new start y coordinate for this link.
     */
    public void setStartY(int startY) {
        this.startY = startY;
    }

    /**
     * Registers the y coordinate where the link starts.
     */
    private int startY;

    /**
     * Returns the end x coordinate of this link.
     */
    public int getEndX() {
        return endX;
    }

    /**
     * Sets the end x coordinate of this link to the given int.
     *
     * @param endX the new end x coordinate for this link.
     */
    public void setEndX(int endX) {
        this.endX = endX;
    }

    /**
     * Registers the x coordinate where the link ends.
     */
    private int endX;

    /**
     * Returns the end y coordinate of this link.
     */
    public int getEndY() {
        return endY;
    }

    /**
     * Sets the end y coordinate of this link to the given int.
     *
     * @param endY the new end y coordinate for this link.
     */
    public void setEndY(int endY) {
        this.endY = endY;
    }

    /**
     * Registers the y coordinate where the link ends.
     */
    private int endY;

    @Override
    public Rectangle getLabelBounds() {
        Rectangle bounds = super.getLabelBounds();
        int startX = getStartX(), endX = getEndX();
        int startY = getStartY(), endY = getEndY();
        bounds.setX(startX + (endX - startX)/2 - bounds.getWidth()/2);
        bounds.setY(Math.min(startY, endY) + Math.abs(startY - endY)/2 - 3);
        return bounds;
    }

    /**
     * Checks whether this link crosses the given one.
     *
     * @param other The other link to check with.
     * @return True if and only if this link crosses the given one.
     */
    public boolean crosses(Link other) {
        int o1 = orientation(getStartX(), getStartY(), getEndX(), getEndY(), other.getStartX(), other.getStartY());
        int o2 = orientation(getStartX(), getStartY(), getEndX(), getEndY(), other.getEndX(), other.getEndY());
        int o3 = orientation(other.getStartX(), other.getStartY(), other.getEndX(), other.getEndY(), getStartX(), getStartY());
        int o4 = orientation(other.getStartX(), other.getStartY(), other.getEndX(), other.getEndY(), getEndX(), getEndY());
        if (o1 != o2 && o3 != o4)
            return true;
        return false;
    }

    /**
     * Convenience method for checking for line intersections.
     *  From https://stackoverflow.com/questions/25830932/how-to-find-if-two-line-segments-intersect-or-not-in-java
     */
    private static int orientation(int x1, int y1, int x2, int y2, int x3, int y3) {
        double val = (y2 - y1) * (x3 - x2) - (x2 - x1) * (y3 - y2);
        if (val == 0.0)
            return 0;
        return (val > 0 ? 1 : 2);
    }

}