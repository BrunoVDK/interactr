package interactr.cs.kuleuven.be.ui.design;

import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

/**
 * A class of stick figures.
 *
 * @author Team 25
 * @version 1.0
 */
public class StickFigure extends Figure {

    /**
     * Registers default values for the width/height of stick figures.
     */
    private static int defaultWidth = 46, defaultHeight = 65;

    /**
     * Initialize this new stick figure with a width of 30,
     *  a height of 65 and an empty label.
     */
    public StickFigure() {
        super(defaultWidth, defaultHeight);
        int manHeight = 61 - PaintBoard.charHeight, headWidth = Math.min(defaultWidth, manHeight/2) - 5;
        add(new Circle((defaultWidth - headWidth)/2, 0, Math.min(45, manHeight/2) - 5));
        add(new Line(0, manHeight/2, defaultWidth, manHeight/2));
        add(new Line(defaultWidth/2, manHeight/2, defaultWidth/2, manHeight - defaultWidth/2));
        add(new Line(0, manHeight, defaultWidth/2, manHeight - defaultWidth/2));
        add(new Line(defaultWidth, manHeight, defaultWidth/2, manHeight - defaultWidth/2));
    }

    @Override
    public Rectangle getLabelBounds() {
        Rectangle bounds = super.getLabelBounds();
        bounds.setX(getX() + getWidth()/2 - bounds.getWidth()/2);
        bounds.setY(getY() + getHeight() - 4);
        return bounds;
    }

}