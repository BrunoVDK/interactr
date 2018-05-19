package interactr.cs.kuleuven.be.ui.command;

import interactr.cs.kuleuven.be.exceptions.InvalidLabelException;
import interactr.cs.kuleuven.be.ui.control.diagram.DiagramView;

/**
 * A class of commands for aborting an edit session.
 *
 * @author Team 25
 * @version 1.0
 */
public class AbortEditingCommand extends Command {

    @Override
    public void executeDiagramView(DiagramView view) {
        try {
            view.setIsEditing(false);
        }
        catch (InvalidLabelException ignored) {}
    }

}