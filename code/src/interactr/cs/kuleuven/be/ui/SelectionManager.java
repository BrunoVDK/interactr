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

    /**
     * Returns the active component for this selection manager.
     *
     * @return The active component for this selection manager.
     */
    public DiagramComponent getActiveComponent() {
        return this.activeComponent;
    }

    /**
     * Registers the active component for this selection manager.
     */
    private DiagramComponent activeComponent;

    /**
     * Adds the given component to the selected components in this selection manager.
     *  If the given component is already selected, it is activated.
     *
     * @param component The component that is to be selected.
     */
    public void addToSelection(DiagramComponent component) {
        if (isSelected(component)) {
            unselectAll();
            this.activeComponent = component;
            setTemporaryLabel("");
        }
        else
            selection = selection.plus(component);
    }

    /**
     * Removes all components from the list of selected components held by this manager.
     */
    public void unselectAll() {
        activeComponent = null;
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

    /**
     * Returns the temporary label for the active component held by this selection manager.
     */
    public String getTemporaryLabel() {
        return temporaryLabel;
    }

    /**
     * Sets the temporary label for the active component to the given one.
     *
     * @param temporaryLabel The new temporary label for the active component.
     */
    public void setTemporaryLabel(String temporaryLabel) {
        this.temporaryLabel = temporaryLabel;
    }

    /**
     * The temporary label for the active component of this manager.
     */
    private String temporaryLabel = "";

}