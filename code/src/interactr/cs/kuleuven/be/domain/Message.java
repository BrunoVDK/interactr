package interactr.cs.kuleuven.be.domain;

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
     * @param label The label for this new message.
     * @param sender The sender party for this new message.
     * @param receiver The receiver party for this new message.
     * @throws IllegalArgumentException If any of the given parties is null.
     */
    Message(String label, Party sender, Party receiver) throws IllegalArgumentException {
        super(label);
        setSender(sender);
        setReceiver(receiver);
    }

    /**
     * Returns the sender of this message.
     */
    public Party getSender() {
        return sender;
    }

    /**
     * Set the sender for this message.
     *
     * @param sender The new sender for this message.
     */
    public void setSender(Party sender) {
        if (sender == null || sender == getReceiver())
            throw new IllegalArgumentException("Invalid sender for this message.");
        this.sender = sender;
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
     * Set the receiver for this message.
     *
     * @param receiver The new receiver for this message.
     */
    public void setReceiver(Party receiver) {
        if (receiver == null || receiver == getSender())
            throw new IllegalArgumentException("Invalid receiver for this message.");
        this.receiver = receiver;
    }

    /**
     * Registers the receiver for this message.
     */
    private Party receiver;

    /**
     * Checks whether this message activates the given party.
     *
     * @param party The party to check with.
     * @return True if and only if this message activates the given party.
     */
    public boolean activates(Party party) {
        return (party == getReceiver());
    }

    @Override
    public void deleteFrom(Diagram diagram) {
        diagram.deleteMessage(this);
    }

    @Override
    public String toString() {
        return getSender().toString() + " " + getLabel() + " " + getReceiver().toString();
    }

}
