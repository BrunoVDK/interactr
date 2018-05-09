package interactr.cs.kuleuven.be.ui.control;

import interactr.cs.kuleuven.be.exceptions.FailedDragOperation;

/**
 * An interface for responders, objects that can respond to system operations.
 *
 * @author Team 25
 * @version 1.0
 */
abstract class Responder {

    /**
     * Returns the next responder associated with this responder.
     */
    Responder getNextResponder() {
        return nextResponder;
    }

    /**
     * Registers the next responder for this responder.
     */
    private Responder nextResponder;

    /**
     * Handle a mouse click at the given coordinates.
     *
     * @param x
     * @param y
     */
    void mouseClicked(int x, int y) {

    }

    /**
     * Handle a mouse drag at the given coordinates.
     *
     * @param fromX The start x coordinate.
     * @param fromY The start y coordinate.
     * @param toX The end x coordinate.
     * @param toY The end y coordinate.
     * @throws FailedDragOperation When the drag failed.
     * @return The responder that handled the drag operation, if any.
     */
    Responder mouseDragged(int fromX, int fromY, int toX, int toY) throws FailedDragOperation {
        throw new FailedDragOperation();
    }

}