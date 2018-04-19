package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.*;
import interactr.cs.kuleuven.be.exceptions.*;
import interactr.cs.kuleuven.be.purecollections.PList;
import interactr.cs.kuleuven.be.purecollections.PMap;
import interactr.cs.kuleuven.be.ui.geometry.*;

/**
 * An abstract interface for diagram views. Diagram views can display diagrams in
 *  a coordinate system. They allow for manipulation and selection of diagram components.
 *
 * @author Team 25
 * @version 1.0
 */
public class DiagramView implements Cloneable {

    /**
     * Initialize this new diagram view with the given diagram.
     *
     * @param diagram The diagram to associate this diagram view with.
     * @throws IllegalArgumentException If the given diagram is null.
     */
    public DiagramView(Diagram diagram) {
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
        this.diagram = diagram;
        DiagramNotificationCenter.defaultCenter().registerDiagramView(diagram, this);
    }

    /**
     * The diagram associated with this diagram view.
     */
    protected Diagram diagram;

    /**
     * Display the given diagram in this view using the given paintboard.
     *
     * @param paintBoard The paintboard to use when displaying the view.
     * @param selectedComponent The selected component, if any.
     * @param selectedLabel The temporary label of the selected component, if any.
     */
    public void display(PaintBoard paintBoard, DiagramComponent selectedComponent, String selectedLabel) {
        displayFigures(paintBoard, selectedComponent, selectedLabel);
        displayMessages(paintBoard, selectedComponent, selectedLabel);
    }

    /**
     * Display the figures of the given diagram diagram in this view using the given paint board.
     *
     * @param paintBoard The paintboard to use when displaying the view.
     * @param selectedComponent The selected component, if any.
     * @param selectedLabel The temporary label of the selected component, if any.
     */
    protected void displayFigures(PaintBoard paintBoard, DiagramComponent selectedComponent, String selectedLabel) {
        for (Party party : figures.keySet()) {
            boolean isSelected = (party == selectedComponent), isActive = (isSelected && selectedLabel != null);
            paintBoard.setColour((isSelected || isActive)
                    ? ((isActive && !selectedComponent.canHaveAsLabel(selectedLabel)) ? Colour.RED : Colour.BLUE)
                    : Colour.BLACK);
            Figure partyFigure = figureForParty(party);
            if (isActive)
                partyFigure.setLabel(selectedLabel + "|");
            partyFigure.draw(paintBoard);
        }
    }

    /**
     * Display the messages of the given diagram diagram in this view using the given paintboard.
     *
     * @param paintBoard The paintboard to use when displaying the view.
     * @param selectedComponent The selected component, if any.
     * @param selectedLabel The temporary label of the selected component, if any.
     */
    protected void displayMessages(PaintBoard paintBoard, DiagramComponent selectedComponent, String selectedLabel) {
        for (Message message : links.keySet()) {
            boolean isSelected = (message == selectedComponent), isActive = (isSelected && selectedLabel != null);
            paintBoard.setColour((isSelected || isActive)
                    ? ((isActive && !selectedComponent.canHaveAsLabel(selectedLabel)) ? Colour.RED : Colour.BLUE)
                    : Colour.BLACK);
            Link messageLink = linkForMessage(message);
            if (isActive)
                messageLink.setLabel(selectedLabel + "|");
            else
                messageLink.setLabel(diagram.getPrefix(message) + " " + messageLink.getLabel());
            messageLink.draw(paintBoard);
        }
    }

    /**
     * Creates a new party at the given coordinates in this view's diagram.
     *
     * @param x The x coordinate of the new party.
     * @param y The y coordinate of the new party.
     * @throws InvalidAddPartyException The given party cannot be added to this diagram view at the given coordinate.
     */
    public void addParty(int x, int y) throws InvalidAddPartyException {

        // Make sure there is nothing at the given coordinates
        for (Party p : figures.keySet()) {
            Figure figure = figureForParty(p);
            if (figure.isHit(x,y))
                throw new InvalidAddPartyException();
        }

        // Create the new party and throw notification
        Party party = Party.createParty();
        diagram.addParty(party);
        DiagramNotificationCenter.defaultCenter().diagramDidAddParty(diagram, party, new Point(x,y));

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
            DiagramNotificationCenter.defaultCenter().diagramDidReplaceParty(diagram, oldParty, newParty);
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
        for (Message message : links.keySet()) {
            if (linkForMessage(message).isLabelHit(x,y))
                return message;
        }
        for (Party party : figures.keySet()) {
            if (figureForParty(party).isLabelHit(x,y))
                return party;
        }
        return null;
    }

