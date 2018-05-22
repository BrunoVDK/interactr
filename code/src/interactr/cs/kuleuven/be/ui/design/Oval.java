package interactr.cs.kuleuven.be.ui.design;

import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.geometry.Point;

/**
 * A class of ovals.
 *
 * @author Team 25
 * @version 1.0
 */
public class Oval extends Figure {

    /**
     * Initialize this new oval with given x & y coordines, width and height.
     *
     * @param x The x coordinate for this new oval.
     * @param y The y coordinate for this new oval.
     * @param width The width coordinate for this new oval.
     * @param height The height coordinate for this new oval.
     */
    Oval(int x, int y, int width, int height) {
        super(width, height);
        setCoordinates(new Point(x,y));
    }

    @Override
    public void draw(PaintBoard paintBoard) {
        super.draw(paintBoard);
        paintBoard.drawOval(getX(), getY(), getWidth(), getHeight());
    }

}