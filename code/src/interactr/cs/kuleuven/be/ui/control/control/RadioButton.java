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


    private String label;

    @Override
    public void display(PaintBoard paintBoard, int x, int y) {

    }

}
