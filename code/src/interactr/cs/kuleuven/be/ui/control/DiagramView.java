package interactr.cs.kuleuven.be.ui.control;

import interactr.cs.kuleuven.be.domain.*;
import interactr.cs.kuleuven.be.exceptions.*;
import interactr.cs.kuleuven.be.purecollections.PList;
import interactr.cs.kuleuven.be.purecollections.PMap;
import interactr.cs.kuleuven.be.ui.DiagramObserver;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.design.*;
import interactr.cs.kuleuven.be.ui.geometry.Point;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

import java.util.Map;

/**
 * An abstract interface for diagram views. Diagram views can display diagrams in
 *  a coordinate system. They allow for manipulation and selection of diagram components.
 *
 * @author Team 25
 * @version 1.0
 */
public abstract class DiagramView implements Cloneable, DiagramObserver {

    /**
     * Initialize this new diagram view with the given diagram.
     *
     * @param diagram The diagram to associate this diagram view with.
     * @throws IllegalArgumentException If the given diagram is null.
     */
    DiagramView(Diagram diagram) {
        setDiagram(diagram);
    }

    /**
     * Returns the diagram associated with this diagram view.
     */
    public Diagram getDiagram() {
        return this.diagram;
    }

    /**
     * Sets the diagram of this diagram view to the given one.
     *
     * @param diagram The new diagram associated with this diagram view.
     * @throws IllegalArgumentException If the given diagram is null.
     */
    public void setDiagram(Diagram diagram) throws IllegalArgumentException {
        if (diagram == null)
            throw new IllegalArgumentException("Diagram cannot be null.");
        if (this.diagram != null)
            this.diagram.unregisterObserver(this);
        this.diagram = diagram;
        diagram.registerObserver(this);
    }

    /**
     * The diagram associated with this diagram view.
     */
    protected Diagram diagram;

    /**
     * Display the given diagram in this view using the given paintboard.
     *
     * @param paintBoard The paintboard to use when displaying the view.
     */
    public void display(PaintBoard paintBoard) {
        displayFigures(paintBoard);
        displayMessages(paintBoard);
    }

    /**
     * Display the figures of the given diagram diagram in this view using the given paint board.
     *
     * @param paintBoard The paintboard to use when displaying the view.
     */
    void displayFigures(PaintBoard paintBoard) {
        paintBoard.setColour(Colour.BLACK);
        for (Party party : getDiagram().getParties())
            getFigureForParty(party).draw(paintBoard);
    }

    /**
     * Display the messages of the given diagram diagram in this view using the given paintboard.
     *
     * @param paintBoard The paintboard to use when displaying the view.
     */
    void displayMessages(PaintBoard paintBoard) {
        for (Message message : getDiagram().getMessages())
            if (message.activates(message.getReceiver()))
                getLinkForMessage(message).draw(paintBoard);
    }

    /**
     * Creates a new party at the given coordinates in this view's diagram.
     *
     * @param x The x coordinate of the new party.
     * @param y The y coordinate of the new party.
     * @throws InvalidAddPartyException The given party cannot be added to this diagram view at the given coordinate.
     */
    public void addParty(int x, int y) throws InvalidAddPartyException {
        if (getParty(x,y) != null)
            throw new InvalidAddPartyException();
        Party newParty = Party.createParty();
        setCoordinateForParty(newParty, new Point(x,y));
        this.getDiagram().addParty(newParty);
    }

    /**
     * Switch the type of the party at given coordinates.
     *
     * @param x The x coordinate of the party.
     * @param y The y coordinate of the party.
     */
    public void switchTypeOfParty(int x, int y) {
        Party oldParty = getParty(x,y);
        if (oldParty != null) {
            Party newParty = oldParty.switchType();
            diagram.replaceParty(oldParty, newParty);
        }
    }

    /**
     * Returns a selectable diagram component at given location, or null
     *  if no component is selectable at that coordinate.
     *
     * @param x The x coordinate to look at.
     * @param y The y coordinate to look at.
     * @return The selectable diagram's component at given location in this view,
     *  or null if no such component is visible at the given coordinate.
     */
    public DiagramComponent getSelectableComponent(int x, int y) {
        for (Message message : getDiagram().getMessages())
            if (getLinkForMessage(message).isLabelHit(x,y))
                return message;
        for (Party party : getDiagram().getParties())
            if (getFigureForParty(party).isLabelHit(x,y))
                return party;
        return null;
    }

