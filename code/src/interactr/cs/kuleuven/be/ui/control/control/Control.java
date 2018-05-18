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

    public abstract void displayControl(PaintBoard paintBoard, int x, int y, boolean active);

    public static final int margeOfLabelAndControl = 50;



}