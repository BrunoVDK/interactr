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
        this(100, 200, 300, 200);
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
        int startX = getStartX(), endY = getEndX();
        int startY = getStartY(), endX = getEndY();
        bounds.setY(Math.min(startY, endY) + Math.abs(startY - endY)/2 + 5); // Above link
        int boundsX = startX + Math.abs(startX - endX)/2 - bounds.getWidth()/2;
        int offset = bounds.getWidth()/2 + charHeight;
        if ((startY < endY && getStartX() < getEndX())
                || (startY > endY && getStartX() > getEndX())) {
            offset = -offset;
        }
        bounds.setX(boundsX + offset);
        return super.getLabelBounds();
    }

}