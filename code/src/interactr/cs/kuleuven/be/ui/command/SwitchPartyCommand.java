package interactr.cs.kuleuven.be.ui.command;

import interactr.cs.kuleuven.be.exceptions.NoSuchPartyException;
import interactr.cs.kuleuven.be.ui.control.diagram.DiagramView;
import interactr.cs.kuleuven.be.ui.geometry.Point;

/**
 * A class of commands for switching party types.
 *
 * @author Team 25
 * @version 1.0
 */
public class SwitchPartyCommand extends Command {

    /**
     * Initialize this new switch party command with given location.
     *
     * @param location The location of the party whose type should be switched.
     */
    public SwitchPartyCommand(Point location) {
        this.location = location;
    }

    /**
     * Registers the location for this add party command.
     */
    private final Point location;

    @Override
    public void executeDiagramView(DiagramView view) {
        try {
            view.switchTypeOfParty(location.getX(), location.getY());
        }
        catch (NoSuchPartyException e) {throw new CommandNotProcessedException();}
    }

}