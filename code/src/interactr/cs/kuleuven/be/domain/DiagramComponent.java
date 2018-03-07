package interactr.cs.kuleuven.be.domain;

import interactr.cs.kuleuven.be.ui.geometry.LabeledFigure;

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
     * @throws IllegalArgumentException If the given label is not a valid label for this component.
     */
    public DiagramComponent(String label) throws IllegalArgumentException {
        setLabel(label);
    }

    /**
     * Checks whether or not this diagram component can have the given string as its label.
     *
     * @param label The string to check with.
     * @return True if and only if the given string is at most 30 characters long.
     */
    public boolean canHaveAsLabel(String label) {
        return (label.length() <= 30);
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
     * @throws IllegalArgumentException If the given label is not a valid label for this component.
     */
    public void setLabel(String label) throws IllegalArgumentException {
        if (!canHaveAsLabel(label))
            throw new IllegalArgumentException("Invalid label for this component.");
        this.label = label;
    }

    /**
     * Registers the label for this figure.
     */
    private String label;

    /**
     * Returns a proposal for how this component should be drawn, by returning a mock-up figure.
     *
     * @return A mock-up for how this component should be drawn.
     */
    public abstract LabeledFigure getProposedFigure();

}