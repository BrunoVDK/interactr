package interactr.cs.kuleuven.be.ui.state;

import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.design.Drawable;

/**
 * A class of states representing an empty selection.
 *
 * @author Team 25
 * @version 1.0
 */
public class SelectionStateNone implements SelectionState {

    @Override
    public void activateComponent(Drawable component) {

    }

    @Override
    public void drawComponent(Drawable component, PaintBoard paintBoard) {
        component.draw(paintBoard);
    }

}