package interactr.cs.kuleuven.be.ui.command;

import interactr.cs.kuleuven.be.exceptions.InvalidActivateException;
import interactr.cs.kuleuven.be.ui.control.DialogWindow;

/**
 * A class of commands for activating the focused element.
 *
 * @author Team 25
 * @version 1.0
 */
public class ActivateFocusCommand extends Command {

    @Override
    public void executeDialogWindow(DialogWindow dialog) {
        try {
            dialog.activateFocus();
        }
        catch (InvalidActivateException e) {
            throw new CommandNotProcessedException();
        }
    }

}