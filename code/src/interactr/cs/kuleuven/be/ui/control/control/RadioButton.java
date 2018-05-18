package interactr.cs.kuleuven.be.ui.control.control;

import interactr.cs.kuleuven.be.ui.PaintBoard;

public class RadioButton extends Control{

    public RadioButton(String label){
        this.label = label;
    }

    @Override
    public void displayControl(PaintBoard paintBoard, int x, int y, boolean active) {
        paintBoard.drawString(label,x,paintBoard.charHeight + y);
        paintBoard.drawOval(x + margeOfLabelAndControl ,y,diameter,diameter);
        if(active)
            paintBoard.fillOval(x + margeOfLabelAndControl + 1 , y + 1, diameter -2, diameter -2);
    }

    private String label;
    private final int diameter = 10;
}
