package interactr.cs.kuleuven.be.ui.control;

import interactr.cs.kuleuven.be.domain.*;
import interactr.cs.kuleuven.be.exceptions.InvalidAddPartyException;
import interactr.cs.kuleuven.be.exceptions.InvalidMovePartyException;
import interactr.cs.kuleuven.be.purecollections.PList;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.design.*;
import interactr.cs.kuleuven.be.ui.geometry.Point;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class of sequence diagram views. Sequence diagram views display diagrams
 *  as a composite of parties with messages sent between them.
 *  The parties are drawn in a top row, while the messages are drawn in the rows
 *  underneath this top row.
 *  Below each party, a lifeline is drawn.
 *
 * @author Team 25
 * @version 1.0
 */
public class SequenceView extends DiagramView {

    /**
     * Initialize this new diagram view with the given diagram.
     *
     * @param diagram The diagram to associate this diagram view with.
     * @throws IllegalArgumentException If the given diagram is null.
     */
    SequenceView(Diagram diagram) {
        super(diagram);
    }

    @Override
    void displayFigures(PaintBoard paintBoard) {
        super.displayFigures(paintBoard);
        paintBoard.setColour(Colour.GRAY);
        for (Party party : getDiagram().getParties()) {
            Figure partyFigure = getFigureForParty(party);
            paintBoard.drawLine(partyFigure.getX() + partyFigure.getWidth() / 2,
                    PARTY_ROW_HEIGHT,
                    partyFigure.getX() + partyFigure.getWidth() / 2,
                    paintBoard.getHeight());
        }
        paintBoard.setColour(Colour.BLACK);
        paintBoard.drawLine(0, PARTY_ROW_HEIGHT, paintBoard.getWidth(), PARTY_ROW_HEIGHT);
    }

    @Override
    protected void displayMessages(PaintBoard paintBoard) {

        HashMap<Party, List<ActivationBar>> activations = new HashMap<>();
        ArrayList<Link> links = new ArrayList<>();
        ArrayList<ActivationBar> bars = new ArrayList<>();

        for (int i=0 ; i<getDiagram().getNbMessages() ; i++) {

            Message message = getDiagram().getMessageAtIndex(i);
            Link link = getLinkForMessage(message);
            links.add(link);

            if (message.getSender() == getDiagram().getInitiator()) {
                Figure senderFigure = getFigureForParty(message.getSender());
                ActivationBar bar = new ActivationBar(senderFigure.getX() + senderFigure.getWidth()/2, i);
                ArrayList<ActivationBar> senderActivations = new ArrayList<>();
                senderActivations.add(bar);
                activations.put(message.getSender(), senderActivations);
            }

            List<ActivationBar> receiverActivations = activations.get(message.getReceiver());
            if (receiverActivations == null) {
                receiverActivations = new ArrayList<>();
                activations.put(message.getReceiver(), receiverActivations);
            }
            if (message.activates(message.getReceiver())) {
                Figure receiverFigure = getFigureForParty(message.getReceiver());
                ActivationBar bar = new ActivationBar(receiverFigure.getX() + receiverFigure.getWidth()/2, i);
                receiverActivations.add(0, bar);
            }
            else {
                ActivationBar bar = receiverActivations.remove(0);
                bar.end = i;
                bars.add(bar);
            }

        }

        for (ActivationBar bar : bars)
            drawActivationBar(paintBoard, bar);
        for (Link link : links)
            link.draw(paintBoard);

    }

    /**
     * Draw the given activation bar on the given paint board.
     *
     * @param paintBoard The paint board on which to draw.
     * @param bar The bar that is to be drawn.
     */
    private void drawActivationBar(PaintBoard paintBoard, ActivationBar bar) {
        paintBoard.setColour(ACTIVATION_COLOR);
        Rectangle drawRectangle = new Rectangle(
                bar.x - ACTIVATION_BAR_WIDTH/2,
                PARTY_ROW_HEIGHT + MESSAGE_ROW_HEIGHT/4 + MESSAGE_ROW_HEIGHT * bar.start,
                ACTIVATION_BAR_WIDTH,
                MESSAGE_ROW_HEIGHT * (bar.end - bar.start) + MESSAGE_ROW_HEIGHT/2);
        paintBoard.fillRectangle(drawRectangle.getX(), drawRectangle.getY(), drawRectangle.getWidth(), drawRectangle.getHeight());
        paintBoard.setColour(Colour.BLACK);
        paintBoard.drawRectangle(drawRectangle.getX(), drawRectangle.getY(), drawRectangle.getWidth(), drawRectangle.getHeight());
    }

