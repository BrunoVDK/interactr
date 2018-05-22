package interactr.cs.kuleuven.be.ui.control.control;

import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.design.Colour;

import java.util.ArrayList;
import java.util.List;

/**
 * A class of list boxes.
 *
 * @author Team 25
 * @version 1.0
 */
public class ListBox extends Control {


    public ListBox(String[] arguments){
        this.arguments = arguments;
    }

    /**
     * The width and height of an argument box
     */
    private static final int width = 100, height = 200;

    @Override
    public void display(PaintBoard paintBoard, int x, int y) {
        paintBoard.drawRectangle(x,y,width,height);
        int offset = 0;

        for (int i = 0; i < arguments.length; i ++){
            if( i == selectedArgumentIndex)
                paintBoard.setColour(Colour.BLUE);

            paintBoard.drawRectangle(x,y,width,height);
            paintBoard.drawString(arguments[i],x + 5, y + offset - PaintBoard.charHeight);
            offset += argumentHeight;

            paintBoard.setColour(Colour.BLACK);

        }
    }


    /**
     * The index of the currently selected arcument
     */
    private int selectedArgumentIndex = 0;

    /**
     * A method that increases the selected argument index
     */
    public void nextArgument(){
        if(selectedArgumentIndex + 1 < arguments.length)
            selectedArgumentIndex += 1;
    }

    /**
     * A method that decreses the selescted argument index
     */
    public void previousArgument(){
        if(selectedArgumentIndex - 1 >= 0)
            selectedArgumentIndex -= 1;
    }

    /**
     * The height of an argument
     */
    private static final int argumentHeight = 20;

    /**
     * All the arguments that the listbox contains
     */
    private String[] arguments;

}