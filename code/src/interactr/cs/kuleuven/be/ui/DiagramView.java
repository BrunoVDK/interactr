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

    public void registerParty(Party party, int x, int y) {
        coordinates = coordinates.plus(party, new Point(x,y));
    }

    /**
     * Returns the given diagram's component at given location, or null
     *  if no component is shown at that coordinate.
     *
     * @param diagram The diagram whose components are to be considered.
     * @param x The x coordinate to look at.
     * @param y The y coordinate to look at.
     * @return The given diagram's component at given location in this view,
     *  or null if no component is visible at the given coordinate.
     */
    public abstract DiagramComponent componentAt(Diagram diagram, int x, int y);

    /**
     * Returns the name of this diagram view as a string.
     *
     * @return This diagram view's name as a string.
     */
    public abstract String viewName();

    /**
     * Returns the figure for representing the given party.
     *
     * @param party The party that is to be represented.
     * @return A figure representation of the given party.
     */
    protected Figure figureForParty(Party party) {
        Figure figure = null;
        if (party.isActor())
            figure = defaultActorFigure;
        else
            figure = defaultObjectFigure;
        Point coordinate = getCoordinate(party);
        figure.setX(coordinate.getX());
        figure.setY(coordinate.getY());
        return figure;
    }

    /**
     * Returns a text box for the given label.
     *
     * @param label
     * @param isActive
     * @return
     */
    protected TextBox textboxForLabel(Label label, boolean isActive) {
        return new TextBox(label.getValue() + (isActive ? "|" : ""));
    }

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

    // Default figures (recycled throughout)
    public static Figure defaultObjectFigure;
    public static Figure defaultActorFigure;

    /**
     * Generate default figures for parties.
     */
    static {

        defaultObjectFigure = new Box();
        defaultObjectFigure.setWidth(80);
        defaultObjectFigure.setHeight(50);

        defaultActorFigure = new StickFigure();
        defaultActorFigure.setWidth(20);
        defaultActorFigure.setHeight(50);

    }

}