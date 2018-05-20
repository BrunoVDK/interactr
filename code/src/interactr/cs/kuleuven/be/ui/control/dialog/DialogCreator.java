package interactr.cs.kuleuven.be.ui.control.dialog;

import interactr.cs.kuleuven.be.domain.*;
import interactr.cs.kuleuven.be.ui.control.DialogWindow;
import interactr.cs.kuleuven.be.ui.control.diagram.DiagramView;

/**
 * A class of visitors for creating dialogs for diagram components.
 *
 * @author Team 25
 * @version 1.0
 */
public class DialogCreator implements DiagramVisitor {

    private DialogCreator() {
        // Exists only to defeat instantiation.
    }

    public static DialogCreator defaultCreator() {
        return defaultCreator;
    }

    /**
     * The singleton instance.
     */

    private final static DialogCreator defaultCreator = new DialogCreator();

    /**
     * Create a dialog for the given diagram view.
     *
     * @param view The view to create a dialog for.
     * @return A dialog for the given view.
     */
    public DialogWindow createDialog(DiagramView view) {
        return new DialogDiagramView(view);
    }

    /**
     * Create a dialog for the given component.
     *
     * @param diagram The diagram of the component is part of.
     * @param component The component to create a dialog for.
     * @return A dialog for the given component, or null if none could be created.
     */
    public DialogWindow createDialog(Diagram diagram, DiagramComponent component) {
        dialogWindow = null;
        this.diagram = diagram;
        component.acceptVisitor(this);
        return dialogWindow;
    }

    /**
     * Registers the diagram for which the dialog is created.
     */
    private Diagram diagram;

    /**
     * Registers the created dialog window.
     */
    private DialogWindow dialogWindow = null;

    @Override
    public void visit(ObjectParty party) {
        dialogWindow = new DialogParty(party, this.diagram, false);
    }

    @Override
    public void visit(ActorParty party) { dialogWindow = new DialogParty(party, this.diagram, true); }

    @Override
    public void visit(ResultMessage message) {
        dialogWindow = new DialogResultMessage(this.diagram, message);
    }

    @Override
    public void visit(InvocationMessage message) {
        dialogWindow = new DialogInvocationMessage(this.diagram, message);
    }

}