    /**
     * Returns the diagram component at given location, or null
     *  if no component is present at that coordinate.
     *
     * @param x The x coordinate to look at.
     * @param y The y coordinate to look at.
     * @return The diagram component at given location in this view,
     *  or null if no such component is visible at the given coordinate.
     */
    public DiagramComponent getComponent(int x, int y) {
        for (Message message : links.keySet()) {
            if (linkForMessage(message).isLabelHit(x,y))
                return message;
        }
        for (Party party : diagram.getParties()) {
            if (figureForParty(party).isHit(x,y))
                return party;
        }
        return null;
    }

    /**
     * Returns the diagram component at given location, or null if no component is present at that coordinate.
     *  The given excluded component, if not null, is ignored.
     *
     * @param x The x coordinate to look at.
     * @param y The y coordinate to look at.
     * @param excludedComponent The component to ignore when looking for the component at given coordinate.
     * @return The diagram component at given location in this view,
     *  or null if no such component is visible at the given coordinate.
     */
    protected DiagramComponent getComponent(int x, int y, DiagramComponent excludedComponent) {
        for (Party party : diagram.getParties()) {
            if (party != excludedComponent && figureForParty(party).isHit(x,y))
                return party;
        }
        return null;
    }

    /**
     * Returns the figure for the given party held by this view.
     *
     * @param party The party whose figure is desired.
     * @return The figure associated with the given party, or null if there is none.
     */
    protected Figure figureForParty(Party party) {
        if (party == null)
            return null;
        Figure figure = figures.get(party);
        if (figure != null) {
            figure.setLabel(party.getLabel());
        }
        return figure;
    }

    /**
     * Creates a figure for the given party at the given coordinates.
     *
     * @param party The party to make a figure for.
     * @param x The x coordinate for the figure.
     * @param y The y coordinate for the figure.
     * @return A figure at given coordinates representing the given party.
     */
    protected Figure createFigureForParty(Party party, int x, int y) {

        // Get the figure for the given party, if any
        Figure figure = figureForParty(party);
        if (figure != null) {
            figure.setLabel(party.getLabel());
            figure.setX(x);
            figure.setY(y);
        } else { // No figure has been made for the given party
            figure = party.proposedFigure();
            figure.setX(x);
            figure.setY(y);
            figure.setWidth(50);
            figure.setHeight(65);
            figure.setLabel(party.getLabel());
        }

        return figure;

    }

