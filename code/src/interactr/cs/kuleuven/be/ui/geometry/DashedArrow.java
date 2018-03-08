package interactr.cs.kuleuven.be.ui.geometry;

import interactr.cs.kuleuven.be.ui.PaintBoard;

/**
 * A class of dashed arrow links.
 *
 * @author Team 25
 * @version 1.0
 */
public class DashedArrow extends Link {

    @Override
    public void draw(PaintBoard paintBoard) {

        paintBoard.drawLine(getStartX(), getStartY(), getEndX(), getEndY());
        paintBoard.drawLine(getEndX(), getEndY(), getEndX() + (getEndX() > getStartX() ? 5 : -5), getEndY());
        paintBoard.drawLine(getEndX(), getEndY(), getEndX(), getEndY() + (getEndY() > getStartY() ? 5 : -5));

    }

}
