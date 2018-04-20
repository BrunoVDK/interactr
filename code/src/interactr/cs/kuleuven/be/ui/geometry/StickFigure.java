package interactr.cs.kuleuven.be.ui.geometry;

import interactr.cs.kuleuven.be.ui.PaintBoard;

/**
 * A class of stick figures.
 *
 * @author Team 25
 * @version 1.0
 */
public class StickFigure extends Figure {

    /**
     * Initialize this new stick figure with zero coordinates, a width of 100,
     *  a height of 200 and an empty label.
     */
    public StickFigure() {
        super(0, 0, 30, 70);
    }

    @Override
    public void draw(PaintBoard paintBoard) {

        int manWidth = getWidth(), manHeight = getHeight() - PaintBoard.charHeight - 4;
        int headWidth = Math.min(getWidth(), manHeight/2) - 5;

        // Draw stick figure
        paintBoard.drawOval(getX() + (getWidth() - headWidth)/2,
                getY(),
                headWidth,
                headWidth);
        paintBoard.drawLine(getX(),
                getY() + manHeight/2,
                getX() + manWidth,
                getY() + manHeight/2);
        paintBoard.drawLine(getX() + manWidth/2,
                getY() + manHeight/2,
                getX() + manWidth/2,
                getY() + manHeight - manWidth/2);
        paintBoard.drawLine(getX(),
                getY() + manHeight,
                getX() + manWidth/2,
                getY() + manHeight - manWidth/2);
        paintBoard.drawLine(getX() + manWidth,
                getY() + manHeight,
                getX() + manWidth/2,
                getY() + manHeight - manWidth/2);

        super.drawLabel(paintBoard);

    }

    @Override
    public Rectangle getLabelBounds() {
        Rectangle bounds = super.getLabelBounds();
        bounds.setX(getX() + getWidth()/2 - bounds.getWidth()/2);
        bounds.setY(getY() + getHeight() - 4);
        return bounds;
    }

}