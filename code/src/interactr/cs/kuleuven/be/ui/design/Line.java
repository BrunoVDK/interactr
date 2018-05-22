package interactr.cs.kuleuven.be.ui.design;

import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.geometry.Point;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

/**
 * A class of links.
 *
 * @author Team 25
 * @version 1.0
 */
public class Line extends Model {

    /**
     * Initialize this new link with end coordinates of (100,400).
     */
    Line() {
        this(false, 0, 0, 100, 400);
    }

    /**
     * Initialize this new link with given end coordinates.
     *
     * @param endX The end x coordinate for this new linke.
     * @param endY The end y coordinate for this new linke.
     */
    Line(int startX, int startY, int endX, int endY) {
        super("");
        setCoordinates(new Point(startX, startY));
        setEndCoordinates(new Point(endX, endY));
    }

    /**
     * Initialize this new link with given end coordinates.
     *
     * @param dashed A flag denoting whether or not this line is a dashed line.
     * @param endX The end x coordinate for this new linke.
     * @param endY The end y coordinate for this new linke.
     */
    private Line(boolean dashed, int startX, int startY, int endX, int endY) {
        this(startX, startY, endX, endY);
        setDashed(dashed);
    }

    /**
     * Sets whether or not this line is dashed.
     *
     * @param dashed True if the line is dashed.
     */
    void setDashed(boolean dashed) {
        this.dashed = dashed;
    }

    /**
     * Registers whether or not this line is a dashed line.
     */
    private boolean dashed = false;

    @Override
    public void setCoordinates(Point coordinates) {
        endCoordinates.setX(getEndCoordinates().getX() + (coordinates.getX() - getX()));
        endCoordinates.setY(getEndCoordinates().getY() + (coordinates.getY() - getY()));
        super.setCoordinates(coordinates);
    }

    /**
     * Returns the end coordinates of this link.
     */
    public Point getEndCoordinates() {
        return this.endCoordinates;
    }

    /**
     * Sets the end coordinates of this link to the given point.
     *
     * @param endCoordinates the new end coordinates for this link.
     */
    private void setEndCoordinates(Point endCoordinates) {
        this.endCoordinates = endCoordinates;
    }

    /**
     * Registers the coordinates where the link ends.
     */
    private Point endCoordinates = new Point(0,0);

    @Override
    public void draw(PaintBoard paintBoard) {
        super.draw(paintBoard);
        if (dashed) {
            int xNew, xOld, xLast;
            if (getY() > getEndCoordinates().getX()) {
                xOld= getEndCoordinates().getX();
                xLast = getX();
            }
            else {
                xOld = getX();
                xLast = getEndCoordinates().getX();
            }
            while(xOld + 10 < xLast){
                xNew = xOld + 10;
                paintBoard.drawLine(xOld, getY(), xNew, getY());
                xOld = xNew + 10;
            }
        }
        else
            paintBoard.drawLine(getX(), getY(), getEndCoordinates().getX(), getEndCoordinates().getY());
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), 0, 0);
    }

    @Override
    public Rectangle getLabelBounds() {
        Rectangle bounds = super.getLabelBounds();
        int startX = getCoordinates().getX(), endX = getEndCoordinates().getX();
        int startY = getCoordinates().getY(), endY = getEndCoordinates().getY();
        bounds.setX(startX + (endX - startX)/2 - bounds.getWidth()/2);
        bounds.setY(Math.min(startY, endY) + Math.abs(startY - endY)/2 - 3);
        return bounds;
    }

}