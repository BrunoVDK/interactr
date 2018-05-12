package interactr.cs.kuleuven.be.domain;

/**
 * An interface for diagram visitors.
 *
 * @author Team 25
 * @version 1.0
 */
public interface DiagramVisitor {

    /**
     * Visit the given diagram.
     *
     * @param diagram The diagram that is to be visited.
     */
    default void visit(Diagram diagram) {
        System.out.println("diagram");
    }

    /**
     * Visit the given object party.
     *
     * @param party The object party that is to be visited.
     */
    default void visit(ObjectParty party) {}

    /**
     * Visit the given actor party.
     *
     * @param party The actor party that is to be visited.
     */
    default void visit(ActorParty party) {}

    /**
     * Visit the given invocation message.
     *
     * @param message The invocation message that is to be visited.
     */
    default void visit(InvocationMessage message) {}

    /**
     * Visit the given invocation message.
     *
     * @param message The invocation message that is to be visited.
     */
    default void visit(ResultMessage message) {}

}