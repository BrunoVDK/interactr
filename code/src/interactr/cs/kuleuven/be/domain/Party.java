package interactr.cs.kuleuven.be.domain;

import interactr.cs.kuleuven.be.ui.geometry.Box;
import interactr.cs.kuleuven.be.ui.geometry.Figure;

/**
 * A class of parties.
 *
 * @author Team 25
 * @version 1.0
 */
public class Party extends DiagramComponent {

    /**
     * Creates a novel party.
     *
     * @return The newly created party.
     */
    public static Party createParty() {
        return new ObjectParty();
    }

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
     * Switches the type of this party
     *
     * @return returns the switched party
     */
    public Party switchType(){return null;}

    /**
     * Returns a proposal for how this component should be drawn, by returning a mock-up figure.
     *
     * @return A mock-up for how this component should be drawn.
     */
    public Figure proposedFigure() {
        return new Box();
    }

    @Override
    public boolean canHaveAsLabel(String label) {
        if (!super.canHaveAsLabel(label))
            return false;
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
        return validCharacters;
    }

    @Override
    public void deleteFrom(Diagram diagram) {
        diagram.deleteParty(this);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}