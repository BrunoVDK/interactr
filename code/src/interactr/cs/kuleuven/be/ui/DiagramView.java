package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.DiagramComponent;
import interactr.cs.kuleuven.be.domain.Label;
import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.purecollections.PMap;
import interactr.cs.kuleuven.be.ui.exceptions.InvalidAddException;
import interactr.cs.kuleuven.be.ui.geometry.*;

/**
 * An abstract interface for diagram views. Diagram views can display diagrams in
 *  a coordinate system.
 *
 * @author Team 25
 * @version 1.0
 */
public abstract class DiagramView {

    /**
     * Display the given diagram in this view using the given paintboard.
     *
     * @param paintBoard The paintboard to use when displaying the view.
     * @param diagram The diagram that is to be displayed in this view.
     */
    public abstract void display(PaintBoard paintBoard, Diagram diagram);

    /**
     * Adds the given party to this view at the given coordinates.
     *
     * @param diagram The diagram the party is gonna be addded to.
     * @param party The party that is to be added.
     * @param x The x coordinate of the new party.
     * @param y The y coordinate of the new party.
     * @throws InvalidAddException The given party cannot be added to this diagram view at the given coordinate.
     */
    public abstract void addParty(Diagram diagram, Party party, int x, int y) throws InvalidAddException;

    /**
     * Registers the given party at the given coordinate.
     *
     * @param party The party to register.
     * @param x The x coordinate of the party that is to be registered.
     * @param y The y coordinate of the party that is to be registered.
     */
    public void registerParty(Party party, int x, int y) {
        coordinates = coordinates.plus(party, new Point(x,y));
    }

    /**
     * Returns a selectable diagram's component at given location, or null
     *  if no component is selectable at that coordinate.
     *
     * @param diagram The diagram whose components are to be considered.
     * @param x The x coordinate to look at.
     * @param y The y coordinate to look at.
     * @return The selectable diagram's component at given location in this view,
     *  or null if no such component is visible at the given coordinate.
     */
    public abstract DiagramComponent selectableComponentAt(Diagram diagram, int x, int y);

    /**
     * Returns the name of this diagram view as a string.
     *
     * @return This diagram view's name as a string.
     */
    public abstract String viewName();

    /**
     * Returns the coordinate of the given party for this view.
     *
     * @param party The party whose coordinate is desired.
     * @return The coordinate of the given party for this view, or null
     *  if the given party has no coordinate.
     */
    protected Point getCoordinate(Party party) {
        Point coordinate = coordinates.get(party);
        return (coordinate != null ? coordinate : getDefaultCoordinate());
    }

    /**
     * Returns the default coordinate for new parties added to this view.
     *
     * @return The default coordinate for new parties added to this view.
     */
    protected Point getDefaultCoordinate() {
        return new Point(0,0);
    }

    /**
     * A hashmap containing coordinates of all parties in this diagram view.
     */
    protected PMap<Party, Point> coordinates = PMap.<Party, Point>empty();

}