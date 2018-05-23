package interactr.cs.kuleuven.be.ui.design;

import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

/**
 * A class of basic geometrical figures.
 *
 * @author Team 25
 * @version 1.0
 */
public class Figure extends Model {

    /**
     * Initialize this new figure with given coordinates and size, and an empty label.
     *
     * @param width The width for this new figure.
     * @param height The height for this new figure.
     */
    Figure(int width, int height) {
        super("");
        setWidth(width);
        setHeight(height);
    }

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
    protected void setWidth(int width) {
        this.width = width;
    }

    /**
     * The width of this figure.
     */
    private int width;

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
    protected void setHeight(int height) {
        this.height = height;
    }

    /**
     * The height of this figure.
     */
    private int height;

    /**
     * Returns the bounds of this figure.
     *
     * @return A rectangle representing the bounds of this figure.
     */
    public Rectangle getBounds() {
        return new Rectangle(coordinates.getX(), coordinates.getY(), getWidth(), getHeight());
    }

}