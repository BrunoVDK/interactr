package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Party;
import jdk.jshell.Diag;

import java.awt.*;
import java.util.ArrayList;

/**
 * A class of diagram handlers for intercepting and interpreting mouse and key events.
 *  Each instance has a sequence view and a communication view.
 */
public class DiagramHandler {

    /**
     * Initialize a new diagram handler.
     *
     */
    public DiagramHandler() {
        this.diagram = new Diagram();
        views.add(new SequenceView(diagram));
        views.add(new CommunicationView(diagram));
        java.awt.EventQueue.invokeLater(() -> {
            this.window = new DiagramWindow("test", diagram);
            this.window.show();
        });
    }

    public void repaint(Graphics context) {


    }

    /**
     * Registers the index of the currently active view.
     */
    private int activeViewIndex = 0;

    /**
     * A list of all diagram views kept by this diagram handler.
     */
    private ArrayList<DiagramView> views = new ArrayList<DiagramView>();

    /**
     * Switch to the next diagram view.
     */
    public void switchView() {
        activeViewIndex = (activeViewIndex + 1) % views.size();
        this.window.repaint();
    }

    public void editLabelOfParty(Party part) {

    }

    private Party activeParty = null;

    public void selectParty(Party party) {

    }

    public void selectLabelOfParty(Party party) {

    }

    private DiagramWindow window;

    /**
     * Register the diagram associated with this diagram handler.
     */
    private Diagram diagram;


    public static void main(String[] args) {
        new DiagramHandler();
    }

}