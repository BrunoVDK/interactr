package interactr.cs.kuleuven.be.ui.command;

import interactr.cs.kuleuven.be.ui.geometry.Point;

/**
 * A class of commands for dragging.
 *
 * @author Team 25
 * @version 1.0
 */
abstract class DragCommand extends Command {

    /**
     * Initialize this new command with given source and end location.
     *
     * @param fromLocation The point to drag from.
     * @param toLocation The point to drag to.
     */
    DragCommand(Point fromLocation, Point toLocation) {
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
    }

    /**
     * Returns the start coordinate for this drag session.
     */
    Point getFromLocation() {
        return fromLocation;
    }

    /**
     * Returns the end coordinate for this drag session.
     */
    Point getToLocation() {
        return toLocation;
    }

    /**
     * Registers the start and end coordinates for the drag session.
     */
    private Point fromLocation, toLocation;

}