    /**
     * Returns the party at the given coordinate.
     *
     * @param x The x coordinate to look at.
     * @param y The y coordinate to look at.
     * @return The party at the given coordinate.
     */
    private Party getParty(int x, int y) {
        for (Party p : getDiagram().getParties()) {
            Figure figure = getFigureForParty(p);
            if (figure.isHit(x,y) && !figure.isLabelHit(x,y))
                return p;
        }
        return null;
    }

    /**
     * A method that moves the given party to the given coordinates
     *
     * @param fromX The start x coordinate for the add.
     * @param fromY The start y coordinate for the add.
     * @param toX The end x coordinate for the add.
     * @param toY The end y coordinate for the add.
     * @throws NoSuchPartyException If there is no party at the given start coordinates.
     * @throws InvalidMovePartyException If the party could not be moved to the given end coordinates.
     */
    public void moveParty(int fromX, int fromY, int toX, int toY) throws  NoSuchPartyException, InvalidMovePartyException {
        Party movedParty = getParty(fromX, fromY);
        if (movedParty == null)
            throw new NoSuchPartyException(fromX, fromY);
        Figure figure = getFigureForParty(movedParty);
        int newX = figure.getX() + (toX - fromX), newY = figure.getY() + (toY - fromY);
        figure.setX(newX);
        figure.setY(newY);
        for (Party party : getDiagram().getParties())
            if (party != movedParty && figure.getBounds().overlaps(getFigureForParty(party).getBounds()))
                throw new InvalidMovePartyException();
        setCoordinateForParty(movedParty, new Point(newX, newY));
    }

    /**
     * Checks whether a message can be added at the given coordinates.
     *
     * This method does not check for validity of the callstack after addition of the message
     *  to the diagram itself.
     *
     * @param fromX The start x coordinate for the message.
     * @param fromY The start y coordinate for the message.
     * @param toX The end x coordinate for the message.
     * @param toY The end y coordinate for the message.
     * @return True if and only if a message can be inserted at the given coordinates.
     */
    boolean canAddMessageAt(int fromX, int fromY, int toX, int toY) {
        return false;
    }

    /**
     * Returns the index of the message lying right above a message, if inserted at the the given coordinates.
     *
     * @param fromX The start x coordinate for the message to insert.
     * @param fromY The start x coordinate for the message to insert.
     * @param toX The start x coordinate for the message to insert.
     * @param toY The start x coordinate for the message to insert.
     * @return The index at which a message should be inserted if added at the given coordinates.
     */
    int getMessageInsertionIndex(int fromX, int fromY, int toX, int toY) {
        return -1;
    }

    /**
     * Add a message from the given start to the given end coordinates.
     *
     * @param fromX The start x coordinate for the message.
     * @param fromY The start y coordinate for the message.
     * @param toX The end x coordinate for the message.
     * @param toY The end y coordinate for the message.
     * @throws InvalidAddMessageException If a message could not be added.
     */
    public void addMessage(int fromX, int fromY, int toX, int toY) {
        if (canAddMessageAt(fromX, fromY, toX, toY)) {
            int index = getMessageInsertionIndex(fromX, fromY, toX, toY);
            Party sender = getParty(fromX, 10), receiver = getParty(toX, 10);
            diagram.insertInvocationMessageAtIndex(new InvocationMessage(sender, receiver), index);
        }
        else
            throw new InvalidAddMessageException();
    }

    /**
     * Notifies this diagram view that the given party was removed from the given diagram.
     *
     * @param diagram The diagram from which the component was removed.
     * @param party The party that was removed.
     */
    public void diagramDidDeleteParty(Diagram diagram, Party party) {
        removeCoordinateForParty(party);
    }

    /**
     * Notifies this diagram that the given party was added to the given diagram.
     *
     * @param diagram The diagram to which the party was added.
     * @param party The party to register.
     */
    public void diagramDidAddParty(Diagram diagram, Party party) {
        setCoordinateForParty(party, findEmptyCoordinateForParty(party));
    }

    /**
     * Registers a replacement of the given old party by the given new party.
     *
     * @param diagram The diagram in which the replacement occurred.
     * @param oldParty The old party to be replaced.
     * @param newParty The party to replace the old party with.
     */
    public void diagramDidReplaceParty(Diagram diagram, Party oldParty, Party newParty) {
        setCoordinateForParty(newParty, partyCoordinates.get(oldParty));
        removeCoordinateForParty(oldParty);
    }

