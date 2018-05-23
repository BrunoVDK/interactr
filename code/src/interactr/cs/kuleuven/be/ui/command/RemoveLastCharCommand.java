package interactr.cs.kuleuven.be.ui.command;

import interactr.cs.kuleuven.be.exceptions.InvalidLabelException;
import interactr.cs.kuleuven.be.ui.control.diagram.DiagramView;

/**
 * A class of commands for removing the last char in an active component's label.
 *
 * @author Team 25
 * @version 1.0
 */
public class RemoveLastCharCommand extends Command {

    @Override
    public void executeDiagramView(DiagramView view) {
        try {
            view.setSelectedLabel(view.getSelectedLabel().substring(0, view.getSelectedLabel().length()-1));
        }
        catch (InvalidLabelException ignored) {}
    }

}