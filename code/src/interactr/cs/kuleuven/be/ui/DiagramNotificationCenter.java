package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.purecollections.PMap;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class of notification centers for notifying registered observers of changes to a diagram.
 *
 *  Currently this notification center (an event bus - pattern = singleton/mediator/observer) only knows
 *  of diagram observers. It could be written to notify subwindows and diagram views of specific notifications
 *  tailored to their needs, but it would be less extensible that way.
 *
 * @author Team 25
 * @version  1.0
 */
public class DiagramNotificationCenter {

    private DiagramNotificationCenter() {
        // Exists only to defeat instantiation.
    }

    /**
     * Get the default notification center for diagrams.
     *  This is a singleton.
     */
    public static DiagramNotificationCenter defaultCenter() {
        return defaultCenter;
    }

    /**
     * The singleton instance.
     */
    private final static DiagramNotificationCenter defaultCenter = new DiagramNotificationCenter();

    /**
     * Post a notification of given type representing a change to the given diagram.
     *  The given parameters are associated with the notifications.
     *
     * This notifies observers associated with the diagram of the update.
     *
     * @param diagram The diagram that was updated.
     * @param type The type of the update.
     * @param parameters The parameters for the update.
     */
    public void postNotification(Diagram diagram, DiagramUpdateType type, HashMap<String, Object> parameters) {
        ArrayList<DiagramObserver> observers = this.observers.get(diagram);
        if (observers != null)
            for (DiagramObserver observer : observers)
                observer.diagramDidUpdate(diagram, type, parameters);
    }

    /**
     * Register the given observer. This associates the observer with the given diagram so that it can
     *  be notified of any changes to that diagram.
     *
     * @param diagram The diagram to register the observer with.
     * @param observer The observer that is to be registered.
     */
    public void registerObserver(Diagram diagram, DiagramObserver observer) {
        ArrayList<DiagramObserver> observers = this.observers.get(diagram);
        if (observers == null) {
            observers = new ArrayList<DiagramObserver>();
            this.observers = this.observers.plus(diagram, observers);
        }
        if (!observers.contains(observer))
            observers.add(observer);
    }

    /**
     * Remove the given observer. If the observer was previously associated with the given diagram,
     *  than it is unregistered as an observer (i.e. it won't receive any updates anymore).
     *
     * @param diagram The diagram to register the observer with.
     * @param observer The observer that is to be registered.
     */
    public void unregisterObserver(Diagram diagram, DiagramObserver observer) {
        ArrayList<DiagramObserver> observers = this.observers.get(diagram);
        if (observers != null)
            observers.remove(observer);
    }

    /**
     * The list of registered observers kept track of by this center.
     *  This is on a per-diagram basis.
     */
    protected PMap<Diagram, ArrayList<DiagramObserver>> observers = PMap.empty();

}