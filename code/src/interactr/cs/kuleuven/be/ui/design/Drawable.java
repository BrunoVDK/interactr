package interactr.cs.kuleuven.be.ui.design;

import interactr.cs.kuleuven.be.ui.PaintBoard;

/**
 * An interface for drawable components.
 *
 * @author Team 25
 * @version 1.0
 */
public interface Drawable {

    /**
     * Draw this component in the given paintboard.
     *
     * @param paintBoard The paint board on which to draw.
     */
    void draw(PaintBoard paintBoard);

}