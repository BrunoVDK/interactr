package interactr.cs.kuleuven.be.ui.control.diagram;

import interactr.cs.kuleuven.be.domain.*;
import interactr.cs.kuleuven.be.ui.design.Arrow;
import interactr.cs.kuleuven.be.ui.design.DashedArrow;
import interactr.cs.kuleuven.be.ui.design.Link;

/**
 * A class of visitors for creating links for ùessages.
 *
 * @author Team 25
 * @version 1.0
 */
public class MessageModeller implements DiagramVisitor {

    private MessageModeller() {
        // Exists only to defeat instantiation.
    }

    /**
     * Get the default modeller for diagrams.
     *  This is a singleton.
     */
    public static MessageModeller defaultCenter() {
        return defaultModeller;
    }

    /**
     * The singleton instance.
     */
    private final static MessageModeller defaultModeller = new MessageModeller();

    /**
     * Create a link for the given message.
     *  This link is not unique, i.e. it is meant to be used as a flyweight.
     *
     * @param message The message to create a link for.
     * @return A link representing the given message, or null if none could be created.
     */
    Link generateLink(Message message) {
        link = null;
        message.acceptVisitor(this);
        return link;
    }

    /**
     * Registers the created link.
     */
    private Link link;

    /**
     * Registers the flyweights.
     */
    private static Link invocationLink = new Arrow(), resultLink = new DashedArrow();

    @Override
    public void visit(InvocationMessage message) {
        link = invocationLink;
    }

    @Override
    public void visit(ResultMessage message) { link = resultLink; }

}