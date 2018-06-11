package interactr.cs.kuleuven.be.domain;

/**
 * A class of diagram observers.
 *
 * @author Team 25
 * @version 1.0
 */
public interface DiagramObserver {

    /**
     * Notify observers that the given party was added to the given diagram.
     *
     * @param diagram The diagram to which the party was added.
     * @param party The party that was added.
     */
    default void diagramDidAddParty(Diagram diagram, Party party) {}

    /**
     * Notify observers that the given party was replaced in the given diagram.
     *
     * @param diagram The diagram to which the party was added.
     * @param party The party that was replaced.
     * @param newParty The replacement of the party.
     */
    default void diagramDidReplaceParty(Diagram diagram, Party party, Party newParty) {}

    /**
     * Notify observers that the given messages were added to the given diagram.
     *
     * @param diagram The diagram to which the party was added.
     * @param invocationMessage The invocation message that was added.
     * @param resultMessage The result message that was added.
     * @param row The row into which the messages were inserted.
     */
    default void diagramDidAddMessages(Diagram diagram, InvocationMessage invocationMessage, ResultMessage resultMessage, int row) {}

    /**
     * Notify observers that the given party was removed from the given diagram.
     *
     * @param diagram The diagram from which the component was removed.
     * @param party The party that was deleted.
     */
    default void diagramDidDeleteParty(Diagram diagram, Party party) {}

    /**
     * Notify observers that the given message was removed from the given diagram.
     *
     * @param diagram The diagram from which the component was removed.
     * @param message The message that was deleted.
     */
    default void diagramDidDeleteMessage(Diagram diagram, Message message) {}

    /**
     * Notify observers that the given component's label was edited.
     *
     * @param diagram The diagram to which the component belongs.
     * @param component The component whose label was edited.
     */
    default void diagramDidEditLabel(Diagram diagram, DiagramComponent component) {}

}