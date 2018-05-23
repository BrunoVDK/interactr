package interactr.cs.kuleuven.be.ui.control.control;

import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

/**
 * A class of controls for use in a dialog.
 *
 * @author Team 25
 * @version 1.0:
 */
public abstract class Control {

    /**
     * Display this control in the given paintboard, at the given coordinates.
     *
     * @param paintBoard The paintboard to draw on.
     * @param x The x coordinate for the control
     * @param y The y coordinate for the control
     */
    public abstract void display(PaintBoard paintBoard, int x, int y);

    public static final int margeOfLabelAndControl = 100;

    /**
     * A getter for the variable isActive
     * @return
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * A setter fot the variable is Active.
     * @param active
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * A boolean that is true when this control is active
     *  Initializes on false
     */
    private boolean isActive = false;

    /**
     * The method to draw an arrowHead
     * @param paintBoard
     * @param startX
     * @param endX
     * @param startY
     * @param endY
     */
    public void drawArrowHead(PaintBoard paintBoard, int startX,  int endX,  int startY,  int endY) {
        int d = 5, xDif = endX - startX, yDif = endY - startY;
        double l = Math.sqrt(Math.pow(xDif,2) + Math.pow(yDif , 2)), n = Math.tan(Math.PI/4);
        double Cx = endX - (((xDif - ( n* yDif)) / Math.sqrt(1 + Math.pow(n,2))) * d/l);
        double Cy = endY - (((yDif + ( n* xDif)) / Math.sqrt(1 + Math.pow(n,2))) * d/l);
        n *= -1;
        double Dx = endX - (((xDif - ( n* yDif)) / Math.sqrt(1 + Math.pow(n,2))) * d/l);
        double Dy = endY - (((yDif + ( n* xDif)) / Math.sqrt(1 + Math.pow(n,2))) * d/l);
        paintBoard.drawLine((int) Math.floor(Cx),(int) Math.floor(Cy),endX,endY);
        paintBoard.drawLine((int) Math.floor(Dx),(int) Math.floor(Dy),endX,endY);
    }



}