    /**
     * Returns the party at the given coordinate.
     *
     * @param x The x coordinate to look at.
     * @param y The y coordinate to look at.
     * @return The party at the given coordinate.
     */
    public Party getParty(int x, int y) {
        for (Party p : figures.keySet()) {
            Figure figure = figureForParty(p);
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
        if (getComponent(toX, toY, movedParty) != null)
            throw new InvalidMovePartyException();
        Figure figure = figures.get(movedParty);
        figure.setX(figure.getX() + (toX - fromX));
        figure.setY(figure.getY() + (toY - fromY));
    }

    /**
     * A hashmap containing figures of all parties in this diagram view.
     */
    protected PMap<Party, Figure> figures = PMap.<Party, Figure>empty();

    /**
     * Creates a link for the given message.
     *
     * @param message The message for which a link is created.
     * @return A link representing the given message in this view.
     */
    protected Link linkForMessage(Message message) {
        Link link = links.get(message);
        if (link != null) {
            Party sender = message.getSender(), receiver = message.getReceiver();
            Figure senderFigure = figures.get(sender), receiverFigure = figures.get(receiver);
            link.setStartX(senderFigure.getX() + (senderFigure.getX() < receiverFigure.getX() ? senderFigure.getWidth() : 0));
            link.setStartY(senderFigure.getY() + senderFigure.getHeight()/2);
            link.setEndX(receiverFigure.getX() + (senderFigure.getX() < receiverFigure.getX() ? 0 : receiverFigure.getWidth()));
            link.setEndY(receiverFigure.getY() + receiverFigure.getHeight()/2);
            link.setLabel(message.getLabel());
        }
        return link;
    }

    /**
     * Creates a link for the given message at the given coordinates.
     *
     * @param message The message to make a link for.
     * @param fromX The from x coordinate for the message.
     * @param fromY The from y coordinate for the message.
     * @param toX The to x coordinate for the message.
     * @param toY The to y coordinate for the message.
     * @return A link at given coordinates representing the given message.
     */
    protected Link createLinkForMessage(Message message, int fromX, int fromY, int toX, int toY) {
        Link link = message.proposedLink();
        Party sender = message.getSender(), receiver = message.getReceiver();
        Figure senderFigure = figures.get(sender), receiverFigure = figures.get(receiver);
        link.setStartX(senderFigure.getX() + (senderFigure.getX() < receiverFigure.getX() ? senderFigure.getWidth() : 0));
        link.setStartY(senderFigure.getY() + senderFigure.getHeight()/2);
        link.setEndX(receiverFigure.getX() + (receiverFigure.getX() < receiverFigure.getX() ? 0 : receiverFigure.getWidth()));
        link.setEndY(receiverFigure.getY() + receiverFigure.getHeight()/2);
        link.setLabel(message.getLabel());
        return link;
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
    public int getMessageInsertionIndex(int fromX, int fromY, int toX, int toY) {
        PList<Message> messages = diagram.getMessages();
        for (int i=0 ; i<messages.size() ; i++) {
            Link link = linkForMessage(messages.get(i));
            if (link.getStartY() > fromY || link.getEndY() > toY)
                return i;

        }
        return messages.size();
    }

    /**
     * Checks whether a message can be inserted at the given coordinates.
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
    public boolean canInsertMessageAt(int fromX, int fromY, int toX, int toY) {
        return false; // Default behaviour is inability to add anything
    }

    /**
     * Add the given message from the given start to the given end coordinates.
     *
     * @param message The message that is to be added.
     * @param fromX The start x coordinate for the message.
     * @param fromY The start y coordinate for the message.
     * @param toX The end x coordinate for the message.
     * @param toY The end y coordinate for the message.
     * @throws InvalidAddMessageException If a message could not be added.
     */
    public void addMessage(InvocationMessage message, int fromX, int fromY, int toX, int toY) {
        if (message != null && canInsertMessageAt(fromX, fromY, toX, toY)) {
            int index = getMessageInsertionIndex(fromX, fromY, toX, toY);
            diagram.insertInvocationMessageAtIndex(message, index);
            ResultMessage result = diagram.getResultMessageForInvocationMessage(message);
            DiagramNotificationCenter.defaultCenter().diagramDidAddMessages(diagram,
                    message, result, new Point(fromX, fromY), new Point(toX, toY));
        }
        else
            throw new InvalidAddMessageException();
    }

    /**
     * Returns the invocation message representing the given coordinates.
     *
     * @param fromX The start x coordinate for the message's link.
     * @param fromY The start y coordinate for the message's link.
     * @param toX The end x coordinate for the message's link.
     * @param toY The end y coordinate for the message's link.
     * @return A message whose link coincides with the given one.
     */
    public InvocationMessage getInvocationMessageForCoordinates(int fromX, int fromY, int toX, int toY) {
        return null;
    }

    /**
     * A hashmap containing links of all messages in this diagram view.
     */
    protected PMap<Message, Link> links = PMap.<Message, Link>empty();

    /**
     * Returns the name of this diagram view as a string.
     *
     * @return This diagram view's name as a string.
     */
    public String viewName() {
        return "Default";
    }

    /**
     * Notifies this diagram view that the given component was removed from the given diagram.
     *
     * @param diagram The diagram from which the component was removed.
     * @param component The component that was removed.
     */
    public void diagramDidDeleteComponent(Diagram diagram, DiagramComponent component) {
        PList<Message> diagramMessages = diagram.getMessages();
        for (Message message : links.keySet())
            if (!diagramMessages.contains(message))
                links = links.minus(message);
        PList<Party> diagramParties = diagram.getParties();
        for (Party p : figures.keySet())
            if (!diagramParties.contains(p))
                figures = figures.minus(p);
    }

    /**
     * Registers the given party at the given coordinate.
     *
     * @param diagram The diagram to which the party was added.
     * @param party The party to register.
     * @param coordinates The coordinates of the newly created party.
     */
    public void diagramDidAddParty(Diagram diagram, Party party, Point coordinates) {
        if (!figures.containsKey(party))
            figures = figures.plus(party, createFigureForParty(party, coordinates.getX(), coordinates.getY()));
    }

    /**
     * Registers a replacement of the given old party by the given new party.
     *
     * @param diagram The diagram in which the replacement occurred.
     * @param oldParty The old party to be replaced.
     * @param newParty The party to replace the old party with.
     */
    public void diagramDidReplaceParty(Diagram diagram, Party oldParty, Party newParty) {
        Figure oldFigure = figureForParty(oldParty);
        if (oldFigure == null)
            return;
        Figure newFigure = createFigureForParty(newParty, 0, 0);
        newFigure.setX(oldFigure.getX());
        newFigure.setY(oldFigure.getY());
        newFigure.setWidth(oldFigure.getWidth());
        newFigure.setHeight(oldFigure.getHeight());
        figures = figures.minus(oldParty);
        figures = figures.plus(newParty, newFigure);
    }

    /**
     * Registers the given invocation message and result message in this diagram view,
     *  using the given start/end coordinates.
     *
     * @param diagram The diagram to which the messages were added.
     * @param invocation The invocation message that is to be registered.
     * @param resultMessage The result message that is to be registered.
     * @param startCoordinates The start coordinates for the invocation message's link.
     * @param endCoordinates The end coordinates for the invocation message's link.
     */
    protected void diagramDidAddMessages(Diagram diagram, InvocationMessage invocation, ResultMessage resultMessage, Point startCoordinates, Point endCoordinates) {
        // Result messages are ignored!
        Link invocationLink = createLinkForMessage(invocation,
                startCoordinates.getX(),
                startCoordinates.getY(),
                endCoordinates.getX(),
                endCoordinates.getY());
        // Link resultLink = createLinkForMessage(resultMessage, fromX, fromY, toX, toY);
        links = links.plus(invocation, invocationLink);
        // links = links.plus(resultMessage, resultLink);
    }

    @Override
    public DiagramView clone() {
        final DiagramView clone;
        try {
            clone = getClass().getConstructor(Diagram.class).newInstance(diagram);
            for (Party party : figures.keySet())
                clone.registerFigure(party, figures.get(party));
            for (Message message : links.keySet())
                clone.registerLink(message, links.get(message));
        }
        catch (Exception ignored) {throw new RuntimeException("Failed to clone diagram view." + ignored.getClass().toString());}
        return clone;
    }

    /**
     * Register the given figure for the given party.
     *
     * @param party The party to register a figure for.
     * @param figure The figure that is to be registered.
     */
    protected void registerFigure(Party party, Figure figure) {
        if (figure == null)
            return;
        figures = figures.minus(party);
        figures = figures.plus(party, figure.clone());
    }

    /**
     * Register the given link for the given message.
     *
     * @param message The party to register a figure for.
     * @param link The figure that is to be registered.
     */
    protected void registerLink(Message message, Link link) {
        links = links.minus(message);
        links = links.plus(message, link.clone());
    }

    /**
     * Close this view.
     *  This unregisters it as an observer.
     */
    public void close() {
        DiagramNotificationCenter.defaultCenter().unregisterDiagramView(diagram, this);
    }

}