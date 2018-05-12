package interactr.cs.kuleuven.be.ui.visitor;

import interactr.cs.kuleuven.be.domain.*;
import interactr.cs.kuleuven.be.ui.control.Dialog;

/**
 * A class of visitors for creating dialogs for diagram components.
 *
 * @author Team 25
 * @version 1.0
 */
public class DialogCreator implements DiagramVisitor {

    /**
     * Create a dialog for the given component.
     *
     * @param diagram The diagram of the component is part of.
     * @param component The component to create a dialog for.
     * @return A dialog for the given component, or null if none could be created.
     */
    Dialog createDialog(Diagram diagram, Visitable component) {
        dialog = null;
        this.diagram = diagram;
        component.acceptVisitor(this);
        return dialog;
    }

    /**
     * Registers the diagram for which the dialog is created.
     */
    private Diagram diagram;

    /**
     * Registers the created dialog.
     */
    private Dialog dialog;

    @Override
    public void visit(Diagram diagram) {
        System.out.println("diagram");
        dialog = null;
    }

    @Override
    public void visit(ObjectParty party) {

    }

    @Override
    public void visit(ActorParty party) {

    }

    @Override
    public void visit(ResultMessage message) {

    }

    @Override
    public void visit(InvocationMessage message) {

    }

}