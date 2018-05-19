package interactr.cs.kuleuven.be.ui.control.diagram;

import interactr.cs.kuleuven.be.domain.*;
import interactr.cs.kuleuven.be.exceptions.*;
import interactr.cs.kuleuven.be.purecollections.PMap;
import interactr.cs.kuleuven.be.domain.DiagramObserver;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.command.Command;
import interactr.cs.kuleuven.be.ui.command.CommandHandler;
import interactr.cs.kuleuven.be.ui.command.CommandNotProcessedException;
import interactr.cs.kuleuven.be.ui.design.*;
import interactr.cs.kuleuven.be.ui.geometry.*;

/**
 * An abstract interface for diagram views. Diagram views can display diagrams in
 *  a coordinate system. They allow for manipulation and selection of diagram components.
 *
 * @author Team 25
 * @version 1.0
 */
public abstract class DiagramView implements Cloneable, CommandHandler, DiagramObserver {

    /**
     * Initialize this new diagram view with the given diagram and given frame.
     *
     * @param diagram The diagram to associate this diagram view with.
     * @param frame The frame for this new view.
     * @throws IllegalArgumentException If the given diagram is null.
     */
    DiagramView(Diagram diagram, Rectangle frame) {
        setDiagram(diagram);
        this.frame = frame;
    }

    /**
     * Returns the frame for this view.
     */
    Rectangle getFrame() {
        return this.frame;
    }

    /**
     * Converts the given absolute coordinates to coordinates relative to this view's frame.
     *
     * @param absoluteCoordinates The coordinates to convert.
     * @return The same coordinates relative to this view's frame.
     */
    public Point getRelativeCoordinates(Point absoluteCoordinates) {
        return new Point(absoluteCoordinates.getX() - getFrame().getX(), absoluteCoordinates.getY() - getFrame().getY());
    }

    /**
     * Sets the frame of this view to the given one.
     *
     * @param frame The new frame for this view.
     */
    public void setFrame(Rectangle frame) {
        boolean widthChanged = this.frame.getWidth() != frame.getWidth(), heightChanged = this.frame.getHeight() != frame.getHeight();
        if (widthChanged || heightChanged) {
            for (Party party : getDiagram().getParties()) {
                Point coordinate = getCoordinateForParty(party);
                setCoordinateForParty(party, new Point(
                        coordinate.getX() + (widthChanged ? this.frame.getX() - frame.getX() : 0),
                        coordinate.getY() + (heightChanged ? this.frame.getY() - frame.getY() : 0)));
            }
        }
        this.frame = frame;
    }

