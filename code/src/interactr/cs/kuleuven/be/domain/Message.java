package interactr.cs.kuleuven.be.domain;

import interactr.cs.kuleuven.be.ui.geometry.Arrow;

/**
 * A  class of messages. Each message has a receiving - and a sender party.
 *
 * @author Team 25
 * @version 1.0
 */
public class Message extends DiagramComponent {

    /**
     * Initialize this new message with given sender and receiver party.
     *
     * @param sender The sender party for this new message.
     * @param receiver The receiver party for this new message.
     * @throws IllegalArgumentException If any of the given parties is null.
     */
    public Message(Party sender, Party receiver) throws IllegalArgumentException {
        if (sender == null || receiver == null)
            throw new IllegalArgumentException("Null parties.");
        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     * Returns the sender of this message.
     */
    public Party getSender() {
        return sender;
    }

    /**
     * Registers the sender for this message.
     */
    private Party sender;

    /**
     * Returns the receiver of this message.
     */
    public Party getReceiver() {
        return receiver;
    }

    /**
     * Registers the receiver for this message.
     */
    private Party receiver;

    /**
     * Checks whether or not the given label is a valid label for this message.
     *
     * @param label The label whose validity is to be checked.
     * @return True if and only if the given label has a length of at most 30 characters.
     */
    public boolean canHaveAsLabel(Label label) {
        return label.getLength() <= 30;
    }

    /**
     * Returns a proposal link type for drawing this message.
     *
     * @return A link type for drawing this message.
     */
    public Class proposedLinkType() {
        return Arrow.class;
    }

}
