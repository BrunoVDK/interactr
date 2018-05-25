package interactr.cs.kuleuven.be.ui.command;

import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.control.diagram.DiagramView;
import interactr.cs.kuleuven.be.ui.geometry.Point;

/**
 * A class of commands for moving parties.
 *
 * @author Team 25
 * @version 1.0
 */
public class MovePartyCommand extends Command {

    /**
     * Initialize this new command with given source and end location.
     *
     * @param fromLocation The source coordinates.
     * @param toLocation The end coordinates.
     */
    public MovePartyCommand(Point fromLocation, Point toLocation) {
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
    }

    /**
     * Sets the target location of this move party command to the given one.
     *
     * @param targetLocation The new target location for this move party command.
     */
    public void setTargetLocation(Point targetLocation) {
        this.toLocation = targetLocation;
    }

    /**
     * Registers the start and end coordinates of the moving session.
     */
    private Point fromLocation, toLocation;

    /**
     * Returns whether or not this command was successful.
     *
     * @return True if and only if a party was moved using this command.
     */
    public boolean movedParty() {
        return (movedParty != null);
    }

    /**
     * Registers the party that is to be moved.
     */
    private Party movedParty = null;

    @Override
    public void executeDiagramView(DiagramView view) {
        try {
            if (movedParty == null)
                movedParty = view.getParty(view.getRelativeCoordinates(fromLocation).getX(), view.getRelativeCoordinates(fromLocation).getY());
            view.moveParty(movedParty, view.getRelativeCoordinates(toLocation).getX(), view.getRelativeCoordinates(toLocation).getY());
        }
        catch (Exception e) {
            throw new CommandNotProcessedException();
        }
    }

}