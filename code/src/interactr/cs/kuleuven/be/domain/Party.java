package interactr.cs.kuleuven.be.domain;

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
    public Party switchType(){
        return null;
    }

    @Override
    public boolean canHaveAsLabel(String label) {
        if (!super.canHaveAsLabel(label))
            return false;
        String[] parts = label.split(":");
        if (parts.length == 2)
            return (canHaveAsInstanceName(parts[0]) && canHaveAsClassName(parts[1]));
        return false;
    }

    /**
     * Checks whether or not the given instance name can be used as an instance name for this party.
     *
     * @param instanceName The instance name to check with.
     * @return True if and only if the given instance name is valid.
     */
    public boolean canHaveAsInstanceName(String instanceName) {
        if (instanceName.length() == 0)
            return true;
        return (Character.isLowerCase(instanceName.charAt(0)) && Character.isLetter(instanceName.charAt(0)));
    }

    /**
     * Returns the class name for this party.
     */
    public String getClassName() {
        return getLabel().split(":")[1];
    }

    /**
     * Checks whether or not the given class name can be used as an class name for this party.
     *
     * @param className The instance name to check with.
     * @return True if and only if the given instance name is valid.
     */
    public boolean canHaveAsClassName(String className) {
        if (className.length() == 0)
            return false;
        return (Character.isUpperCase(className.charAt(0)) && Character.isLetter(className.charAt(0)));
    }

    /**
     * Returns the instance name for this party.
     */
    public String getInstanceName() {
        return getLabel().split(":")[0];
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