package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.*;
import interactr.cs.kuleuven.be.purecollections.PMap;
import interactr.cs.kuleuven.be.ui.exceptions.InvalidAddPartyException;
import interactr.cs.kuleuven.be.ui.geometry.*;

import java.awt.*;

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
    public void display(PaintBoard paintBoard, Diagram diagram, SelectionManager selectionManager) {
        displayFigures(paintBoard, diagram, selectionManager);
    }

    /**
     * Display the figures of the given diagram diagram in this view using the given paintboard.
     *
     * @param paintBoard The paintboard to use when displaying the view.
     * @param diagram The diagram that is to be displayed in this view.
     */
    protected void displayFigures(PaintBoard paintBoard, Diagram diagram, SelectionManager selectionManager) {
        for (Party party : figures.keySet()) {
            boolean isSelected = selectionManager.isSelected(party);
            boolean isActive = selectionManager.getActiveComponent() == party;
            paintBoard.setColor((isSelected || isActive ? Color.BLUE : Color.BLACK));
            Figure partyFigure = figureForParty(party);
            if (isActive)
                partyFigure.setLabel(selectionManager.getTemporaryLabel() + "|");
            partyFigure.draw(paintBoard);
            paintBoard.setColor(Color.BLACK);
        }
    }

    /**
     * Adds the given party to this view at the given coordinates.
     *
     * @param diagram The diagram the party is gonna be addded to.
     * @param party The party that is to be added.
     * @param x The x coordinate of the new party.
     * @param y The y coordinate of the new party.
     * @throws InvalidAddPartyException The given party cannot be added to this diagram view at the given coordinate.
     */
    public void addParty(Diagram diagram, Party party, int x, int y) throws InvalidAddPartyException {
        for (Party p : figures.keySet()) {
            Figure figure = figureForParty(p);
            if (figure.isHit(x,y))
                throw new InvalidAddPartyException();
        }
        if (!figures.containsKey(party))
            figures = figures.plus(party, createFigureForParty(party, x, y));
    }

    /**
     * Registers the given party at the given coordinate.
     *
     * @param party The party to register.
     * @param x The x coordinate of the party that is to be registered.
     * @param y The y coordinate of the party that is to be registered.
     */
    public void registerParty(Party party, int x, int y) {
        if (!figures.containsKey(party))
            figures = figures.plus(party, createFigureForParty(party, x, y));
    }

    /**
     * Notify this view of the fact that the given 'old' party was replaced
     *  with a 'new' party.
     *
     * @param oldParty The old party to be replaced.
     * @param newParty The party to replace the old party with.
     */
    public void registerPartyReplace(Party oldParty, Party newParty) {
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
     * Returns a selectable diagram's component at given location, or null
     *  if no component is selectable at that coordinate.
     *
     * @param diagram The diagram whose components are to be considered.
     * @param x The x coordinate to look at.
     * @param y The y coordinate to look at.
     * @return The selectable diagram's component at given location in this view,
     *  or null if no such component is visible at the given coordinate.
     */
    public DiagramComponent selectableComponentAt(Diagram diagram, int x, int y) {
        for (Party party : diagram.getParties()) {
            if (figureForParty(party).isLabelHit(x,y))
                return party;
        }
        return null;
    }

    /**
     * Returns the name of this diagram view as a string.
     *
     * @return This diagram view's name as a string.
     */
    public abstract String viewName();

    /**
     * Returns the figure for the given party held by this view.
     *
     * @param party The party whose figure is desired.
     * @return The figure associated with the given party, or null if there is none.
     */
    protected Figure figureForParty(Party party) {
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
        }
        else { // No figure has been made for the given party
            figure = new Box();
            Class figureType = party.proposedFigure();
            try {
                if (Figure.class.isAssignableFrom(figureType))
                    figure = (Figure) figureType.getConstructor().newInstance();
            } catch (Exception e) {
                System.out.println("Invalid figure type given in custom party class.");
            }
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
    public Party getPartyAt(int x, int y) {
        for (Party p : figures.keySet()) {
            Figure figure = figureForParty(p);
            if (figure.isHit(x,y) && !figure.isLabelHit(x,y))
                return p;
        }
        return null;
    }

    /**
     * A mehtod that moves the given party to the given coordinates
     *
     * @param party The party that has te be moved
     * @param x The new absolute x coordinate of the given Party
     * @param y The new absolut y coordinate of the given Party
     */
    public void moveParty(Party party, int x ,int y){
        figures.get(party).setX(x);
        figures.get(party).setY(y);
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
        Link link = new Arrow();
        Class linkType = message.proposedLinkType();
        try {
            if (Link.class.isAssignableFrom(linkType))
                link = (Link) linkType.getConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("Invalid link type given in custom message class.");
        }
        Party sender = message.getSender(), receiver = message.getReceiver();
        Figure senderFigure = figures.get(sender), receiverFigure = figures.get(receiver);
        /*
        if (senderFigure.getX() < receiverFigure.getX())
            link.setStartX(senderFigure.getX() + senderFigure.getWidth());
        else
            link.setStartX(receiverFigure.getX() + receiverFigure.getWidth());
        if (senderFigure.getY() < receiverFigure.getY())
            link.setStartX(senderFigure.getY() + senderFigure.getHeight());
        else
            link.setStartX(receiverFigure.getY() + receiverFigure.getHeight());
            */
        link.setStartX(senderFigure.getX() + senderFigure.getHeight());
        link.setStartY(senderFigure.getY() + senderFigure.getHeight()/2);
        link.setEndX(receiverFigure.getX());
        link.setEndY(receiverFigure.getY() + receiverFigure.getHeight()/2);
        link.setLabel(message.getLabel());
        return link;
    }

}