package interactr.cs.kuleuven.be.ui.geometry;

/**
 * A class of figures having a label.
 *
 * @author Team 25
 * @version 1.0
 */
public class LabeledFigure extends Figure {

    /**
     * Initialize this new labeled figure with zero coordinates, a width of 100,
     *  a height of 200 and an empty label.
     */
    public LabeledFigure() {
        this(0, 0, 100, 200, "");
    }

    /**
     * Initialize this new figure with given coordinates, size and label.
     *
     * @param x The x coordinate for this new figure.
     * @param y The y coordinate for this new figure.
     * @param width The width for this new figure.
     * @param height The height for this new figure.
     * @param label The string value for this figure.
     */
    public LabeledFigure(int x, int y, int width, int height, String label) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }

    /**
     * Checks whether the given coordinate is enclosed by this figure's label.
     *
     * @param x The x coordinate to check with.
     * @param y The y coordinate to check with.
     * @return True if and only if the given coordinate is enclosed by this figure's label.
     */
    public boolean labelEncloses(int x, int y) {
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