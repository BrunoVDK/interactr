package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.*;
import interactr.cs.kuleuven.be.exceptions.InvalidAddPartyException;
import interactr.cs.kuleuven.be.ui.geometry.Arrow;
import interactr.cs.kuleuven.be.ui.geometry.Figure;
import interactr.cs.kuleuven.be.ui.geometry.Link;

import java.awt.*;
import java.util.ArrayList;

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
    private static int MESSAGE_ROW_HEIGHT = 30;

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
        for (Message message : links.keySet()) {
            boolean isSelected = selectionManager.isSelected(message);
            boolean isActive = selectionManager.getActiveComponent() == message;
            paintBoard.setColor((isSelected || isActive ? Color.BLUE : Color.BLACK));
            Link messageLink = linkForMessage(message);
            if (isActive)
                messageLink.setLabel(selectionManager.getTemporaryLabel() + "|");
            messageLink.draw(paintBoard);
            paintBoard.setColor(Color.BLACK);
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
    public DiagramComponent selectableComponentAt(Diagram diagram, int x, int y) {
        for (Party party : diagram.getParties()) {
            if (figureForParty(party).isLabelHit(x,y))
                return party;
        }
        return null;
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
        Link link = new Link(fromX, fromY, toX, toY);
        for (Message message : diagram.getMessages()) {
            Link messageLink = linkForMessage(message);
            if (messageLink != null && messageLink.crosses(link))
                return false;
        }
        return true;
    }

    @Override
    public void registerMessages(InvocationMessage invocation, ResultMessage resultMessage, int fromX, int fromY, int toX, int toY) {
        int y = Math.min(fromY, toY) + Math.abs(fromY - toY) / 2;
        Link invocationLink = createLinkForMessage(invocation, fromX, y, toX, y);
        int max = Math.max(fromY, toY);
        Link resultLink = createLinkForMessage(resultMessage, fromX, max + 20, toX, max + 20);
        links = links.plus(invocation, invocationLink);
        links = links.plus(resultMessage, resultLink);
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