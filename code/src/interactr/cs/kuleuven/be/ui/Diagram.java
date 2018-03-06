package interactr.cs.kuleuven.be.ui;
import interactr.cs.kuleuven.be.domain.Party;
import java.util.ArrayList;

public class Diagram {


    private ArrayList<Party> parties = new ArrayList<Party>();

    public Diagram(DiagramController diagramController) {
        this.diagramController = diagramController;
    }

    public DiagramController getDiagramController() {
        return diagramController;
    }

    private DiagramController diagramController;

    public void addParty(int x, int y){
        Party newParty = new Party(false);
        this.parties.add(newParty);
        this.diagramController.addPartyToView(newParty,x,y);
    }

    public void changePartyType(Party party){
        party.setIsActor(!party.isActor());
    }

    public void addCharToLabel(Party p, char c){
        String previous = p.getLabel();
        p.setLabel(previous + c);
    }


    public void deleteCharOfLabel(Party p){
        p.setLabel( p.getLabel().substring(0, p.getLabel().length() - 1) );
    }



}
