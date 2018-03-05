package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Label;
import interactr.cs.kuleuven.be.domain.Party;

import java.awt.*;
import java.util.ArrayList;

public class PaintController {

    /**
     * The constructor of the class
     */
    public PaintController(DiagramController controller) {
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
        activeViewIndex = (activeViewIndex + 1) % views.size();
    }

    /**
     * Registers the index of the currently active view.
     */
    private int activeViewIndex = 0;

    /**
     * The list of all diagram views kept by this diagram handler.
     */
    private ArrayList<DiagramView> views = new ArrayList<DiagramView>();

    /**
     * Returns teh active View
     */
    private DiagramView getActiveView(){ return this.views.get(activeViewIndex); }

    /**
     * The party that is selected on this moment
     */
    private Party selectedParty;

    public void paint(Graphics context) {
        views.get(activeViewIndex).draw(getDiagramController().getDiagram());
    }

    /**
     * A method that that calls the active diagram to ask for the active party and g
     * @param diagram
     * @return
     */
    public Party getPartyAt(Diagram diagram, int x, int y){
        return getActiveView().getPartyAt(diagram,x,y);
    }

    /**
     * A method that calls the active diagram to ask if it is possible to add a party at the given coordinate
     * @param diagram
     * @param x
     * @return
     */
    public boolean canAddPartyAt(Diagram diagram, int x, int y){
        return getActiveView().canAddPartyAt(diagram,x,y);
    }

}
