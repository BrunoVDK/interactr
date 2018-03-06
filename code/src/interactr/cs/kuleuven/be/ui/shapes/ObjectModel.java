package interactr.cs.kuleuven.be.ui.shapes;

import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.HitBox;
import interactr.cs.kuleuven.be.ui.PaintController;

import java.awt.*;

public class ObjectModel {

    public static final int labelXOffset = 5;
    public static final int labelYOffset = HitBox.heigth/2;
    public static final int labelWidth = HitBox.width -10;
    public static final int labelHeight = 15;


    public static void draw(Graphics g, HitBox hitBox,Party party){
        g.drawRect(hitBox.getxMin(),hitBox.getyMin(),HitBox.width,HitBox.heigth);
        if(party.getLabel() != null)
        g.drawString(party.getLabel(),
                hitBox.getxMin() + labelXOffset + 2,
                hitBox.getyMin() + labelYOffset + labelHeight - 3);

        g.drawRect(hitBox.getxMin() + labelXOffset
                ,hitBox.getyMin() + labelYOffset,
                labelWidth,labelHeight);
    }


}
