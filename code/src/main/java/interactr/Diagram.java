package main.java.interactr;
import java.util.ArrayList;

public class Diagram {

    private Diagram activeDiagram;



    /**
     * A method that makes a new party and adds it to the list
     */
    public void addParty(int x, int y){
        parties.add(new Party(x,y));
    }

    /**
     * Checks of the given x, y a
     */
    private boolean checkPositionParty(int x, int y){
        return checkForSequence(x,y) && checkForCummunication(x,y);

    }

    private boolean checkForSequence(int x, int y) {
        return (activeDiagram instanceof SequenceDiagram && y < ((SequenceDiagram) activeDiagram).activvationBarY);
    }

    private boolean checkForCummunication(int x, int y){
        return true;
    }

    /**
     * A list of all the parties that are currently in the diagram
     */
    ArrayList <Party> parties = new ArrayList<Party>();



}
