package interactr.cs.kuleuven.be.ui.command;

import interactr.cs.kuleuven.be.ui.control.DiagramWindow;
import interactr.cs.kuleuven.be.ui.control.DialogWindow;
import interactr.cs.kuleuven.be.ui.control.dialog.DialogCreator;

public class CreateDialogCommand extends Command {

    @Override
    public void executeDiagramWindow(DiagramWindow window) {
        window.getSelectedComponent().acceptVisitor(DialogCreator.defaultModeller());
    }
}
