package interactr.cs.kuleuven.be.ui.geometry;

import interactr.cs.kuleuven.be.ui.PaintBoard;

/**
 * A class of basic geometrical figures.
 *
 * @author Team 25
 * @version 1.0
 */
public class Figure {

    /**
     * Initialize this new figure with zero coordinates and a width and height of 20.
     */
    public Figure() {
        this(0, 0, 20, 20);
    }

    /**
     * Initialize this new figure with given coordinates and size.
     *
     * @param x The x coordinate for this new figure.
     * @param y The y coordinate for this new figure.
     * @param width The width for this new figure.
     * @param height The height for this new figure.
     */
    public Figure(int x, int y, int width, int height) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }

    /**
     * Draw this figure in the given paintboard.
     *
     * @param paintBoard The paint board on which to draw.
     */
    public void draw(PaintBoard paintBoard) {

    }

    /**
     * Checks whether or not this figure encloses the given coordinate.
     *
     * @param x The x coordinate to check with.
     * @param y The y coordinate to check with.
     * @return True if and only if the given coordinate lies within the confines of this
     *  figure's bounds.
     */
    public boolean encloses(int x, int y) {
        return (getX() <= x
                && getY() <= y
                && x <= getX() + getWidth()
                && y <= getY() + getHeight());
    }

    /**
     * Get the x coordinate of this figure.
     *
     * @return The x coordinate of this figure.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Set the x coordinate for this figure to the given one.
     *
     * @param x The new x coordinate for this figure.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * The x coordinate of this figure.
     */
    protected int x;

    /**
     * Get the y coordinate of this figure.
     *
     * @return The y coordinate of this figure.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Set the y coordinate for this figure to the given one.
     *
     * @param y The new y coordinate for this figure.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * The x coordinate of this figure.
     */
    protected int y;

    /**
     * Get the width of this figure.
     *
     * @return The width of this figure.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Set the width for this figure to the given one.
     *
     * @param width The new width for this figure.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * The width of this figure.
     */
    protected int width;

    /**
     * Get the height of this figure.
     *
     * @return The height of this figure.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Set the height for this figure to the given one.
     *
     * @param height The new height for this figure.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * The height of this figure.
     */
    protected int height;

    /**
     * Returns the z-index for this figure.
     */
    public int getZIndex() {
        return this.zIndex;
    }

    /**
     * Set the z-index for this figure to the given index.
     *
     * @param zIndex The new z-index for this figure.
     */
    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    /**
     * Variable registering the z index for this figure.
     */
    protected int zIndex;

}