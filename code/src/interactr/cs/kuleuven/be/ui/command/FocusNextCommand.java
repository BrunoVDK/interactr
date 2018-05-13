package interactr.cs.kuleuven.be.ui.command;

import interactr.cs.kuleuven.be.ui.control.SubWindow;

/**
 * A class of commands for adding parties.
 *
 * @author Team 25
 * @version 1.0
 */
public class FocusNextCommand extends Command {

    @Override
    public void executeSubWindow(SubWindow window) {
        window.nextView();
    }

}