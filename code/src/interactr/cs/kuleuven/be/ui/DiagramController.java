package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.Diagram;
import interactr.cs.kuleuven.be.domain.Party;
import interactr.cs.kuleuven.be.ui.exceptions.InvalidAddException;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A class of diagram controllers for managing a diagram and associated diagram views.
 *  Diagram controllers allow for manipulation and selection of diagram components such as parties and messages,
 *  as well as cycling between available diagram views.
 *
 * @author Team 25
 * @version 1.0
 */
public class DiagramController {

    /**
     * Returns a default list of views for diagram controllers.
     *
     * @return An arraylist with a sequence - and a communication view.
     */
    private static ArrayList<DiagramView> defaultViews() {
        ArrayList<DiagramView> views = new ArrayList<DiagramView>();
        views.add(new SequenceView());
        views.add(new CommunicationView());
        return views;
    }

    /**
     * Initialize this new diagram controller with a communication & sequence view,
     *  and an empty diagram.
     */
    public DiagramController() {
        this(new Diagram(), defaultViews());
    }

    /**
     * Initialize this new diagram controller with given diagram and diagram views.
     *
     * @param diagram The diagram to initialize this controller with.
     * @param views The views for this new diagram.
     */
    public DiagramController(Diagram diagram, ArrayList<DiagramView> views) {
        this.diagram = diagram;
        this.views = new ArrayList<DiagramView>();
        if (views != null)
            for (DiagramView view : views)
                this.views.add(view);
        java.awt.EventQueue.invokeLater(() -> {
            DiagramWindow window = new DiagramWindow("New document - Interactr");
            PaintBoard paintBoard = new PaintBoard(window, this);
            setPaintBoard(paintBoard);
            window.setPaintBoard(paintBoard);
            window.setEventHandler(new EventHandler(this));
            window.show();
        });
    }

    /**
     * Select the next diagram view associated with this controller and display it
     *  in this controller's diagram window.
     */
    public void nextView() {
        activeViewIndex = (activeViewIndex + 1) % views.size();
        getPaintBoard().refresh();
    }

    /**
     * Display the currently active diagram view by making use of the given paintboard.
     */
    public void displayView() {
        getActiveView().display(getPaintBoard(), getDiagram());
    }

    /**
     * Get the currently active view of this diagram controller.
     *
     * @return The diagram view of this controller that's currently active.
     */
    private DiagramView getActiveView() {
        return views.get(activeViewIndex);
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
     * Add a new party at the given x and y coordinate.
     *
     * @param x The x coordinate for the new party.
     * @param y The y coordinate for the new party.
     * @throws InvalidAddException The given coordinates point to a component already.
     */
    public void addPartyAt(int x, int y) throws InvalidAddException {
        Party newParty = new Party(false);
        try {
            getActiveView().addParty(getDiagram(), newParty, x, y);
            getDiagram().addParty(newParty);
            for (DiagramView view : views)
                if (view != getActiveView())
                    view.registerParty(newParty, x, y);
            getPaintBoard().refresh();
        }
        catch (InvalidAddException addException) {
            throw addException;
        }
    }

    /**
     * Switch the type of the given party.
     *
     * @param party The party whose type is to be switched.
     */
    public void switchPartyType(Party party) {
        getDiagram().changePartyType(party);
        getPaintBoard().refresh();
    }

    /**
     * Returns the diagram associated with this diagram controller.
     *
     * @return The diagram associated with this diagram controller.
     */
    public Diagram getDiagram() {
        return this.diagram;
    }

    /**
     * The diagram associated with this diagram handler.
     */
    private Diagram diagram;

    /**
     * Returns the paint board associated with this diagram controller.
     */
    public PaintBoard getPaintBoard() {
        return paintBoard;
    }

    /**
     * Set the paint board of this diagram controller to match the given one.
     *
     * @param paintBoard The new paint board for this controller.
     */
    public void setPaintBoard(PaintBoard paintBoard) {
        this.paintBoard = paintBoard;
    }

    /**
     * Registers the paint board associated with this controller.
     */
    private PaintBoard paintBoard;

    public static void main(String[] args) { // No documentation
        new DiagramController();
    }

}