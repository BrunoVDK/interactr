package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.DiagramComponent;
import interactr.cs.kuleuven.be.purecollections.PList;

/**
 * A class of selection managers for managing selections in a diagram.
 *
 * @author Team 25
 * @version 1.0
 */
public class SelectionManager {

    public DiagramComponent getActiveComponent() {
        return this.activeComponent;
    }

    public void setActiveComponent(DiagramComponent component) {
        if (selection.contains(component))
            this.activeComponent = component;
    }

    private DiagramComponent activeComponent;

    public void addToSelection(DiagramComponent component) {
        selection = selection.plus(component);
    }

    public void unselectAll() {
        selection = selection.minusAll(selection);
    }

    /**
     * Returns whether or not the given diagram component is currently selected.
     *
     * @param component The component to check for.
     * @return True if and only if the given diagram component is currently selected.
     */
    public boolean isSelected(DiagramComponent component) {
        return selection.contains(component);
    }

    /**
     * List registering selected components.
     */
    private PList<DiagramComponent> selection = PList.<DiagramComponent>empty();

}