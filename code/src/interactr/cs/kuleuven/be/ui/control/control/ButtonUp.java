package interactr.cs.kuleuven.be.ui.control.control;

import interactr.cs.kuleuven.be.ui.PaintBoard;

public class ButtonUp extends Control {

    @Override
    public void display(PaintBoard paintBoard, int x, int y) {
        paintBoard.drawRectangle(x, y, side,side);
        paintBoard.drawLine(x + side/2 ,y + side/5, x +side/2, y + side * 4/5);
        drawArrowHead(paintBoard,x + side/2, x + side/2 ,y + (side * 4/5), y + (side * 1/5));
    }


    /**
     * The side size of a square buttonDown
     */
    private static final int side = 10;
}
