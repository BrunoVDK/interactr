package interactr.cs.kuleuven.be.ui.design;

/**
 * A class of circles.
 *
 * @author Team 25
 * @version 1.0
 */
public class Circle extends Oval {

    /**
     * Initialize this new circle with given x & y coordines, width and height.
     *
     * @param x The x coordinate for this new circle.
     * @param y The y coordinate for this new circle.
     * @param diameter The diameter for this new circle.
     */
    Circle(int x, int y, int diameter) {
        super(x, y, diameter, diameter);
    }

}