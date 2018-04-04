package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.purecollections.PMap;

import java.util.ArrayList;
import java.util.List;

/**
 * A class of notification centers for notifying registered observers of changes to a diagram.
 *
 * @author Team 25
 * @version  1.0
 */
public class DiagramNotificationCenter {

    /**
     * Register the given observer with the given diagram.
     */
    public void registerObserver(Diagram diagram, Object observer) {
        observers = observers.plus(diagram, observer);
    }

    /**
     * The list of registered observers kept track of by this center.
     *  This is on a per-diagram basis.
     */
    protected PMap<Diagram, Object> observers = PMap.<Diagram, Object>empty();

    /**
     * @note https://www.javaworld.com/article/2073352/core-java/simply-singleton.html
     */
    private DiagramNotificationCenter() {
        // Exists only to defeat instantiation.
    }

    /**
     * Get the default notification center for diagrams.
     *
     * @note This is a singleton
     */
    public static DiagramNotificationCenter defaultCenter() {
        return defaultCenter;
    }

    /**
     * The singleton instance.
     */
    private final static DiagramNotificationCenter defaultCenter = new DiagramNotificationCenter();

}