package interactr.cs.kuleuven.be.ui.geometry;

import interactr.cs.kuleuven.be.ui.PaintBoard;

/**
 * A class of boxes.
 *
 * @author Team 25
 * @version 1.0
 */
public class Box extends Figure {

    @Override
    public void draw(PaintBoard paintBoard) {
        paintBoard.drawRectangle(getX(), getY(), getWidth(), getHeight());
        super.drawLabel(paintBoard);
    }

    @Override
    public Rectangle getLabelBounds() {
        Rectangle bounds = super.getLabelBounds();
        bounds.setX(getX() + getWidth()/2 - bounds.getWidth()/2);
        bounds.setY(getY() + getHeight()/2 + PaintBoard.charHeight/2);
        return bounds;
    }

}
