package interactr.cs.kuleuven.be.ui.control.control;

import interactr.cs.kuleuven.be.ui.PaintBoard;

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
            paintBoard.drawRectangle(x,y,width,height);
            paintBoard.drawString(arguments[i],x + 5, y + offset - PaintBoard.charHeight);
            offset += argumentHeight;

        }
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