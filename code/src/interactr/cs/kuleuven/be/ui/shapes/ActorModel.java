package interactr.cs.kuleuven.be.ui.shapes;

import interactr.cs.kuleuven.be.ui.HitBox;

import java.awt.*;

public class ActorModel{

    public static void draw(Graphics g, HitBox hitBox) {
        g.drawOval(hitBox.getxMin(),hitBox.getyMin(),HitBox.width,HitBox.heigth);
    }
}
