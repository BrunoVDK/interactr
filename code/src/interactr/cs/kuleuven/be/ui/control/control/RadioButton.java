package interactr.cs.kuleuven.be.ui.control.control;

import interactr.cs.kuleuven.be.ui.PaintBoard;

public class RadioButton extends Control{


    @Override
    public void displayControl(PaintBoard paintBoard, int x, int y, boolean active) {
        paintBoard.drawOval(x,y,diameter,diameter);
        if(active)
            paintBoard.fillOval(x +1 , y + 1, diameter -2, diameter -2);
    }



    private final int diameter = 10;
}
