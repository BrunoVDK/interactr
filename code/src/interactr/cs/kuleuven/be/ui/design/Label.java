package interactr.cs.kuleuven.be.ui.design;

import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.geometry.Point;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

/**
 * A class of labels to display text.
 *
 * @author Team 25
 * @version 1.0
 */
public class Label extends Model {

    /**
     * Initialize this new label with given text.
     *
     * @param x The new x coordinate for this label.
     * @param y The new y coordinate for this label.
     * @param text The text to initialize this label with.
     */
    public Label(int x, int y, String text) {
        super("");
        setCoordinates(new Point(x,y));
    }

    /**
     * Sets the maximum width for this label.
     *
     * @param maxWidth The maximum width for this label.
     */
    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    /**
     * Registers the maximum width for this label.
     */
    private int maxWidth = 100;

    @Override
    public Rectangle getBounds() {
        return getLabelBounds();
    }

    @Override
    public void draw(PaintBoard paintBoard) {
        super.draw(paintBoard);
        paintBoard.drawString(getLabel(), getX(), getY() + paintBoard.getHeightForString(getLabel()) - 1);
    }

}