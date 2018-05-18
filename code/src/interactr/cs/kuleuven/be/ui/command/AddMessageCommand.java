package interactr.cs.kuleuven.be.ui.command;

import interactr.cs.kuleuven.be.exceptions.InvalidAddMessageException;
import interactr.cs.kuleuven.be.ui.control.diagram.DiagramView;
import interactr.cs.kuleuven.be.ui.geometry.Point;

/**
 * A class of commands for adding a message to a diagram.
 *
 * @author Team 25
 * @version 1.0
 */
public class AddMessageCommand extends Command {

    /**
     * Initialize this new command with given source and end location.
     *
     * @param fromLocation The point where the future message should start.
     * @param toLocation The point where the future message should end.
     */
    public AddMessageCommand(Point fromLocation, Point toLocation) {
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
    }

    /**
     * Registers the start and end coordinates for the message adding.
     */
    Point fromLocation, toLocation;

    @Override
    public void executeDiagramView(DiagramView view) {
        try {
            view.addMessage(
                    view.getRelativeCoordinates(fromLocation).getX(),
                    view.getRelativeCoordinates(fromLocation).getY(),
                    view.getRelativeCoordinates(toLocation).getX(),
                    view.getRelativeCoordinates(toLocation).getY());
        }
        catch (Exception e) {
            throw new CommandNotProcessedException();
        }
    }

}