package interactr.cs.kuleuven.be.ui.design;

import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.geometry.Point;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

/**
 * A class of boxes.
 *
 * @author Team 25
 * @version 1.0
 */
public class Box extends Figure {

    /**
     * Initialize
     */
    public Box() {
        super(60, 65);
    }

    /**
     * Initialize this new box with given coordinates and size.
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Box(int x, int y, int width , int height){
        super(width, height);
        this.setCoordinates(new Point(x,y));
    }

    @Override
    public void draw(PaintBoard paintBoard) {
        super.draw(paintBoard);
        paintBoard.drawRectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public Rectangle getLabelBounds() {
        Rectangle bounds = super.getLabelBounds();
        bounds.setX(getX() + getWidth()/2 - bounds.getWidth()/2);
        bounds.setY(getY() + getHeight()/2 + PaintBoard.charHeight/2);
        return bounds;
    }

}