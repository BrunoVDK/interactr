package interactr.cs.kuleuven.be.ui.command;

import interactr.cs.kuleuven.be.exceptions.InvalidAddPartyException;
import interactr.cs.kuleuven.be.ui.control.diagram.DiagramView;
import interactr.cs.kuleuven.be.ui.geometry.Point;

/**
 * A class of commands for adding parties.
 *
 * @author Team 25
 * @version 1.0
 */
public class AddPartyCommand extends Command {

    /**
     * Initialize this new add party command with given location.
     *
     * @param location The location where the party should be added.
     */
    public AddPartyCommand(Point location) {
        this.location = location;
    }

    /**
     * Registers the location for this add party command.
     */
    private final Point location;

    @Override
    public void executeDiagramView(DiagramView view) {
        try {
            view.addParty(location.getX(), location.getY());
        }
        catch (InvalidAddPartyException e) {throw new CommandNotProcessedException();}
    }

}