package interactr.cs.kuleuven.be.domain;

/**
 * An interface for visitable components.
 *
 * @author Team 25
 * @version 1.0
 */
public interface Visitable {

    /**
     * Accept the given visit.
     *
     * @param visitor The visit that is to be accepted.
     */
    default void acceptVisitor(DiagramVisitor visitor) {}

}