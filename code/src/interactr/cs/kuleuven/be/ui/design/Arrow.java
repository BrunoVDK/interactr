package interactr.cs.kuleuven.be.ui.design;

import interactr.cs.kuleuven.be.ui.PaintBoard;

/**
 * A class of arrow links.
 *
 * @author Team 25
 * @version 1.0
 */
public class Arrow extends Line {

    /**
     * Initialize this new arrow with given flag.
     *
     * @param dashed True if this arrow should be dashed.
     */
    public Arrow(boolean dashed) {
        super();
        setDashed(dashed);
    }

    @Override
    public void draw(PaintBoard paintBoard) {
        super.draw(paintBoard);
        drawArrowHead(paintBoard);
    }

    /**
     * Draw the arrow head of this dashed arrow in the given paint board.
     *
     * @param paintBoard The paintboard in which to draw.
     */
    private void drawArrowHead(PaintBoard paintBoard) {
        int d = 5, endX = getEndCoordinates().getX(), endY = getEndCoordinates().getY(), xDif = endX - getX(), yDif = endY - getY();
        double l = Math.sqrt(Math.pow(xDif,2) + Math.pow(yDif , 2));
        double Cx = endX - (((xDif - (yDif)) / Math.sqrt(2)) * d/l);
        double Cy = endY - (((yDif + (xDif)) / Math.sqrt(2)) * d/l);
        double Dx = endX - (((xDif + (yDif)) / Math.sqrt(2)) * d/l);
        double Dy = endY - (((yDif - (xDif)) / Math.sqrt(2)) * d/l);
        paintBoard.drawLine((int)Math.floor(Cx), (int)Math.floor(Cy), endX, endY);
        paintBoard.drawLine((int)Math.floor(Dx), (int)Math.floor(Dy), endX, endY);
    }

}