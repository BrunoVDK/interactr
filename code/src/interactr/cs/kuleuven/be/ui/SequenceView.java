package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.*;
import interactr.cs.kuleuven.be.ui.exceptions.InvalidAddPartyException;
import interactr.cs.kuleuven.be.ui.geometry.Figure;

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
        super.displayFigures(paintBoard, diagram, selectionManager);
        paintBoard.setColor(Color.BLACK);
        for (Party party : figures.keySet()) {
            Figure partyFigure = figures.get(party);
            paintBoard.drawLine(partyFigure.getX() + partyFigure.getWidth() / 2,
                    PARTY_ROW_HEIGHT,
                    partyFigure.getX() + partyFigure.getWidth() / 2,
                    paintBoard.getHeight());
        }
        paintBoard.drawLine(0, PARTY_ROW_HEIGHT, paintBoard.getWidth(), PARTY_ROW_HEIGHT);
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
            System.out.println(figureForParty(party).getLabelBounds().getY());
            System.out.println(figureForParty(party).getLabelBounds().getWidth());
            if (figureForParty(party).isLabelHit(x,y))
                return party;
        }
        return null;
    }

    @Override
    public String viewName() {
        return "Sequence View";
    }

    @Override
    public void moveParty(Party party, int x, int y){
        figures.get(party).setX(x);
    }

    public boolean canAddMessage(Party sender, Party receiver, int y){

        return true;
    }

    public ArrayList<MessageY> getMessagesOnYCo(){
        return messagesOnYCo;
    }

    private ArrayList<MessageY> messagesOnYCo = new ArrayList<>();

    public void initializeCallStack(Party sender, Party receiver, int y){
        MessageY invoc = new MessageY(new InvocationMessage(sender, receiver), y);
        MessageY result = new MessageY(new ResultMessage(sender, receiver), y+20);

        getMessagesOnYCo().add(invoc);
        getMessagesOnYCo().add(result);
    }

    public void updateCallStack(Party sender, Party receiver, int y){
        Message previous = null;
        for(int i = 0; i < getMessagesOnYCo().size(); i++){
            if(getMessagesOnYCo().get(i).getY() > y){
                previous = getMessagesOnYCo().get(i-1).getMessage();
                break;
            }
        }
        if(!(previous.equals(null)) && previous.getReceiver() == sender){
            MessageY invoc = new MessageY(new InvocationMessage(sender, receiver), y);
            MessageY result = new MessageY(new ResultMessage(sender, receiver), y+20);

            getMessagesOnYCo().add(invoc);
            getMessagesOnYCo().add(result);
        }
    }

    private int height;

    public void setOffSet(int height, int width){
        this.height = height;
    }
    public int getOffSet(){
        return 0;
    }
}