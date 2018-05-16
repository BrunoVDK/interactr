package interactr.cs.kuleuven.be.ui.control.dialog;

import interactr.cs.kuleuven.be.domain.*;

/**
 * A class of visitors for creating dialogs for diagram components.
 *
 * @author Team 25
 * @version 1.0
 */
public class DialogCreator implements DiagramVisitor {

    /**
     * Create a dialogWindow for the given component.
     *
     * @param diagram The diagram of the component is part of.
     * @param component The component to create a dialogWindow for.
     * @return A dialogWindow for the given component, or null if none could be created.
     */
    DialogWindow createDialog(Diagram diagram, Visitable component) {
        dialogWindow = null;
        this.diagram = diagram;
        component.acceptVisitor(this);
        return dialogWindow;
    }

    /**
     * Registers the diagram for which the dialogWindow is created.
     */
    private Diagram diagram;

    /**
     * Registers the created dialogWindow.
     */
    private DialogWindow dialogWindow;

    @Override
    public void visit(Diagram diagram) {
        System.out.println("diagram");
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