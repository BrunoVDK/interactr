package interactr.cs.kuleuven.be.ui.command;

import interactr.cs.kuleuven.be.ui.control.DiagramWindow;
import interactr.cs.kuleuven.be.ui.control.DialogWindow;
import interactr.cs.kuleuven.be.ui.control.dialog.DialogCreator;

/**
 * A class of commands for creating dialogs.
 *
 * @author Team 25
 * @version 1.0
 */
public class CreateDialogCommand extends Command {

    /**
     * Returns the dialog window created with this command.
     */
    public DialogWindow getDialogWindow() {
        return dialogWindow;
    }

    /**
     * Registers the dialog window created by this create dialog command.
     */
    private DialogWindow dialogWindow = null;

    @Override
    public void executeDiagramWindow(DiagramWindow window) {
        if (window.getActiveView().getSelectedComponent() == null)
            dialogWindow = DialogCreator.defaultCreator().createDialog(window.getActiveView());
        else
            dialogWindow = DialogCreator.defaultCreator().createDialog(
                    window.getDiagram(),
                    window.getActiveView().getSelectedComponent());
    }

}