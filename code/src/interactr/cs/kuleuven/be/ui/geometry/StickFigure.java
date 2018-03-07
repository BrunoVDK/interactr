package interactr.cs.kuleuven.be.ui.geometry;

import interactr.cs.kuleuven.be.ui.PaintBoard;

/**
 * A class of stick figures.
 *
 * @author Team 25
 * @version 1.0
 */
public class StickFigure extends LabeledFigure {

    @Override
    public void draw(PaintBoard paintBoard) {
        int headWidth = Math.min(getWidth(), getHeight()/2);
        paintBoard.drawOval(getX(),
                getY(),
                headWidth,
                headWidth);
        paintBoard.drawLine(getX(),
                getY() + getHeight()/2,
                getX() + getWidth(),
                getY() + getHeight()/2);
        paintBoard.drawLine(getX() + getWidth()/2,
                getY() + getHeight()/2,
                getX() + getWidth()/2,
                getY() + getHeight() - getWidth()/2);
        paintBoard.drawLine(getX(),
                getY() + getHeight(),
                getX() + getWidth()/2,
                getY() + getHeight() - getWidth()/2);
        paintBoard.drawLine(getX() + getWidth(),
                getY() + getHeight(),
                getX() + getWidth()/2,
                getY() + getHeight() - getWidth()/2);
    }

}