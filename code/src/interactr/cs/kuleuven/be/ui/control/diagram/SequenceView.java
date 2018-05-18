package interactr.cs.kuleuven.be.ui.control.diagram;

import interactr.cs.kuleuven.be.domain.*;
import interactr.cs.kuleuven.be.exceptions.InvalidAddMessageException;
import interactr.cs.kuleuven.be.exceptions.InvalidAddPartyException;
import interactr.cs.kuleuven.be.exceptions.InvalidMovePartyException;
import interactr.cs.kuleuven.be.exceptions.NoSuchPartyException;
import interactr.cs.kuleuven.be.ui.PaintBoard;
import interactr.cs.kuleuven.be.ui.design.*;
import interactr.cs.kuleuven.be.ui.geometry.Point;
import interactr.cs.kuleuven.be.ui.geometry.Rectangle;

import java.util.HashMap;

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
     * @param frame The frame for this new view.
     * @throws IllegalArgumentException If the given diagram is null.
     */
    public SequenceView(Diagram diagram, Rectangle frame) {
        super(diagram, frame);
    }

    @Override
    void displayFigures(PaintBoard paintBoard) {
        super.displayFigures(paintBoard);
        paintBoard.setColour(Colour.GRAY);
        for (Party party : getDiagram().getParties()) {
            Figure partyFigure = getFigureForParty(party);
            paintBoard.drawLine(partyFigure.getX() + partyFigure.getWidth() / 2,
                    PARTY_ROW_HEIGHT - 15,
                    partyFigure.getX() + partyFigure.getWidth() / 2,
                    getFrame().getHeight());
        }
        paintBoard.setColour(Colour.BLACK);
        paintBoard.drawLine(0, PARTY_ROW_HEIGHT - 15, paintBoard.getWidth(), PARTY_ROW_HEIGHT - 15);
    }

    @Override
    protected void displayMessages(PaintBoard paintBoard) {
        HashMap<Party, Integer> activations = new HashMap<>();
        for (int i=0 ; i<getDiagram().getNbMessages() ; i++) {
            Message message = getDiagram().getMessageAtIndex(i);
            Link link = getLinkForMessage(message);
            Integer associatedIndex = getDiagram().getIndexOfAssociatedMessage(i);
            Integer senderActivations = (activations.get(message.getSender()) == null ? 0 : activations.get(message.getSender()));
            Integer receiverActivations = (activations.get(message.getReceiver()) == null ? 0 : activations.get(message.getReceiver()));
            if (senderActivations == null || senderActivations  == 0) {
                drawActivationBar(paintBoard, message.getSender(), 0, i, associatedIndex);
                senderActivations = 1;
            }
            if (message.activates(message.getReceiver())) {
                drawActivationBar(paintBoard, message.getReceiver(), receiverActivations, i, associatedIndex);
                receiverActivations++;
            }
            else {
                if (message.getReceiver() == getDiagram().getInitiator() && receiverActivations == 1) {
                    receiverActivations = 0;
                    link.setEndX(link.getEndX() + ACTIVATION_BAR_WIDTH/2);
                }
                else link.setStartX(link.getStartX() + ACTIVATION_BAR_WIDTH/2);
                senderActivations--;
            }
            activations.put(message.getSender(), senderActivations);
            activations.put(message.getReceiver(), receiverActivations);
            link.setStartX(link.getStartX() + senderActivations * ACTIVATION_BAR_WIDTH/2);
            link.setEndX(link.getEndX() + receiverActivations * ACTIVATION_BAR_WIDTH/2);
            link.draw(paintBoard);
        }
    }

    /**
     * Draw an activation bar for the given party, using given indentation and start/end indices.
     *
     * @param paintBoard The painboard to draw on.
     * @param party The party to draw an activation bar for.
     * @param indentation Indentation of the activation bar.
     * @param start Start index.
     * @param end End index (should be bigger than start index).
     */
    private void drawActivationBar(PaintBoard paintBoard, Party party, int indentation, int start, int end) {
        Figure figure = getFigureForParty(party);
        drawActivationBar(paintBoard, figure.getX() + figure.getWidth()/2 + indentation * ACTIVATION_BAR_WIDTH/2, start, end);
    }

    /**
     * Draw the given activation bar on the given paint board.
     *
     * @param paintBoard The paint board on which to draw.
     * @param x The middle x coordinate of the bar that is to be drawn.
     * @param startRow The row in which to start the bar.
     * @param endRow The row in which to end the bar.
     */
    private void drawActivationBar(PaintBoard paintBoard, int x, int startRow, int endRow) {
        paintBoard.setColour(Colour.DARK_GRAY);
        Rectangle drawRectangle = new Rectangle(
                x - ACTIVATION_BAR_WIDTH/2,
                PARTY_ROW_HEIGHT + MESSAGE_ROW_HEIGHT/4 + MESSAGE_ROW_HEIGHT * startRow,
                ACTIVATION_BAR_WIDTH,
                MESSAGE_ROW_HEIGHT * (endRow - startRow) + MESSAGE_ROW_HEIGHT/2);
        paintBoard.fillRectangle(drawRectangle.getX(), drawRectangle.getY(), drawRectangle.getWidth(), drawRectangle.getHeight());
        paintBoard.setColour(Colour.BLACK);
        paintBoard.drawRectangle(drawRectangle.getX(), drawRectangle.getY(), drawRectangle.getWidth(), drawRectangle.getHeight());
    }

    @Override
    public void addParty(int x, int y) throws InvalidAddPartyException {
        if (y <= 5 || y >= PARTY_ROW_HEIGHT - 5)
            throw new InvalidAddPartyException();
        super.addParty(x, y);
    }

    @Override
    public void moveParty(Party movedParty, int toX, int toY) throws NoSuchPartyException, InvalidMovePartyException {
        if (toY <= 5 || toY >= PARTY_ROW_HEIGHT - 5)
            throw new InvalidMovePartyException();
        super.moveParty(movedParty, toX, toY);
    }

    @Override
    Point getCoordinateForParty(Party party) {
        return new Point(super.getCoordinateForParty(party).getX(), 5);
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
    public void addMessage(int fromX, int fromY, int toX, int toY) {
        if (canAddMessageAt(fromX, fromY, toX, toY)) {
            int index = getMessageInsertionIndex(fromX, fromY, toX, toY);
            Party sender = getParty(fromX, 10), receiver = getParty(toX, 10);
            if (sender != null && receiver != null && sender != receiver) {
                diagram.insertInvocationMessageAtIndex(new InvocationMessage(sender, receiver), index);
                return;
            }
        }
        throw new InvalidAddMessageException();
    }

    @Override
    int getMessageInsertionIndex(int fromX, int fromY, int toX, int toY) {
        for (int i=0 ; i<getDiagram().getNbMessages() ; i++) {
            Link link = getLinkForMessage(getDiagram().getMessageAtIndex(i));
            if (link.getStartY() > fromY || link.getEndY() > fromY)
                return i;
        }
        return getDiagram().getNbMessages();
    }

    @Override
    Link getLinkForMessage(Message message) {
        Link link = MessageModeller.defaultModeller().generateLink(message);
        int rowY = PARTY_ROW_HEIGHT + MESSAGE_ROW_HEIGHT/2 + getDiagram().getIndexOfMessage(message) * MESSAGE_ROW_HEIGHT;
        link.setStartY(rowY);
        link.setEndY(rowY);
        link.setStartX(getFigureForParty(message.getSender()).getCenter().getX());
        link.setEndX(getFigureForParty(message.getReceiver()).getCenter().getX());
        if (link.getStartX() > link.getEndX())
            link.setStartX(link.getStartX() - ACTIVATION_BAR_WIDTH);
        else
            link.setEndX(link.getEndX() - ACTIVATION_BAR_WIDTH);
        link.setLabel(message.getLabel());
        return link;
    }

    /**
     * The height of the party row.
     */
    private static final int PARTY_ROW_HEIGHT = 90;

    /**
     * The height of each message row.
     */
    private static final int MESSAGE_ROW_HEIGHT = 30;

    /**
     * The width of activation bars.
     */
    private static final int ACTIVATION_BAR_WIDTH = 10;

    @Override
    public String toString() {
        return super.toString() + " - Sequence View";
    }

}