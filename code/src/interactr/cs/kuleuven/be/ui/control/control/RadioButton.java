package interactr.cs.kuleuven.be.ui.control.control;

import interactr.cs.kuleuven.be.ui.PaintBoard;

/**
 * A class of radio buttons.
 *
 * @author Team 25
 * @version 1.0
 */
public class RadioButton extends Control {

    /**
     * Initialize this new radio button with given label.
     *
     * @param label The label to initialize the radio button with.
     */
    public RadioButton(String label) {
        this.label = label;
    }

    @Override
    public void display(PaintBoard paintBoard, int x, int y) {
        paintBoard.drawString(label, x + 50, y);
        paintBoard.drawOval(x + 15, y + 2 - PaintBoard.charHeight, getDiameter(), getDiameter());
        if (isActive())
            paintBoard.fillOval(x + 16 , y + 3 - PaintBoard.charHeight, getDiameter() - 2, getDiameter() - 2);
    }

    /**
     * The label of the button itself, the text it shows before the button
     */
    private String label;

    /**
     * Returns the diameter of the radio button
     */
    private int getDiameter() {
        return 10;
    }

}
