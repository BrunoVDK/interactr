package interactr.cs.kuleuven.be.ui.state;

import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.design.Drawable;

/**
 * An interface for
 */
public interface SelectionState {

    /**
     * Select the given component.
     *
     * @param component The component that is to be activated.
     */
    void activateComponent(Drawable component);

    /**
     * Draw the given component.
     *
     * @param component The component that is to be drawn.
     * @param paintBoard The paintboard in which to draw.
     */
    void drawComponent(Drawable component, PaintBoard paintBoard);

}