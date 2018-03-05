package interactr.cs.kuleuven.be.domain;

/**
 * A class of parties.
 */
public class Party {

    public boolean canHaveAsLabel(Label label) {
        return label.getLength() <= 30
                && true;
    }

}
