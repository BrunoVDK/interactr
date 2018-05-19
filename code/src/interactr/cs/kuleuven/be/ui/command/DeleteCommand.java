package interactr.cs.kuleuven.be.ui.command;

import interactr.cs.kuleuven.be.ui.control.diagram.DiagramView;

/**
 * A class of commands for removing the active component(s).
 *
 * @author Team 25
 * @version 1.0
 */
public class DeleteCommand extends Command {

    @Override
    public void executeDiagramView(DiagramView view) {
        view.deleteSelectedComponent();
    }

}