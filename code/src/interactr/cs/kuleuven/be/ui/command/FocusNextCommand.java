package interactr.cs.kuleuven.be.ui.command;

import interactr.cs.kuleuven.be.ui.control.DiagramWindow;
import interactr.cs.kuleuven.be.ui.control.DialogWindow;

/**
 * A class of commands for adding parties.
 *
 * @author Team 25
 * @version 1.0
 */
public class FocusNextCommand extends Command {

    @Override
    public void executeDiagramWindow(DiagramWindow window) {
        window.nextView();
    }

    @Override
    public void executeDialogWindow(DialogWindow dialog) {
        dialog.focusNext();
    }

}