package interactr.cs.kuleuven.be.ui.command;

import interactr.cs.kuleuven.be.ui.geometry.Point;

/**
 * A class of commands for dragging.
 *
 * @author Team 25
 * @version 1.0
 */
public abstract class DragCommand extends Command {

    /**
     * Initialize this new command with given source and end location.
     *
     * @param startLocation The point to drag from.
     * @param toLocation The point to drag to.
     */
    DragCommand(Point startLocation, Point toLocation) {
        this.startLocation = startLocation;
        this.fromLocation = startLocation;
        this.toLocation = toLocation;
    }

    @Override
    public void execute(CommandHandler receiver) throws CommandNotProcessedException {
        super.execute(receiver);
        this.fromLocation = this.toLocation;
    }

    /**
     * Set the new end location for this drag operation.
     *
     * @param toLocation The new end coordinate for this drag session.
     */
    public void setTargetLocation(Point toLocation) {
        this.toLocation = toLocation;
    }

    /**
     * Registers the start and end coordinates for the drag session.
     */
    Point startLocation, fromLocation, toLocation;

}