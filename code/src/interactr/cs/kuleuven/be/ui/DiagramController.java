package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Party;

import java.awt.*;
import java.util.ArrayList;

/**
 * A class of diagram handlers for intercepting and interpreting mouse and key events.
 *  Each instance has a sequence view and a communication view.
 */
public class DiagramController {

    /**
     * An enumeration for diagram component statuses.
     */
    public enum DiagramComponentStatus {
        SELECTED,
        ACTIVE;
    }

    /**
     * Initialize a new diagram handler.
     *
     */
    public DiagramController() {
        this.diagram = new Diagram();
        views.add(new SequenceView(diagram));
        views.add(new CommunicationView(diagram));
        java.awt.EventQueue.invokeLater(() -> {
            this.window = new DiagramWindow("test/interactr/cs/kuleuven/be", this);
            this.window.show();
        });
    }

    public void repaint(Graphics context) {
        views.get(activeViewIndex).draw(this.diagram);
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
     * Switch to the next diagram view.
     */
    public void switchView() {
        activeViewIndex = (activeViewIndex + 1) % views.size();
        this.window.repaint();
    }

    public void deleteSelected() {
        System.out.println("delete");
    }

    private Party activeParty = null;

    /**
     * The window associated with this diagram controller.
     */
    private DiagramWindow window;

    /**
     * The diagram associated with this diagram handler.
     */
    private Diagram diagram;


    public static void main(String[] args) {
        new DiagramController();
    }

}