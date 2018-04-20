package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.*;
import interactr.cs.kuleuven.be.purecollections.PMap;
import interactr.cs.kuleuven.be.ui.geometry.Point;

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
     * Notify observers that the given party was added to the given diagram.
     *
     * @param diagram The diagram to which the party was added.
     * @param party The party that was added.
     * @param coordinates The coordinates at which the party was added.
     */
    public void diagramDidAddParty(Diagram diagram, Party party, Point coordinates) {
        ArrayList<DiagramView> observers = this.observingDiagramViews.get(diagram);
        if (observers != null)
            for (DiagramView observer : observers)
                observer.diagramDidAddParty(diagram, party, coordinates);
    }

    /**
     * Notify observers that the given party was replaced in the given diagram.
     *
     * @param diagram The diagram to which the party was added.
     * @param party The party that was replaced.
     * @param newParty The replacement of the party.
     */
    public void diagramDidReplaceParty(Diagram diagram, Party party, Party newParty) {
        ArrayList<DiagramView> observers = this.observingDiagramViews.get(diagram);
        if (observers != null)
            for (DiagramView observer : observers)
                observer.diagramDidReplaceParty(diagram, party, newParty);
    }

    /**
     * Notify observers that the given messages were added to the given diagram.
     *
     * @param diagram The diagram to which the party was added.
     * @param invocationMessage The invocation message that was added.
     * @param resultMessage The result message that was added.
     * @param startCoordinates The start coordinates for the invocation message.
     * @param endCoordinates The end coordinates for the invocation message.
     */
    public void diagramDidAddMessages(Diagram diagram,
                                      InvocationMessage invocationMessage,
                                      ResultMessage resultMessage,
                                      Point startCoordinates,
                                      Point endCoordinates) {
        ArrayList<DiagramView> observers = this.observingDiagramViews.get(diagram);
        if (observers != null)
            for (DiagramView observer : observers)
                observer.diagramDidAddMessages(diagram, invocationMessage, resultMessage, startCoordinates, endCoordinates);
    }

    /**
     * Notify observers that the given component was removed from the given diagram.
     *
     * @param diagram The diagram from which the component was removed.
     * @param component The component that was deleted.
     */
    public void diagramDidDeleteComponent(Diagram diagram, DiagramComponent component) {
        ArrayList<DiagramView> observers = this.observingDiagramViews.get(diagram);
        if (observers != null)
            for (DiagramView observer : observers)
                observer.diagramDidDeleteComponent(diagram, component);
    }

    /**
     * Notify observers that the label of the component belonging to the given diagram was edited.
     *
     * @param diagram The diagram to which the component belongs.
     * @param component The component whose label was edited.
     */
    public void diagramDidEditLabel(Diagram diagram, DiagramComponent component) {
        ArrayList<SubWindow> observers = this.observingSubWindows.get(diagram);
        if (observers != null)
            for (SubWindow observer : observers)
                observer.diagramDidEditLabel(diagram, component);
    }

    /**
     * Register the given diagram view as an observer. This associates the observer with the given diagram so that it can
     *  be notified of any changes to that diagram.
     *
     * @param diagram The diagram to register the observer with.
     * @param diagramView The diagram view that is to be registered.
     */
    public void registerDiagramView(Diagram diagram, DiagramView diagramView) {
        ArrayList<DiagramView> observers = this.observingDiagramViews.get(diagram);
        if (observers == null) {
            observers = new ArrayList<DiagramView>();
            this.observingDiagramViews = this.observingDiagramViews.plus(diagram, observers);
        }
        if (!observers.contains(diagramView))
            observers.add(diagramView);
    }

    /**
     * Register the given subwindow as an observer. This associates the observer with the given diagram so that it can
     *  be notified of any changes to that diagram.
     *
     * @param diagram The diagram to register the observer with.
     * @param subWindow The subwindow that is to be registered.
     */
    public void registerSubWindow(Diagram diagram, SubWindow subWindow) {
        ArrayList<SubWindow> observers = this.observingSubWindows.get(diagram);
        if (observers == null) {
            observers = new ArrayList<SubWindow>();
            this.observingSubWindows = this.observingSubWindows.plus(diagram, observers);
        }
        if (!observers.contains(subWindow))
            observers.add(subWindow);
    }

    /**
     * Remove the given subwindow as an observer. If the observer was previously associated with the given diagram,
     *  than it is unregistered as an observer (i.e. it won't receive any updates anymore).
     *
     * @param diagram The diagram to register the observer with.
     * @param subWindow The subwindow that is to be removed as an observer.
     */
    public void unregisterSubWindow(Diagram diagram, SubWindow subWindow) {
        if (diagram != null) {
            ArrayList<SubWindow> observersForDiagram = this.getObservingSubWindowsForDiagram(diagram);
            if (observersForDiagram != null)
                this.removeObservingSubWindow(observersForDiagram, subWindow);
        }
    }

    /**
     * Returns the observing subwindows for the given diagram.
     *
     * @param diagram The diagram observed by the desired subwindows.
     * @return A list of subwindows observing the given diagram.
     */
    private ArrayList<SubWindow> getObservingSubWindowsForDiagram(Diagram diagram){
        return observingSubWindows.get(diagram);
    }

    /**
     * Removes the given subwindow as an observer from the given list of observers.
     *
     * @param observers The list of observers to remove the subwindow from.
     * @param subWindow The subwindow that is to be removed.
     */
    private void removeObservingSubWindow(ArrayList<SubWindow> observers, SubWindow subWindow){
        observers.remove(subWindow);
    }

    /**
     * Remove the given diagram view as an observer. If the observer was previously associated with the given diagram,
     *  than it is unregistered as an observer (i.e. it won't receive any updates anymore).
     *
     * @param diagram The diagram to register the observer with.
     * @param diagramView The diagram view that is to be removed as an observer.
     */
    public void unregisterDiagramView(Diagram diagram, DiagramView diagramView) {
        if (diagram != null) {
            ArrayList<DiagramView> observers = this.observingDiagramViews.get(diagram);
            if (observers != null)
                observers.remove(diagramView);
        }
    }

    /**
     * The list of observing diagram views kept track of by this center.
     *  This is on a per-diagram basis.
     */
    private PMap<Diagram, ArrayList<DiagramView>> observingDiagramViews = PMap.empty();

    /**
     * The list of observing subwindows kept track of by this center.
     *  This is on a per-diagram basis.
     */
    private PMap<Diagram, ArrayList<SubWindow>> observingSubWindows = PMap.empty();

}