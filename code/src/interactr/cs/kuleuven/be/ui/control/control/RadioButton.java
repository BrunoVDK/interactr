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
        paintBoard.drawString(label, x,PaintBoard.charHeight + y);
        paintBoard.drawOval(x + margeOfLabelAndControl, y, diameter, diameter);
        if(isActive())
            paintBoard.fillOval(x + margeOfLabelAndControl + 1 , y + 1, diameter -2, diameter -2);
    }

    /**
     * The label of the button itself, the text it shows before the button
     */
    private String label;

    /**
     * The diameter of the radio button
     */
    private final int diameter = 10;

}
