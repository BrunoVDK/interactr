package interactr.cs.kuleuven.be.domain;

import interactr.cs.kuleuven.be.ui.exceptions.InvalidLabelException;
import interactr.cs.kuleuven.be.ui.geometry.Box;

/**
 * A class of parties.
 *
 * @author Team 25
 * @version 1.0
 */
public class Party extends DiagramComponent {

    /**
     * Initialize this new party with a ':Class' label.
     */
    public Party() {
        super(":Class");
    }

    /**
     * Initialize this new party with the given label.
     *
     * @param label The label for this new party.
     */
    public Party(String label) {
        super(label);
    }

    /**
     * Initialize this new party with the given party.
     *
     * @param party The party to initialize this party with.
     */
    public Party(Party party) {
        super(party.getLabel());
    }

    /**
     * Returns a proposal for how this component should be drawn, by returning a mock-up figure.
     *
     * @return A mock-up for how this component should be drawn.
     */
    public Class proposedFigure() {
        return Box.class;
    }

    @Override
    public boolean canHaveAsLabel(String label) {
        boolean validCharacters = true;
        String[] parts = label.split(":");
        if (parts.length == 2) {
            // Instance doesn't start with lowercase letter
            if (parts[0].length() != 0 && (!Character.isLowerCase(parts[0].charAt(0)) || !Character.isLetter(parts[0].charAt(0))))
                validCharacters = false;
            if (parts[1].length() == 0 || (!Character.isUpperCase(parts[1].charAt(0)) || !Character.isLetter(parts[1].charAt(0))))
                validCharacters = false;
        }
        else
            validCharacters = false;
        return (validCharacters && super.canHaveAsLabel(label));
    }

    @Override
    public void delete(Diagram diagram) {
        diagram.deleteParty(this);
    }

}