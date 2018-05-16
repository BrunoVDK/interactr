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



}