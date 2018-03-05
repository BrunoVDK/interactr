package interactr.cs.kuleuven.be.ui.shapes;

import interactr.cs.kuleuven.be.ui.HitBox;

import java.awt.*;

public class ObjectModel {


    public static void draw(Graphics g, HitBox hitBox){
        g.drawRect(hitBox.getxMin(),hitBox.getyMin(),HitBox.width,HitBox.heigth);
    }


}
