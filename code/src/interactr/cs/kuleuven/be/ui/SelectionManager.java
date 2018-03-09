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
     * Returns all components selected by this selection manager.
     *
     * @return A list of components in the selection held by this manager.
     */
    public PList<DiagramComponent> getSelectedComponents() {
        return selection;
    }

    /**
     * Returns the active component for this selection manager.
     *
     * @return The active component for this selection manager.
     */
    public DiagramComponent getActiveComponent() {
        return this.activeComponent;
    }

    /**
     * Sets the active component for this selection manager to the given one.
     *
     * @param activeComponent The new active component for this manager.
     */
    public void setActiveComponent(DiagramComponent activeComponent) {
        unselectAll();
        setTemporaryLabel("");
        this.activeComponent = activeComponent;
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
            setActiveComponent(component);
            setTemporaryLabel("");
        }
        else if (component != null)
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
     * @param label The new temporary label for the active component.
     * @throws IllegalArgumentException If the given label is null.
     */
    public void setTemporaryLabel(String label) {
        if (label == null)
            throw new IllegalArgumentException("Invalid temporary label (null).");
        this.temporaryLabel = label;
    }

    /**
     * The temporary label for the active component of this manager.
     */
    private String temporaryLabel = "";

}