package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.*;
import interactr.cs.kuleuven.be.purecollections.PMap;
import interactr.cs.kuleuven.be.ui.exceptions.InvalidAddPartyException;
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
    public abstract void display(PaintBoard paintBoard, Diagram diagram,SelectionManager selectionManager);

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
        if (!figures.containsKey(party))
            figures = figures.plus(party, figureForParty(party, x, y));
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
            figures = figures.plus(party, figureForParty(party, x, y));
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
     * A hashmap containing figures of all parties in this diagram view.
     */
    protected PMap<Party, Figure> figures = PMap.<Party, Figure>empty();

    /**
     * Creates a figure for the given party at the given coordinates.
     *
     * @param party The party to make a figure for.
     * @param x The x coordinate for the figure.
     * @param y The y coordinate for the figure.
     * @return A figure at given coordinates representing the given party.
     */
    protected Figure figureForParty(Party party, int x, int y) {
        Figure figure = new Box();
        Class figureType = party.proposedFigure();
        try {
            if (Figure.class.isAssignableFrom(figureType))
                figure = (Figure) figureType.getConstructor().newInstance();
        } catch (Exception e) {
            System.err.println("Invalid figure type given in custom party class.");
        }
        figure.setX(x);
        figure.setY(y);
        figure.setWidth(30);
        figure.setHeight(70);
        figure.setLabel(party.getLabel());
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
        for (Party p : figures.keySet())
            if (figures.get(p).isHit(x,y))
                return p;
        return null;
    }

    //TODO
    public void addMessage(int fromX, int fromY, int toX, int toY) {

    }

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
            System.err.println("Invalid link type given in custom message class.");
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

}