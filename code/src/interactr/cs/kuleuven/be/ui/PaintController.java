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
        this.diagramController.getWindow().repaint();
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
     * The party that is selected on this moment
     */
    private Party selectedParty;

    public void paint(Graphics context) {
        views.get(activeViewIndex).draw(getDiagramController().getDiagram());
    }

}
