package interactr.cs.kuleuven.be.ui.geometry;

import interactr.cs.kuleuven.be.ui.PaintBoard;

/**
 * A class of boxes.
 *
 * @author Team 25
 * @version 1.0
 */
public class Box extends LabeledFigure {

    @Override
    public void draw(PaintBoard paintBoard) {
        paintBoard.drawRectangle(getX(), getY(), getWidth(), getHeight());
    }

}
