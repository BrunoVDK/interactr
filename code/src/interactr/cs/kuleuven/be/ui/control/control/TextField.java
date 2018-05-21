package interactr.cs.kuleuven.be.ui.control.control;

import interactr.cs.kuleuven.be.ui.PaintBoard;

import interactr.cs.kuleuven.be.ui.design.Colour;

/**
 * A class of text fields.
 *
 * @author Team 25
 * @version 1.0
 */
public class TextField extends Control {

    public TextField(String labelComponent, String labelTextField){
        this.labelComponent = labelComponent;
        this.labelTextField = labelTextField;

    }

    @Override
    public void display(PaintBoard paintBoard, int x, int y) {
        if (isActive())
            paintBoard.setColour(Colour.BLUE);
        paintBoard.drawRectangle(x + margeOfLabelAndControl,y,width,paintBoard.charHeight);
        paintBoard.setColour(Colour.BLACK);
        paintBoard.drawString(labelComponent,x + margeOfLabelAndControl,paintBoard.charHeight + y);
        paintBoard.drawString(labelTextField,x,y + paintBoard.charHeight);
    }

    private static final int width = 80;

    /**
     * - labelComponent is the label of the component that is changeable
     * - labelTextField is the label of the textfield (not changeable)
     */
    private String labelComponent, labelTextField;

    /**
     * A method that sets the label to the given string
     * @param labelComponent
     *  The new label it should have
     */
    public void setLabelComponent(String labelComponent){
        this.labelComponent = labelComponent;
    }

}