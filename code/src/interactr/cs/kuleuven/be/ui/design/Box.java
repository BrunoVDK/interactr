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

    public Box(){

    }

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