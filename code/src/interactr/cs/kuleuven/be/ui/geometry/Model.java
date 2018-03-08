package interactr.cs.kuleuven.be.ui.geometry;

import interactr.cs.kuleuven.be.ui.PaintBoard;

/**
 * A class of basic geometrical figures.
 *
 * @author Team 25
 * @version 1.0
 */
public abstract class Model {

    /**
     * Returns the char height used for labels for models.
     */
    public static int charHeight = 12;

    /**
     * Returns the char width used for labels for models.
     */
    public static int charWidth = 6;

    /**
     * Initialize this new model with given label.
     *
     * @param label The label to initialize this model with.
     * @throws IllegalArgumentException If the given label is null.
     */
    public Model(String label) {
        if (label == null)
            throw new IllegalArgumentException("Null label given for new model.");
        setLabel(label);
    }

    /**
     * Checks whether the given coordinate 'hits' this figure's label.
     *
     * @param x The x coordinate to check with.
     * @param y The y coordinate to check with.
     * @return True if and only if the given coordinate is enclosed by this figure's label.
     */
    public boolean isLabelHit(int x, int y) {
        return getLabelBounds().encloses(x, y);
    }

    /**
     * Returns bounds of the label drawn in this model.
     *
     * @return The bounds of the label for this model.
     */
    public Rectangle getLabelBounds() {
        return new Rectangle(0, 0, getLabel().length() * charWidth, charHeight);
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

    /**
     * Draw this figure in the given paintboard.
     *
     * @param paintBoard The paint board on which to draw.
     */
    public void draw(PaintBoard paintBoard) {

    }

}