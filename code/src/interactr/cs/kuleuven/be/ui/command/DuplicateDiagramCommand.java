package interactr.cs.kuleuven.be.ui.command;

import interactr.cs.kuleuven.be.domain.ActorParty;
import interactr.cs.kuleuven.be.ui.control.DiagramWindow;
import interactr.cs.kuleuven.be.ui.control.DialogWindow;
import interactr.cs.kuleuven.be.ui.control.dialog.DialogCreator;

/**
 * A class of commands for duplicating diagrams.
 *
 * @author Team 25
 * @version 1.0
 */
public class DuplicateDiagramCommand extends Command {

    /**
     * Returns the diagram window created with this command.
     */
    public DiagramWindow getDiagramWindow() {
        return diagramWindow;
    }

    /**
     * Registers the diagram window created by this duplicate diagram command.
     */
    private DiagramWindow diagramWindow = null;

    @Override
    public void executeDiagramWindow(DiagramWindow window) {
        diagramWindow = new DiagramWindow(window);
    }

}