package interactr.cs.kuleuven.be.ui.control.diagram;

import interactr.cs.kuleuven.be.domain.*;
import interactr.cs.kuleuven.be.exceptions.*;
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
    void displayParties(PaintBoard paintBoard) {
        super.displayParties(paintBoard);
        paintBoard.setColour(Colour.GRAY);
        for (Party party : getDiagram().getParties()) {
            Figure partyFigure = getFigureForParty(party);
            Point partyCoordinates = getCoordinate(party);
            paintBoard.drawLine(partyCoordinates.getX() + partyFigure.getWidth() / 2,
                    PARTY_ROW_HEIGHT - 15,
                    partyCoordinates.getX() + partyFigure.getWidth() / 2,
                    getFrame().getHeight());
        }
        paintBoard.setColour(Colour.BLACK);
        paintBoard.drawLine(0, PARTY_ROW_HEIGHT - 15, paintBoard.getWidth(), PARTY_ROW_HEIGHT - 15);
    }

    @Override
    protected void displayMessages(PaintBoard paintBoard) {
        HashMap<Party, Integer> activations = new HashMap<>(); // Activations per party
        for (int i=0 ; i<getDiagram().getNbMessages() ; i++) {
            Message message = getDiagram().getMessageAtIndex(i);
            Integer associatedIndex = getDiagram().getIndexOfAssociatedMessage(i);
            Integer senderActivations = activations.get(message.getSender());
            Integer receiverActivations = (activations.get(message.getReceiver()) == null ? 0 : activations.get(message.getReceiver()));
            if (senderActivations == null || senderActivations  == 0) {
                drawActivationBar(paintBoard, message.getSender(), 0, i, associatedIndex);
                senderActivations = 1;
            }
            if (message.activates(message.getReceiver()))
                drawActivationBar(paintBoard, message.getReceiver(), receiverActivations++, i, associatedIndex);
            getLinkForMessage(message, senderActivations, receiverActivations).draw(paintBoard); // Order important
            if (!message.activates(message.getReceiver())) {
                if (message.getReceiver() == getDiagram().getInitiator() && receiverActivations == 1)
                    receiverActivations = 0;
                senderActivations--;
            }
            activations.put(message.getSender(), senderActivations);
            activations.put(message.getReceiver(), receiverActivations);
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
        drawActivationBar(paintBoard, getCoordinate(party).getX() + figure.getWidth()/2 + indentation * ACTIVATION_BAR_WIDTH/2, start, end);
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
    Point getCoordinate(Party party) {
        return new Point(super.getCoordinate(party).getX(), 5);
    }

    @Override
    boolean canAddMessageAt(int fromX, int fromY, int toX, int toY) {
        if (Math.abs(fromY - toY) > 8)
            return false;
        if (Math.min(fromY, toY) < PARTY_ROW_HEIGHT)
            return false;
        if (Math.min(fromY, toY) < PARTY_ROW_HEIGHT + diagram.getNbMessages() * MESSAGE_ROW_HEIGHT) {
            for (Message message : diagram.getMessages()) {
                int messageY = getYForMessage(message);
                if (Math.abs(messageY - fromY) < 4 || Math.abs(messageY - fromY) < 4)
                    return false;
            }
        }
        return true;
    }

    @Override
    public void addMessage(int fromX, int fromY, int toX, int toY) {
        if (isEditing()) throw new IllegalOperationException();
        if (canAddMessageAt(fromX, fromY, toX, toY)) {
            int index = getMessageInsertionIndex(fromX, fromY, toX, toY);
            Party sender = getParty(fromX, 10), receiver = getParty(toX, 10);
            if (sender != null && receiver != null && sender != receiver) {
                InvocationMessage message = new InvocationMessage(sender, receiver);
                diagram.insertInvocationMessageAtIndex(message, index);
                editComponent(message);
                return;
            }
        }
        throw new InvalidAddMessageException();
    }

    @Override
    int getMessageInsertionIndex(int fromX, int fromY, int toX, int toY) {
        for (int i=0 ; i<getDiagram().getNbMessages() ; i++) {
            int messageY = getYForMessage(getDiagram().getMessageAtIndex(i));
            if (messageY > fromY || messageY > fromY)
                return i;
        }
        return getDiagram().getNbMessages();
    }

    /**
     * Returns the y coordinate where the given message is drawn at.
     *
     * @param message The message whose y coordinate is desired.
     * @return The y coordinate where the given message is drawn at in this sequence view.
     */
    private int getYForMessage(Message message) {
        return PARTY_ROW_HEIGHT + MESSAGE_ROW_HEIGHT/2 + MESSAGE_ROW_HEIGHT * getDiagram().getIndexOfMessage(message);
    }

    @Override
    Line getLinkForMessage(Message message) {
        return getLinkForMessage(message, 0, 0);
    }

    /**
     * Generate a link for the given message with given activation data.
     *
     * @param message The message to generate a link for.
     * @param senderActivations The current # of activations for the message's sender.
     * @param receiverActivations The current # of activations for the message's receiver.
     * @return A link with correct coordinates for drawing the given message in this sequence view.
     */
    private Line getLinkForMessage(Message message, int senderActivations, int receiverActivations) {
        Line link = MessageModeller.defaultModeller().generateLink(message);
        int rowY = PARTY_ROW_HEIGHT + MESSAGE_ROW_HEIGHT/2 + getDiagram().getIndexOfMessage(message) * MESSAGE_ROW_HEIGHT;
        int senderWidth = getFigureForParty(message.getSender()).getWidth();
        int receiverWidth = getFigureForParty(message.getReceiver()).getWidth();
        boolean left = getCoordinate(message.getSender()).getX() > getCoordinate(message.getReceiver()).getX();
        link.getCoordinates().setY(rowY);
        link.getEndCoordinates().setY(rowY);
        link.getCoordinates().setX(getCoordinate(message.getSender()).getX() + senderWidth/2
            + senderActivations * ACTIVATION_BAR_WIDTH/2 - (left ? ACTIVATION_BAR_WIDTH : 0));
        link.getEndCoordinates().setX(getCoordinate(message.getReceiver()).getX() + receiverWidth/2
            + receiverActivations * ACTIVATION_BAR_WIDTH/2 - (left ? 0 : ACTIVATION_BAR_WIDTH));
        link.setLabel(getLabelOfComponent(message));
        link.setColour(getColourOfComponent(message));
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
        return "Sequence View";
    }

}