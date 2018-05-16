package interactr.cs.kuleuven.be.ui.design;

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
        drawLink(paintBoard);
        drawArrowHead(paintBoard);
        super.draw(paintBoard);
    }

    /**
     * Draw the link of this dashed arrow in the given paint board.
     *
     * @param paintBoard The paintboard in which to draw.
     */
    protected void drawLink(PaintBoard paintBoard) {
        paintBoard.drawLine(getStartX(), getStartY(), getEndX(), getEndY());
    }

    /**
     * Draw the arrow head of this dashed arrow in the given paint board.
     *
     * @param paintBoard The paintboard in which to draw.
     */
    private void drawArrowHead(PaintBoard paintBoard) {
        int d = 5, xDif = getEndX() -getStartX(), yDif = getEndY() - getStartY();
        double l = Math.sqrt(Math.pow(xDif,2) + Math.pow(yDif , 2)), n = Math.tan(Math.PI/4);
        double Cx = getEndX() - (((xDif - ( n* yDif)) / Math.sqrt(1 + Math.pow(n,2))) * d/l);
        double Cy = getEndY() - (((yDif + ( n* xDif)) / Math.sqrt(1 + Math.pow(n,2))) * d/l);
        n *= -1;
        double Dx = getEndX() - (((xDif - ( n* yDif)) / Math.sqrt(1 + Math.pow(n,2))) * d/l);
        double Dy = getEndY() - (((yDif + ( n* xDif)) / Math.sqrt(1 + Math.pow(n,2))) * d/l);
        paintBoard.drawLine((int) Math.floor(Cx),(int) Math.floor(Cy),getEndX(),getEndY());
        paintBoard.drawLine((int) Math.floor(Dx),(int) Math.floor(Dy),getEndX(),getEndY());
    }

}
