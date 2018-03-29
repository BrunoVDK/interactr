package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.*;
import interactr.cs.kuleuven.be.exceptions.InvalidAddPartyException;
import interactr.cs.kuleuven.be.purecollections.PList;
import interactr.cs.kuleuven.be.purecollections.PMap;
import interactr.cs.kuleuven.be.ui.geometry.Arrow;
import interactr.cs.kuleuven.be.ui.geometry.Figure;
import interactr.cs.kuleuven.be.ui.geometry.Link;

import java.awt.*;
import java.util.ArrayList;
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
     * The height of the party row.
     */
    private static int PARTY_ROW_HEIGHT = 75;

    /**
     * The height of each message row.
     */
    private static int MESSAGE_ROW_HEIGHT = 40;

    /**
     * The width of activation bars.
     */
    private static int ACTIVATION_BAR_WIDTH = 10;

    /**
     * The color used to draw activation bars.
     */
    private static Color ACTIVATION_COLOR = Color.getHSBColor(216/360, 35/360, 0.66f);

    /**
     * Initialize this new diagram view with the given diagram.
     *
     * @param diagram The diagram to associate this diagram view with.
     * @throws IllegalArgumentException If the given diagram is null.
     */
    public SequenceView(Diagram diagram) {
        super(diagram);
    }

    @Override
    public void display(PaintBoard paintBoard, Diagram diagram) {
        displayFigures(paintBoard, diagram);
        paintBoard.setColor(Color.LIGHT_GRAY);
        for (Party party : figures.keySet()) {
            Figure partyFigure = figures.get(party);
            paintBoard.drawLine(partyFigure.getX() + partyFigure.getWidth() / 2,
                    PARTY_ROW_HEIGHT,
                    partyFigure.getX() + partyFigure.getWidth() / 2,
                    paintBoard.getHeight());
        }
        paintBoard.setColor(Color.BLACK);
        paintBoard.drawLine(0, PARTY_ROW_HEIGHT, paintBoard.getWidth(), PARTY_ROW_HEIGHT);
        displayMessages(paintBoard, diagram);
    }

    @Override
    protected void displayMessages(PaintBoard paintBoard, Diagram diagram) {

        // Pre-processing
        Party initiator = diagram.getInitiator();
        PList<Party> parties = diagram.getParties();
        int[] activations = new int[parties.size()];

        // Display messages and activation bars
        for (int i=0 ; i<diagram.getNbMessages() ; i++) { // Drawing is done in pairs

            Message message = diagram.getMessageAtIndex(i);
            Party sender = message.getSender(), receiver = message.getReceiver();
            Link messageLink = linkForMessage(message);
            int associatedIndex = diagram.getIndexOfAssociatedMessage(i);
            Message associatedMessage = diagram.getMessageAtIndex(associatedIndex);
            Link associatedMessageLink = linkForMessage(diagram.getMessageAtIndex(associatedIndex));

            if (messageLink != null && associatedMessageLink != null) {

                // Determine horizontal offset for the message
                boolean fromLeft = messageLink.getStartX() < messageLink.getEndX(); // Link direction
                int indexOfReceiver = parties.indexOf(receiver), indexOfSender = parties.indexOf(sender);
                int receiverActivations = activations[indexOfReceiver], senderActivations = activations[indexOfSender];

                if (associatedIndex > i) {

                    // Determine activation bar heights (widths are fixed)
                    int min = Math.min(messageLink.getStartY(), associatedMessageLink.getStartY());
                    int max = Math.max(messageLink.getStartY(), associatedMessageLink.getStartY());
                    int messageX = 0, barX = 0, barY = min - 5, barHeight = max - min + 10;

                    // If the sender of the invocation message is the diagram's initiator, an initial bar is drawn
                    if (sender == initiator && senderActivations == 0) {
                        activations[indexOfSender] = 1;
                        senderActivations++;
                        drawActivationBar(paintBoard,
                                messageLink.getStartX() - ACTIVATION_BAR_WIDTH/2,
                                barY,
                                barHeight);
                    }

                    // Draw bar at the end of the invocation message's receiver
                    activations[indexOfReceiver]++;
                    barX = messageLink.getEndX() - ACTIVATION_BAR_WIDTH/2
                            + (ACTIVATION_BAR_WIDTH/2)*receiverActivations;
                    messageX = messageLink.getStartX()
                            + (ACTIVATION_BAR_WIDTH/2)*senderActivations;
                    drawActivationBar(paintBoard, barX, barY, barHeight);

                    // Draw invocation link (calculate offset!)
                    boolean isSelected = diagram.isSelected(message);
                    boolean isActive = diagram.getActiveComponent() == message;
                    paintBoard.setColor((isSelected || isActive ? Color.BLUE : Color.BLACK));
                    if (isActive)
                        messageLink.setLabel(diagram.getTemporaryLabel() + "|");
                    messageLink.setStartX(messageX - (fromLeft ? 0 : ACTIVATION_BAR_WIDTH));
                    messageLink.setEndX(barX + (fromLeft ? 0 : ACTIVATION_BAR_WIDTH));
                    messageLink.draw(paintBoard);
                    paintBoard.setColor(Color.BLACK);

                    // Draw receiver link (calculate offset!)
                    isSelected = diagram.isSelected(associatedMessage);
                    isActive = diagram.getActiveComponent() == associatedMessage;
                    paintBoard.setColor((isSelected || isActive ? Color.BLUE : Color.BLACK));
                    if (isActive)
                        associatedMessageLink.setLabel(diagram.getTemporaryLabel() + "|");
                    associatedMessageLink.setEndX(messageX - (fromLeft ? 0 : ACTIVATION_BAR_WIDTH));
                    associatedMessageLink.setStartX(barX + (fromLeft ? 0 : ACTIVATION_BAR_WIDTH));
                    associatedMessageLink.draw(paintBoard);
                    paintBoard.setColor(Color.BLACK);

                }
                else {
                    if (receiver == initiator && receiverActivations == 1)
                        activations[indexOfReceiver] = 0;
                    activations[indexOfSender]--;
                }

            }

        }

    }

    /**
     * Draw an activation bar on the given paint board, with given dimensions and coordinate.
     *
     * @param paintBoard The paint board on which to draw.
     * @param x The x coordinate for the bar.
     * @param y The y coordiante for the bar.
     * @param height The height of the bar.
     */
    private void drawActivationBar(PaintBoard paintBoard, int x, int y, int height) {
        paintBoard.setColor(ACTIVATION_COLOR);
        paintBoard.fillRectangle(x, y, ACTIVATION_BAR_WIDTH, height);
        paintBoard.setColor(Color.BLACK);
        paintBoard.drawRectangle(x, y, ACTIVATION_BAR_WIDTH, height);
    }

    @Override
    public void addParty(Diagram diagram, Party party, int x, int y) throws InvalidAddPartyException {
        if (y >= PARTY_ROW_HEIGHT)
            throw new InvalidAddPartyException();
        super.addParty(diagram, party, x, 5);
    }

    @Override
    public void registerParty(Party party, int x, int y) {
        super.registerParty(party, x, 5);
    }

    @Override
    public void moveParty(Diagram diagram, Party party, int x, int y){
        super.moveParty(diagram, party, x, 5);
    }

    @Override
    protected Link linkForMessage(Message message) {
        Link link = links.get(message);
        if (link != null) {
            Party sender = message.getSender(), receiver = message.getReceiver();
            Figure senderFigure = figures.get(sender), receiverFigure = figures.get(receiver);
            link.setStartX(senderFigure.getX() + senderFigure.getWidth()/2);
            link.setEndX(receiverFigure.getX() + receiverFigure.getWidth()/2);
            link.setLabel(message.getLabel());
        }
        return link;
    }

    @Override
    protected Link createLinkForMessage(Message message, int fromX, int fromY, int toX, int toY) {
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
        link.setStartX(senderFigure.getX() + senderFigure.getWidth()/2);
        link.setStartY(fromY);
        link.setEndX(receiverFigure.getX() + receiverFigure.getWidth()/2);
        link.setEndY(toY);
        link.setLabel(message.getLabel());
        return link;
    }

    @Override
    public boolean canInsertMessageAt(Diagram diagram, int fromX, int fromY, int toX, int toY) {
        if (fromY < PARTY_ROW_HEIGHT || toY < PARTY_ROW_HEIGHT)
            return false;
        int min = Math.min(fromY, toY);
        Link link = new Link(fromX, fromY, toX, toY);
        for (Message message : diagram.getMessages()) {
            Link messageLink = linkForMessage(message);
            if (messageLink != null && (messageLink.crosses(link) || Math.abs(messageLink.getStartY() - min) < 8))
                return false;
        }
        return true;
    }

    @Override
    public void registerMessages(Diagram diagram, InvocationMessage invocation, ResultMessage resultMessage, int fromX, int fromY, int toX, int toY) {
        int min = Math.min(fromY, toY);
        Link invocationLink = createLinkForMessage(invocation, fromX, min, toX, min);
        int max = Math.max(fromY, toY);
        Link resultLink = createLinkForMessage(resultMessage, fromX, min + MESSAGE_ROW_HEIGHT, toX, min + MESSAGE_ROW_HEIGHT);
        links = links.plus(invocation, invocationLink);
        links = links.plus(resultMessage, resultLink);
        int minY = min + MESSAGE_ROW_HEIGHT;
        int resultIndex = diagram.getIndexOfMessage(resultMessage);
        for (int i=resultIndex+1 ; i<diagram.getNbMessages() ; i++) {
            Link link = links.get(diagram.getMessageAtIndex(i));
            if (link != null && (link.getStartY() < minY + MESSAGE_ROW_HEIGHT || link.getEndY() < minY + MESSAGE_ROW_HEIGHT)) {
                link.setStartY(minY + MESSAGE_ROW_HEIGHT);
                link.setEndY(minY + MESSAGE_ROW_HEIGHT);
                minY = minY + MESSAGE_ROW_HEIGHT;
            }
        }
    }

    @Override
    public InvocationMessage getInvocationMessageForCoordinates(int fromX, int fromY, int toX, int toY) {
        Party fromParty = getPartyAt(fromX, 10), toParty = getPartyAt(toX, 10);
        if (fromParty == null || toParty == null)
            return null;
        Figure fromFigure = figureForParty(fromParty), toFigure = figureForParty(toParty);
        if (fromFigure == null || toFigure == null)
            return null;
        try {
            InvocationMessage message = new InvocationMessage(fromParty, toParty);
            return message;
        }
        catch (Exception e) {}
        return null;
    }

    @Override
    public String viewName() {
        return "Sequence View";
    }

}