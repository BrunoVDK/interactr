package interactr.cs.kuleuven.be.domain;

/**
 * A  class of messages. Each message has a receiving - and a sender party.
 */
public class Message {

    /**
     * Checks whether or not the given label is a valid label for this message.
     *
     * @param label The label whose validity is to be checked.
     * @return True if and only if the given label has a length of at most 30 characters.
     */
    public boolean canHaveAsLabel(Label label) {
        return label.getLength() <= 30;
    }

}
