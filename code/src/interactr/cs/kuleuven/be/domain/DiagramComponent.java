package interactr.cs.kuleuven.be.domain;

import interactr.cs.kuleuven.be.exceptions.InvalidLabelException;

/**
 * A class of diagram components.
 *
 * @author Team 25
 * @version 1.0
 */
public abstract class DiagramComponent {

    /**
     * Initialize this new diagram component with an empty label.
     */
    public DiagramComponent() {
        this("");
    }

    /**
     * Initialize this new diagram component with given label.
     *
     * @param label The label to initialize this component with.
     * @throws InvalidLabelException If the new component can not have the given label.
     */
    public DiagramComponent(String label) throws InvalidLabelException {
        setLabel(label);
    }

    /**
     * Checks whether or not this diagram component can have the given string as its label.
     *
     * @param label The string to check with.
     * @return True if and only if the given string is not null and is at most 30 characters long.
     */
    public boolean canHaveAsLabel(String label) {
        return (label != null && label.length() <= 30);
    }

    /**
     * Returns the label of this figure.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label of this figure to the given value.
     *
     * @param label The new label value for this figure.
     * @throws InvalidLabelException If the given label is not a valid label for this component.
     */
    public void setLabel(String label) throws InvalidLabelException {
        if (!canHaveAsLabel(label))
            throw new InvalidLabelException();
        this.label = label;
    }

    /**
     * Registers the label for this figure.
     */
    private String label;

    /**
     * Delete this diagram component from the given diagram.
     *
     * @param diagram The diagram from which this component should be deleted.
     */
    public abstract void deleteFrom(Diagram diagram);

    @Override
    public String toString() {
        return this.getLabel();
    }
}