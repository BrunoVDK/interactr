package interactr.cs.kuleuven.be.ui.design;

import interactr.cs.kuleuven.be.ui.PaintBoard;

/**
 * A class of dashed arrow links.
 *
 * @author Team 25
 * @version 1.0
 */
public class DashedArrow extends Arrow {

    @Override
    protected void drawLink(PaintBoard paintBoard) {
        int y = getStartY(), xNew, xOld, xLast;
        if (getStartX() > getEndX()) {
            xOld= getEndX();
            xLast = getStartX();
        }
        else {
            xOld = getStartX();
            xLast = getEndX();
        }
        while(xOld + 10 < xLast){
            xNew = xOld + 10;
            paintBoard.drawLine(xOld,y,xNew,y);
            xOld = xNew + 10;
        }
    }

}
