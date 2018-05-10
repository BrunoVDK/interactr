package interactr.cs.kuleuven.be.ui.geometry;

/**
 * A class of rectangles, having an x and an y coordinate, a width and a height.
 *
 * @author Team 25
 * @version 1.0
 */
public class Rectangle {

    /**
     * Initialize this new rectangle with given x and y coordinates and given width and height..
     *
     * @param x The x coordinate for this new rectangle.
     * @param y The y coordinate for this new rectangle.
     * @param width The width for this new rectangle.
     * @param height The height for this new rectangle.
     */
    public Rectangle(int x, int y, int width, int height) {
        if (width < 0)
            throw new IllegalArgumentException("Negative width for rectangle is invalid.");
        if (height < 0)
            throw new IllegalArgumentException("Negative height for rectangle is invalid.");
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }

    /**
     * Initialize this new rectangle with the given rectangle.
     *
     * @param rectangle The rectangle to initialize this rectangle with.
     */
    public Rectangle(Rectangle rectangle) {
        this(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
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
     * The x coordinate for this point.
     */
    private int x;

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
     * The y coordinate for this point.
     */
    private int y;

    /**
     * Returns the width of this rectangle.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of this rectangle to the given width.
     *
     * @param width The width of this rectangle.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Registers the width of this rectangle.
     */
    private int width;

    /**
     * Returns the height of this rectangle.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of this rectangle to the given width.
     *
     * @param height The height of this rectangle.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Registers the height of this rectangle.
     */
    private int height;

    /**
     * Checks whether the given rectangle overlaps with this rectangle.
     *
     * @param  	other
     *         	The rectangle to check with.
     * @return 	True if and only if the given rectangle overlaps this rectangle.
     */
    public boolean overlaps(Rectangle other) {
        return (other.getX() + other.getWidth() >= getX()
                && other.getX() <= getX() + getWidth()
                && other.getY() + other.getHeight() >= getY()
                && other.getHeight() <= getY() + getHeight());
    }

    /**
     * Checks whether or not this rectangle encloses the given coordinate.
     *
     * @param x The x coordinate to check for.
     * @param y The y coordinate to check for.
     * @return True if and only if the given coordinate is inside this rectangle.
     */
    public boolean encloses(int x, int y) {
        return (x >= getX()
                && x <= getX() + getWidth()
                && y >= getY()
                && y <= getY() + getHeight());
    }

    /**
     * Checks whether or not this rectangle encloses the given coordinate (swapped y axis direction).
     *
     * @param x The x coordinate to check for.
     * @param y The y coordinate to check for.
     * @return True if and only if the given coordinate is inside this rectangle.
     */
    public boolean enclosesNegative(int x, int y) {
        return (x >= getX()
                && x <= getX() + getWidth()
                && y >= getY() - getHeight()
                && y <= getY());
    }

    @Override
    public String toString() {
        return "[" + getX() + " " + getY() + " " + getWidth() + " " + getHeight() + "]";
    }

}