    /**
     * Convenience class representing acivation bars for given parties, from given start to end rows.
     */
    private class ActivationBar {
        ActivationBar(int x, int start) {
            this.x = x;
            this.start = start;
        }
        int x, start, end;
    }

    @Override
    public void addParty(int x, int y) throws InvalidAddPartyException {
        if (y <= 5 || y >= PARTY_ROW_HEIGHT - 5)
            throw new InvalidAddPartyException();
        super.addParty(x, 5);
    }

    @Override
    public void moveParty(int fromX, int fromY, int toX, int toY) {
        if (fromY >= PARTY_ROW_HEIGHT)
            throw new InvalidMovePartyException();
        else
            super.moveParty(fromX, 5, toX, 5);
    }

    @Override
    boolean canAddMessageAt(int fromX, int fromY, int toX, int toY) {
        if (Math.abs(fromY - toY) > 8)
            return false;
        if (Math.min(fromY, toY) < PARTY_ROW_HEIGHT)
            return false;
        if (Math.min(fromY, toY) < PARTY_ROW_HEIGHT + diagram.getNbMessages() * MESSAGE_ROW_HEIGHT) {
            for (Message message : diagram.getMessages()) {
                Link messageLink = getLinkForMessage(message);
                if (Math.abs(messageLink.getStartY() - fromY) < 4 || Math.abs(messageLink.getEndY() - fromY) < 4)
                    return false;
            }
        }
        return true;
    }

    @Override
    int getMessageInsertionIndex(int fromX, int fromY, int toX, int toY) {
        PList<Message> messages = diagram.getMessages();
        for (int i=0 ; i<messages.size() ; i++) {
            Link link = getLinkForMessage(messages.get(i));
            if (link.getStartY() > fromY || link.getEndY() > fromY)
                return i;
        }
        return messages.size();
    }

    @Override
    Link getLinkForMessage(Message message) {
        Link link = super.getLinkForMessage(message);
        Figure senderFigure = getFigureForParty(message.getSender()), receiverFigure = getFigureForParty(message.getReceiver());
        int rowY = PARTY_ROW_HEIGHT + MESSAGE_ROW_HEIGHT/2 + getDiagram().getIndexOfMessage(message) * MESSAGE_ROW_HEIGHT;
        link.setStartY(rowY);
        link.setEndY(rowY);
        int midX = senderFigure.getX() + senderFigure.getWidth()/2;
        if (link.getStartX() > link.getEndX()) {
            link.setStartX(midX + ACTIVATION_BAR_WIDTH/2 - ACTIVATION_BAR_WIDTH);
            link.setEndX(midX - ACTIVATION_BAR_WIDTH/2 - ACTIVATION_BAR_WIDTH);
        }
        else {
            link.setStartX(midX - ACTIVATION_BAR_WIDTH/2 - ACTIVATION_BAR_WIDTH);
            link.setEndX(midX + ACTIVATION_BAR_WIDTH/2 - ACTIVATION_BAR_WIDTH);
        }
        link.setLabel(message.getLabel());
        return link;
    }

    /**
     * The height of the party row.
     */
    private static final int PARTY_ROW_HEIGHT = 75;

    /**
     * The height of each message row.
     */
    private static final int MESSAGE_ROW_HEIGHT = 20;

    /**
     * The width of activation bars.
     */
    private static final int ACTIVATION_BAR_WIDTH = 10;

    /**
     * The color used to draw activation bars.
     */
    private static final Colour ACTIVATION_COLOR = new Colour(216/360f, 35/360f, 0.66f);

    @Override
    public String toString() {
        return "Sequence View";
    }

}