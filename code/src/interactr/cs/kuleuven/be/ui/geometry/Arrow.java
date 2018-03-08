package interactr.cs.kuleuven.be.ui.geometry;

import interactr.cs.kuleuven.be.ui.PaintBoard;

/**
 * A class of arrow links.
 *
 * @author Team 25
 * @version 1.0
 */
public class Arrow extends Link {

    @Override
    public void draw(PaintBoard paintBoard) {

        // Link
        paintBoard.drawLine(getStartX(), getStartY(), getEndX(), getEndY());

        // Arrow head
        paintBoard.drawLine(getEndX(), getEndY(), getEndX() - 5, getEndY());
        paintBoard.drawLine(getEndX(), getEndY(), getEndX(), getEndY() - 5);

    }

}
