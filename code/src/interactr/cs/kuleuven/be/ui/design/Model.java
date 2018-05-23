package interactr.cs.kuleuven.be.ui.design;

import interactr.cs.kuleuven.be.purecollections.PList;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.geometry.Point;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

/**
 * A class of models for drawing. Each model has a label.
 *
 * @author Team 25
 * @version 1.0
 */
public abstract class Model {

    /**
     * Initialize this new model with given label.
     *
     * @param label The label to initialize this model with.
     */
    Model(String label) {
        setLabel(label);
    }

    /**
     * Returns the x coordinate of this model.
     */
    public int getX() {
        return coordinates.getX();
    }

    /**
     * Returns the y coordinate of this model.
     */
    public int getY() {
        return coordinates.getY();
    }

    /**
     * Returns the (start) coordinates of this line.
     */
    public Point getCoordinates() {
        return coordinates;
    }

    /**
     * Sets the coordinates for this model to the given one.
     *
     * @param coordinates The new coordinates for this model.
     */
    public void setCoordinates(Point coordinates) {
        for (Model child : children)
            child.setCoordinates(new Point(
                    child.coordinates.getX() - this.coordinates.getX() + coordinates.getX(),
                    child.coordinates.getY() - this.coordinates.getY() + coordinates.getY()));
        this.coordinates = coordinates;
    }

    /**
     * Registers the coordinates at which this model should be drawn.
     */
    protected Point coordinates = new Point(0, 0);

    /**
     * Checks whether or not the given coordinate 'hits' this model.
     *
     * @param x The x coordinate to check with.
     * @param y The y coordinate to check with.
     * @return True if and only if the given coordinate lies within the confines of this
     *  model's bounds.
     */
    public boolean isHit(int x, int y) {
        return getBounds().encloses(x,y);
    }

    /**
     * Returns the bounds of this model.
     *
     * @return A rectangle, the bounding box of this model (not considering its label).
     */
    public abstract Rectangle getBounds();

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
        return new Rectangle(getX(), getY(), length * PaintBoard.charWidth, PaintBoard.charHeight);
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
        for (Model child : children)
            child.setColour(colour);
    }

    /**
     * The colour for this model.
     */
    private Colour colour = Colour.BLACK;

    /**
     * Draw this model in the given paintboard at the given coordinates.
     *
     * @param paintBoard The paint board on which to draw.
     */
    public void draw(PaintBoard paintBoard) {
        paintBoard.setColour(this.colour);
        if (label != null)
            drawLabel(paintBoard);
        for (Model child : children)
            child.draw(paintBoard);
    }

    /**
     * Draws this model's label in the given paintboard.
     *
     * @param paintBoard The board in which to draw the label.
     */
    protected void drawLabel(PaintBoard paintBoard) {
        paintBoard.drawString(getLabel(), getLabelBounds().getX(), getLabelBounds().getY());
    }

    /**
     * Add the given model to this model's list of models.
     *  The coordinates of the model are interpreted as local coordinates (in reference to this model's coordinate system).
     *
     * @param model The model to be added.
     */
    public void add(Model model) {
        children = children.plus(model);
        model.setCoordinates(new Point(model.coordinates.getX() + getX(), model.coordinates.getY() + getY()));
    }

    /**
     * Registers the children of this model.
     */
    private PList<Model> children = PList.empty();

}