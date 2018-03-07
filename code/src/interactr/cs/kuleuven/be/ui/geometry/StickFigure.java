package interactr.cs.kuleuven.be.ui.geometry;

import interactr.cs.kuleuven.be.ui.PaintBoard;

/**
 * A class of stick figures.
 *
 * @author Team 25
 * @version 1.0
 */
public class StickFigure extends LabeledFigure {

    /**
     * Initialize this new stick figure with zero coordinates, a width of 100,
     *  a height of 200 and an empty label.
     */
    public StickFigure() {
        super(0, 0, 30, 70, ":Class");
    }

    @Override
    public void draw(PaintBoard paintBoard) {

        int charHeight = paintBoard.getHeightOfChars();
        int manWidth = getWidth(), manHeight = getHeight() - charHeight - 4;
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

        // Draw label
        int widthOfLabel = paintBoard.getWidthForString(getLabel());
        paintBoard.drawString(getLabel(), getX() + manWidth/2 - widthOfLabel/2, getY() + manHeight + charHeight + 2);

    }

}