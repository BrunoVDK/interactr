package interactr.cs.kuleuven.be.domain;

/**
 * A class of result messages.
 *
 * @author Team 25
 * @version 1.0
 */
public class ResultMessage extends Message {

    /**
     * Initialize this result message with the given invocation message.
     *
     * @param invocation The invocation message to initialize this result message with.
     */
    ResultMessage(InvocationMessage invocation) {
        this(invocation.getReceiver(), invocation.getSender());
    }

    /**
     * Initialize this result message with given sending and receiving party.
     *
     * @param sender The sender of this result message.
     * @param receiver The receiver of this result message.
     */
    private ResultMessage(Party sender, Party receiver) {super("", sender,receiver);}

    @Override
    public boolean activates(Party party) {
        return false;
    }

    @Override
    public void acceptVisitor(DiagramVisitor visitor) {
        visitor.visit(this);
    }
}
