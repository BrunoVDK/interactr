package interactr.cs.kuleuven.be.ui.control.control;

import interactr.cs.kuleuven.be.ui.PaintBoard;

public class ButtonMinus extends Control {

    @Override
    public void display(PaintBoard paintBoard, int x, int y) {
        paintBoard.drawRectangle(x, y, side,side);
        paintBoard.drawLine(x + side/5 ,y + side/2, x +(side * 4/5), y + side/2);

    }

    /**
     * The side size of a square buttonDown
     */
    private static final int side = 10;
}
