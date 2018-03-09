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
    private static int ACTIVATION_BAR_HEIGHT = 10;

    @Override
    public void display(PaintBoard paintBoard, Diagram diagram, SelectionManager selectionManager) {
        displayFigures(paintBoard, diagram, selectionManager);
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
        displayMessages(paintBoard, diagram, selectionManager);
    }

    @Override
    protected void displayMessages(PaintBoard paintBoard, Diagram diagram, SelectionManager selectionManager) {

        // Pre-processing
        Color activationColor = Color.getHSBColor(216/360, 35/360, 0.66f);
        int barWidth = ACTIVATION_BAR_HEIGHT;
        PList<Party> parties = diagram.getParties();
        int[][] activations = new int[parties.size()][2]; // From left, from right

        // Display messages and activation bars
        for (int i=0 ; i<diagram.getNbMessages() ; i++) {

            Message message = diagram.getMessageAtIndex(i);
            int associatedIndex = diagram.getIndexOfAssociatedMessage(i);
            Party sender = message.getSender(), receiver = message.getReceiver();
            Link messageLink = linkForMessage(message);
            Link associatedMessageLink = linkForMessage(diagram.getMessageAtIndex(associatedIndex));
            if (messageLink != null && associatedMessageLink != null) {

                // Determine activation bar at the 'end' side and draw it
                boolean fromLeft = messageLink.getStartX() < messageLink.getEndX();
                int min = Math.min(messageLink.getStartY(), associatedMessageLink.getStartY());
                int max = Math.max(messageLink.getStartY(), associatedMessageLink.getStartY());
                int barHeight = max - min + 10;
                int barX = messageLink.getStartX() - barWidth/2;
                int barY = min - 5;
                paintBoard.setColor(activationColor);
                paintBoard.fillRectangle(barX, barY, barWidth, barHeight);
                paintBoard.setColor(Color.BLACK);
                paintBoard.drawRectangle(barX, barY, barWidth, barHeight);

                // Draw link
                boolean isSelected = selectionManager.isSelected(message);
                boolean isActive = selectionManager.getActiveComponent() == message;
                paintBoard.setColor((isSelected || isActive ? Color.BLUE : Color.BLACK));
                if (isActive)
                    messageLink.setLabel(selectionManager.getTemporaryLabel() + "|");
                messageLink.draw(paintBoard);
                paintBoard.setColor(Color.BLACK);

            }

        }

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