package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Party;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

public class PaintController {

    /**
     * The constructor of the class
     */
    public PaintController(DiagramController controller) {
        this.activeViewIndex = 0;
        setDiagramController(controller);
        views.add(new SequenceView());
        views.add(new CommunicationView());
    }

    /**
     * The diagram controller
     */
    private DiagramController diagramController;

    /**
     * Sets the diagram controller fo this class
     * @param diagramController
     */
    public void setDiagramController(DiagramController diagramController) {
        this.diagramController = diagramController;
    }

    /**
     * Returns the diagramcontroller of this class
     * @return diagramController
     */
    public DiagramController getDiagramController() {
        return diagramController;
    }

    /**
     * A method that switches between the method
     */
    public void switchView(){
        this.activeViewIndex = (activeViewIndex + 1) % views.size();
    }

    /**
     * Registers the index of the currently active view.
     */
    private int activeViewIndex;

    /**
     * The list of all diagram views kept by this diagram handler.
     */
    private ArrayList<DiagramView> views = new ArrayList<DiagramView>();

    /**
     * Returns teh active View
     */
    private DiagramView getActiveView(){
        return this.views.get(activeViewIndex);
    }

    public Party getSelectedParty() {
        return selectedParty;
    }

    public void setSelectedParty(Party selectedParty) {
        this.selectedParty = selectedParty;
    }

    /**
     * The party that is selected on this moment
     */
    private Party selectedParty;

    public void paint(Graphics context) {
        getActiveView().draw(context);
    }

    /**
     * A method that that calls the active diagram to ask for the active party and g
     * @return
     */
    public Optional<Party> getPartyAt(int x, int y){
        return getActiveView().getPartyAt(x,y);
    }

    public boolean canAddParty(int x, int y){
        return getActiveView().canAddPartyAt(x,y);
    }


    /**
     *
     * @param party
     */
    public void addNewPartyToViews(Party party,int x , int y){
        for(DiagramView view: views){
            view.addNewParty(party,x,y);
        }

    }

    public void moveSelectedParty(int x, int y){
        getActiveView().moveSelectedParty(getSelectedParty(), x , y);
    }




}