    /**
     * Registers the frame of this view.
     */
    private Rectangle frame;

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
        paintBoard.setColour(Colour.BLACK);
        displayParties(paintBoard);
        displayMessages(paintBoard);
    }

    /**
     * Display the parties of the given diagram in this view using the given paint board.
     *
     * @param paintBoard The paintboard to use when displaying the parties.
     */
    void displayParties(PaintBoard paintBoard) {
        for (Party party : getDiagram().getParties()) {
            Figure figure = getFigureForParty(party);
            figure.draw(paintBoard);
        }
    }

    /**
     * Display the messages of the given diagram diagram in this view using the given paintboard.
     *
     * @param paintBoard The paintboard to use when displaying the messages.
     */
    void displayMessages(PaintBoard paintBoard) {
        for (Message message : getDiagram().getMessages())
            if (message.activates(message.getReceiver()))
                getLinkForMessage(message).draw(paintBoard);
    }

    /**
     * Selects the diagram component at given location, or null
     *  if no component is selectable at that coordinate.
     *
     * @param x The x coordinate for the component to select.
     * @param y The y coordinate for the component to select.
     */
    public void selectComponentAt(int x, int y) throws NoSuchComponentException {
        if (isEditing()) throw new IllegalOperationException();
        selectComponent(getSelectableComponent(x,y));

    }

    /**
     * Returns the selectable component at given coordinates, or null if there is none.
     *
     * @param x The x coordinate to search at.
     * @param y The y coordinate to search at.
     */
    public DiagramComponent getSelectableComponent(int x, int y) {
        for (Message message : getDiagram().getMessages())
            if (getLinkForMessage(message).isLabelHit(x,y))
                return message;
        for (Party party : getDiagram().getParties())
            if (getFigureForParty(party).isLabelHit(x,y))
                return party;
        throw new NoSuchComponentException();
    }

    /**
     * Selects the given component.
     *
     * @param component The component to select.
     */
    private void selectComponent(DiagramComponent component) {
        if (component == getSelectedComponent())
            editComponent(component);
        setSelectedComponent(component);
        setSelectedLabel(component.getLabel());
    }

    /**
     * Return true if and only if the current selection of this diagram view is active.
     */
    public boolean isEditing() {
        return isEditing;
    }

    /**
     * Sets whether or not the current selection is in edit mode.
     *
     * @param isEditing A flag denoting whether or not edit mode should be activated.
     * @throws InvalidLabelException If the current edit session could not be aborted because the label is invalid.
     */
    public void setIsEditing(boolean isEditing) throws InvalidLabelException {
        if (!isEditing && !isValidSelectedLabel())
            throw new InvalidLabelException();
        this.isEditing = isEditing;
        if (!isEditing)
            setSelectedComponent(null);
    }

    /**
     * Registers whether or not the current selection is active.
     */
    private boolean isEditing = false;

    /**
     * Sets the label of the diagram component currently selected by this view.
     *
     * @param label The new label for the selected diagram component.
     */
    public void setSelectedLabel(String label) {
        if (getSelectedComponent() != null && isEditing()) {
            selectedLabel = label;
            try {getDiagram().setLabelOfComponent(getSelectedComponent(), label);}
            catch (InvalidLabelException ignored) {}
        }
    }

    /**
     * Checks whether or not the selected label is valid.
     *
     * @return True if and only if no component is currently selected or the component can have the given label as
     *  its label.
     */
    private boolean isValidSelectedLabel() {
        return (getSelectedComponent() == null || getSelectedComponent().canHaveAsLabel(getSelectedLabel()));
    }

    /**
     * Returns the label for the active selection.
     *
     * @return The label for the active selection or an empty string if nothing is selected.
     */
    public String getSelectedLabel() {
        return (selectedComponent == null ? "" : selectedLabel);
    }

    /**
     * Registers the label of the selected component.
     */
    private String selectedLabel = "";

    /**
     * Delete the selected component from this diagram view's diagram.
     */
    public void deleteSelectedComponent() {
        if (getSelectedComponent() != null)
            getSelectedComponent().deleteFrom(getDiagram());
    }

    /**
     * Returns the diagram component selected by this view.
     */
    public DiagramComponent getSelectedComponent() {
        return selectedComponent;
    }

    /**
     * Sets the selected component of this diagram view to the given one.
     *
     * @param component The new selected component of this diagram view.
     */
    public void setSelectedComponent(DiagramComponent component) {
        this.selectedComponent = component;
    }

    /**
     * Starts an edit session for the given component.
     *
     * @param component The component that is to be edited.
     */
    void editComponent(DiagramComponent component) {
        this.selectedComponent = component;
        setIsEditing(true);
        setSelectedLabel("");
    }

    /**
     * Registers the selected component for this diagram view.
     */
    private DiagramComponent selectedComponent = null;

    /**
     * Creates a new party at the given coordinates in this view's diagram.
     *
     * @param x The x coordinate of the new party.
     * @param y The y coordinate of the new party.
     * @throws InvalidAddPartyException The given party cannot be added to this diagram view at the given coordinate.
     */
    public void addParty(int x, int y) throws InvalidAddPartyException {
        if (isEditing()) throw new IllegalOperationException();
        if (getParty(x,y,true) != null) throw new InvalidAddPartyException();
        Party newParty = Party.createParty();
        Rectangle bounds = PartyModeller.defaultModeller().generateFigure(newParty).getBounds();
        setCoordinateForParty(newParty, new Point(x - bounds.getWidth()/2, y - bounds.getHeight()/2));
        this.getDiagram().addParty(newParty);
        editComponent(newParty);
    }

    /**
     * Switch the type of the party at given coordinates.
     *
     * @param x The x coordinate of the party.
     * @param y The y coordinate of the party.
     */
    public void switchTypeOfParty(int x, int y) throws NoSuchPartyException {
        if (isEditing()) throw new IllegalOperationException();
        Party oldParty = getParty(x,y,false);
        if (oldParty != null) {
            Party newParty = oldParty.switchType();
            diagram.replaceParty(oldParty, newParty);
            makeRoomForParty(newParty);
        }
        else
            throw new NoSuchPartyException(x, y);
    }

    /**
     * Returns the party at the given coordinate.
     *
     * @param x The x coordinate to look at.
     * @param y The y coordinate to look at.
     * @return The party at the given coordinate.
     */
    public Party getParty(int x, int y) {
        return getParty(x,y,false);
    }

    /**
     * Returns the party at the given coordinate.
     *
     * @param x The x coordinate to look at.
     * @param y The y coordinate to look at.
     * @param labelIncluded Flag denoting whether or not the label is included in the search.
     * @return The party at the given coordinate.
     */
    private Party getParty(int x, int y, boolean labelIncluded) {
        for (Party p : getDiagram().getParties()) {
            Figure figure = getFigureForParty(p);
            if (figure.isHit(x,y) && !(!labelIncluded && figure.isLabelHit(x,y)))
                return p;
        }
        return null;
    }

    /**
     * A method that moves the given party to the given coordinates
     *
     * @param movedParty The that is to be moved.
     * @param toX The end x coordinate for the moving session.
     * @param toY The end y coordinate for the moving session.
     * @throws InvalidMovePartyException If the give party is null.
     * @throws InvalidMovePartyException If the party could not be moved to the given end coordinates.
     */
    public void moveParty(Party movedParty, int toX, int toY) throws  NoSuchPartyException, InvalidMovePartyException {
        if (isEditing()) throw new IllegalOperationException();
        if (movedParty == null) throw new InvalidMovePartyException();
        Rectangle movedBounds = getFigureForParty(movedParty).getBounds();
        movedBounds.setX(toX - movedBounds.getWidth()/2);
        movedBounds.setY(toY - movedBounds.getHeight()/2);
        for (Party party : getDiagram().getParties())
            if (party != movedParty && movedBounds.overlaps(getFigureForParty(party).getBounds()))
                throw new InvalidMovePartyException();
        setCoordinateForParty(movedParty, new Point(movedBounds.getX(), movedBounds.getY()));
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
        throw new InvalidAddMessageException();
    }

    @Override
    public void diagramDidDeleteParty(Diagram diagram, Party party) {
        removeCoordinateForParty(party);
    }

    @Override
    public void diagramDidAddParty(Diagram diagram, Party party) {
        makeRoomForParty(party);
    }

    @Override
    public void diagramDidReplaceParty(Diagram diagram, Party oldParty, Party newParty) {
        setCoordinateForParty(newParty, partyCoordinates.get(oldParty));
        removeCoordinateForParty(oldParty);
    }

    @Override
    public void diagramDidEditLabel(Diagram diagram, DiagramComponent component) {
        if (getSelectedComponent() == component)
            selectedLabel = component.getLabel();
    }

    /**
     * Returns a link for the given message, to be used as a flyweight when drawing.
     *
     * @param message The message for which a link is desired. Cannot be null.
     * @return A link representing the given message.
     */
    Link getLinkForMessage(Message message) {
        Link link = MessageModeller.defaultModeller().generateLink(message);
        Rectangle senderBounds = getFigureForParty(message.getSender()).getBounds();
        Rectangle receiverBounds = getFigureForParty(message.getReceiver()).getBounds();
        Point senderCoordinate = getCoordinateForParty(message.getSender());
        Point receiverCoordinate = getCoordinateForParty(message.getReceiver());
        link.setStartX(senderCoordinate.getX() + (senderCoordinate.getX() < receiverCoordinate.getX() ? senderBounds.getWidth() : 0));
        link.setStartY(senderCoordinate.getY() + senderBounds.getHeight()/2);
        link.setEndX(receiverCoordinate.getX() + (senderCoordinate.getX() < receiverCoordinate.getX() ? 0 : receiverBounds.getWidth()));
        link.setEndY(receiverCoordinate.getY() + receiverBounds.getHeight()/2);
        link.setColour(getColourOfComponent(message));
        link.setLabel(getDiagram().getPrefix(message) + " " + getLabelOfComponent(message));
        return link;
    }

    /**
     * Returns a figure for the given party, to be used as a flyweight when drawing.
     *
     * @param party The party for which a figure is desired. Cannot be null.
     * @return A figure representing the given party.
     */
    Figure getFigureForParty(Party party) {
        Figure figure = PartyModeller.defaultModeller().generateFigure(party);
        Point coordinate = getCoordinateForParty(party);
        figure.setX(coordinate.getX());
        figure.setY(coordinate.getY());
        figure.setColour(getColourOfComponent(party));
        figure.setLabel(getLabelOfComponent(party));
        return figure;
    }

    /**
     * Returns the label of the given component.
     *
     * @param component The component whose label is desired.
     * @return The label of the component, or the selected label if the component is selected.
     */
    String getLabelOfComponent(DiagramComponent component) {
        if (component == getSelectedComponent() && isEditing())
            return (selectedLabel + (isEditing() ? "|" : ""));
        return component.getLabel();
    }

    /**
     * Returns the colour to use when drawing the given component.
     *
     * @param component The component whose drawing colour is desired.
     * @return The colour to be used for drawing the component.
     */
    Colour getColourOfComponent(DiagramComponent component) {
        if (component == getSelectedComponent())
            return (isValidSelectedLabel() || !isEditing() ? Colour.BLUE : Colour.RED);
        return Colour.BLACK;
    }

    /**
     * Make room for the given party.
     *  This method shifts all party figures such that the given party's figure is in an empty spot.
     *
     * @param party The figure to make room for.
     */
    private void makeRoomForParty(Party party) {
        Rectangle partyBounds = getFigureForParty(party).getBounds(); // Necessary because of flyweight
        for (Party otherParty : diagram.getParties()) {
            if (otherParty != party) {
                Rectangle otherBounds = getFigureForParty(otherParty).getBounds();
                if (otherBounds.overlaps(partyBounds)) {
                    if (partyBounds.getX() > otherBounds.getX())
                        setCoordinateForParty(otherParty, new Point(partyBounds.getX() - otherBounds.getWidth() + 5, otherBounds.getY()));
                    else
                        setCoordinateForParty(otherParty, new Point(partyBounds.getX() + partyBounds.getWidth() + 5, otherBounds.getY()));
                    makeRoomForParty(otherParty); // Recursive call (keep shifting)
                }
            }
        }
    }

    /**
     * Returns the coordinate for the given party or a default coordinate if none was registered.
     *
     * @param party The party whose coordinate is desired.
     * @return The coordinate for the given party.
     */
    Point getCoordinateForParty(Party party) {
        if (partyCoordinates.get(party) == null)
            setCoordinateForParty(party, new Point(5,5));
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
    private PMap<Party, Point> partyCoordinates = PMap.empty();

    /**
     * Close this view.
     *  This unregisters it as an observer.
     */
    public void close() {
        isClosed = true;
        getDiagram().unregisterObserver(this);
    }

    /**
     * Returns whether or not this subwindow is closed.
     */
    public boolean isClosed() {
        return isClosed;
    }

    /**
     * Registers whether or not this subwindow is closed.
     */
    private boolean isClosed = false;

    @Override
    public void executeCommand(Command command) throws CommandNotProcessedException {
        command.executeDiagramView(this);
    }

    @Override
    public String toString() {
        return "Diagram " + getDiagram().getSequenceNumber();
    }

    @Override
    public DiagramView clone() {
        final DiagramView clone;
        try {
            clone = (DiagramView)super.clone();
            clone.setDiagram(getDiagram());
            for (Party party : partyCoordinates.keySet())
                clone.setCoordinateForParty(party, getCoordinateForParty(party));
        }
        catch (Exception e) {throw new RuntimeException("Failed to clone diagram view." + e.getClass().toString());}
        return clone;
    }

    /**
     * Synchronize this view with the given one.
     *
     * @param other The view to synchronize with.
     */
    public void synchronizeWith(DiagramView other) {
        if (other == this)
            return;
        selectedComponent = other.selectedComponent;
        selectedLabel = other.selectedLabel;
        isEditing = other.isEditing;
    }

}