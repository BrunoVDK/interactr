package interactr.cs.kuleuven.be.domain;

/**
 * A class of result messages.
 *
 * @author Team 25
 * @version 1.0
 */
public class InvocationMessage extends Message {

    /**
     * Initialize this invocation message with given sending and receiving party.
     *
     * @param sender The sender of this invocation message.
     * @param receiver The receiver of this invocation message.
     */
    public InvocationMessage(Party sender , Party receiver) {super(sender, receiver);}

}
