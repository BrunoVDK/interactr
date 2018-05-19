package interactr.cs.kuleuven.be.ui.command;

import interactr.cs.kuleuven.be.exceptions.NoSuchComponentException;
import interactr.cs.kuleuven.be.ui.control.DiagramWindow;
import interactr.cs.kuleuven.be.ui.control.diagram.DiagramView;
import interactr.cs.kuleuven.be.ui.geometry.Point;

/**
 * A class of commands for selecting something at a given location.
 *
 * @author Team 25
 * @version 1.0
 */
public class SelectCommand extends Command {

    /**
     * Initialise this command with given location.
     *
     * @param location The location of that which needs to be selected.
     */
    public SelectCommand(Point location) {
        this.location = location;
    }

    /**
     * Registers the location for the selection.
     */
    private final Point location;

    @Override
    public void executeDiagramView(DiagramView view) {
        try {
            view.selectComponentAt(view.getRelativeCoordinates(location).getX(), view.getRelativeCoordinates(location).getY());
        }
        catch (Exception exception) {
            throw new CommandNotProcessedException();
        }
    }

}