    @Override
    public DiagramView clone() {
        final DiagramView clone;
        try {
            clone = getClass().getConstructor(Diagram.class).newInstance(diagram);
            for (Party party : partyCoordinates.keySet())
                clone.setCoordinateForParty(party, partyCoordinates.get(party));
        }
        catch (Exception e) {throw new RuntimeException("Failed to clone diagram view." + e.getClass().toString());}
        return clone;
    }

    /**
     * Registers flyweight links for messages to be used when drawing.
     */
    private static Map<Class<? extends Message>, Link> links = Map.of(
            InvocationMessage.class, new Arrow(),
            ResultMessage.class, new DashedArrow()
    );

    /**
     * Returns a link for the given message, to be used as a flyweight when drawing.
     *
     * @param message The message for which a link is desired. Cannot be null.
     * @return A link representing the given message.
     */
    Link getLinkForMessage(Message message) {
        Link link = links.get(message.getClass());
        Figure figure = getFigureForParty(message.getSender());
        Point senderCoordinate = getCoordinateForParty(message.getSender());
        Point receiverCoordinate = getCoordinateForParty(message.getReceiver());
        link.setStartX(senderCoordinate.getX() + (senderCoordinate.getX() < receiverCoordinate.getX() ? figure.getWidth() : 0));
        link.setStartY(senderCoordinate.getY() + figure.getHeight()/2);
        link.setEndX(receiverCoordinate.getX() + (senderCoordinate.getX() < receiverCoordinate.getX() ? 0 : figure.getWidth()));
        link.setEndY(receiverCoordinate.getY() + figure.getHeight()/2);
        link.setLabel(message.getLabel());
        return link;
    }

    /**
     * Registers flyweight figures for parties to be used when drawing.
     */
    private static Map<Class<? extends Party>, Figure> figures = Map.of(
            ActorParty.class, new StickFigure(),
            ObjectParty.class, new Box()
    );

    /**
     * Returns a figure for the given party, to be used as a flyweight when drawing.
     *
     * @param party The party for which a figure is desired. Cannot be null.
     * @return A figure representing the given party.
     */
    Figure getFigureForParty(Party party) {
        Figure figure = figures.get(party.getClass());
        Point coordinate = getCoordinateForParty(party);
        if (coordinate != null) {
            figure.setX(coordinate.getX());
            figure.setY(coordinate.getY());
            figure.setLabel(party.getLabel());
        }
        return figure;
    }

    /**
     * Determines a coordinate where a party can be placed.
     *
     * @return A point such that placing a party at that point would not make any parties overlap.
     */
    Point findEmptyCoordinateForParty(Party party) {
        Point coordinate = getCoordinateForParty(party);
        if (coordinate == null)
            coordinate = new Point(5, 5);
        Figure figure = getFigureForParty(party);
        Rectangle bounds = figure.getBounds();
        boolean overlaps = true;
        while (overlaps) {
            overlaps = false;
            for (Party p : getDiagram().getParties()) {
                if (p != party) {
                    Figure otherFigure = getFigureForParty(p);
                    if (bounds.overlaps(otherFigure.getBounds())) {
                        overlaps = true;
                        bounds.setX(Math.max(coordinate.getX(), otherFigure.getX() + otherFigure.getWidth() + 5));
                        break;
                    }
                }
            }
        }
        return new Point(bounds.getX(), coordinate.getY());
    }

    /**
     * Returns the coordinate for the given party or a default coordinate if none was registered.
     *
     * @param party The party whose coordinate is desired.
     * @return The coordinate for the given party.
     */
    private Point getCoordinateForParty(Party party) {
        return partyCoordinates.get(party);
    }

    /**
     * Set the given party's position to the given one.
     *
     * @param party The party whose position should be set.
     * @param position The position for the party.
     */
    private void setCoordinateForParty(Party party, Point position) {
        partyCoordinates = partyCoordinates.plus(party, position);
    }

    /**
     * Remove the coordinate entry for the given party.
     *
     * @param party The party whose coordinate should be removed from the registry.
     */
    private void removeCoordinateForParty(Party party) {
        partyCoordinates = partyCoordinates.minus(party);
    }

    /**
     * A hashmap containing coordinates of all parties in this diagram view.
     */
    PMap<Party, Point> partyCoordinates = PMap.empty();

    /**
     * Close this view.
     *  This unregisters it as an observer.
     */
    public void close() {
        diagram.unregisterObserver(this);
    }

}