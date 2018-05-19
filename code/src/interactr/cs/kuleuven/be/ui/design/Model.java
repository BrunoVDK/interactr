package interactr.cs.kuleuven.be.ui.design;

import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

/**
 * A class of models for drawing. Each model has a label.
 *
 * @author Team 25
 * @version 1.0
 */
public class Model implements Cloneable {

    /**
     * Initialize this new model with given label.
     *
     * @param label The label to initialize this model with.
     * @throws IllegalArgumentException If the given label is null.
     */
    Model(String label) {
        if (label == null)
            throw new IllegalArgumentException("Null label given for new model.");
        setLabel(label);
    }

    /**
     * Checks whether or not the given coordinate 'hits' this model.
     *
     * @param x The x coordinate to check with.
     * @param y The y coordinate to check with.
     * @return True if and only if the given coordinate lies within the confines of this
     *  model's bounds.
     */
    public boolean isHit(int x, int y) {
        return isLabelHit(x, y);
    }

    /**
     * Checks whether the given coordinate 'hits' this model's label.
     *
     * @param x The x coordinate to check with.
     * @param y The y coordinate to check with.
     * @return True if and only if the given coordinate is enclosed by this model's label.
     */
    public boolean isLabelHit(int x, int y) {
        return getLabelBounds().enclosesNegative(x, y);
    }

    /**
     * Returns bounds of the label drawn in this model.
     *
     * @return The bounds of the label for this model.
     */
    public Rectangle getLabelBounds() {
        int length = 0;
        if (getLabel() != null)
            length = getLabel().length();
        if (length < 3) length = 3;
        return new Rectangle(0, 0, length * PaintBoard.charWidth, PaintBoard.charHeight);
    }

    /**
     * Returns the label of this model.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label of this model to the given value.
     *
     * @param label The new label value for this model.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Registers the label for this model.
     */
    private String label;

    /**
     * Sets the colour for this model to the given colour.
     *
     * @param colour The new colour for this model.
     */
    public void setColour(Colour colour) {
        this.colour = colour;
    }

    /**
     * The colour for this model.
     */
    private Colour colour = Colour.BLACK;

    /**
     * Draw this model in the given paintboard.
     *
     * @param paintBoard The paint board on which to draw.
     */
    public void draw(PaintBoard paintBoard) {
        paintBoard.setColour(this.colour);
        if (label != null)
            drawLabel(paintBoard);
    }

    /**
     * Draws this model's label in the given paintboard.
     *
     * @param paintBoard The board in which to draw the label.
     */
    void drawLabel(PaintBoard paintBoard) {
        Rectangle labelBounds = getLabelBounds();
        paintBoard.drawString(getLabel(), labelBounds.getX(), labelBounds.getY());
    }

    @Override
    public Model clone() {
        final Model clone;
        try {
            clone = (Model)super.clone();
        }
        catch (Exception e) {throw new RuntimeException("Failed to clone model.");}
        return clone;
    }

}