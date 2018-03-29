package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.*;
import interactr.cs.kuleuven.be.exceptions.InvalidAddMessageException;
import interactr.cs.kuleuven.be.exceptions.InvalidAddPartyException;
import interactr.cs.kuleuven.be.exceptions.InvalidLabelException;

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
    public static ArrayList<DiagramView> defaultViews() {
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
        this.subWindows = new ArrayList<SubWindow>();
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
        getActiveSubwindow().nextView();
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
    public SubWindow getActiveSubwindow(){
        return subWindows.get(0);
    }


    /**
     * The list of all diagram views kept by this diagram handler.
     */
    private ArrayList<SubWindow> subWindows = new ArrayList<SubWindow>();

    /**
     * Add a new party at the given x and y coordinate.
     *
     * @param x The x coordinate for the new party.
     * @param y The y coordinate for the new party.
     * @throws InvalidAddPartyException The given coordinates point to a component already.
     */
    public void addPartyAt(int x, int y) throws InvalidAddPartyException {
        try {
            getDiagram().addParty(x, y, this.getActiveView(), views);
            getPaintBoard().refresh();
        }
        catch (InvalidAddPartyException addException){
            throw addException;
        }
    }

    /**
     * Adds a message using the given start and end coordinates of a drag session.
     *
     * @param x1 The start x coordinate for the session.
     * @param y1 The start y coordinate for the session.
     * @param x2 The end x coordinate for the session.
     * @param y2 The end y coordinate for the session.
     */
    public void addMessageFrom(int x1, int y1, int x2, int y2) {
        try {
            getDiagram().addMessageFrom(x1, y1, x2, y2, this.getActiveView(), views);
            getPaintBoard().refresh();
        }
        catch(InvalidAddMessageException addException){
            throw addException;
        }
    }
        /**
         * A method that returns the editing mode of the selectionManager
         */
    public boolean isEditing() { return getDiagram().getActiveComponent() != null;};

    /**
     * A method that terminates the editing
     */
    public void abortEditing(){
        if (getDiagram().getActiveComponent() != null){
            try {
                getDiagram().getActiveComponent().setLabel(getDiagram().getTemporaryLabel());
                getDiagram().setActiveComponent(null);
                getPaintBoard().refresh();
            }
            catch (InvalidLabelException e) {}
        }
    }

    /**
     * Switch the type of the given party.
     *
     * @param party The party whose type is to be switched.
     */
    public void switchPartyType(Party party){
        getDiagram().replaceParty(party, views);
        getPaintBoard().refresh();
    }

    /**
     * The x & y coordinates of the component.
     *
     * @param x The x coordinate of the component that is to be selected.
     * @param y The y coordinate of the commponent that is to be selected.
     */
    public void selectComponentAt(int x, int y) {
        DiagramComponent component = getActiveView().selectableComponentAt(getDiagram(), x, y);
        getDiagram().addToSelection(component);
        this.getPaintBoard().refresh();
    }

    /**
     * Returns the party at the given coordinate, or null if there is none.
     *
     * @param x The x coordinate for the party.
     * @param y The y coordinate for the party.
     * @return The party at the given coordinate, or null if there is none.
     */
    public Party getPartyAt(int x,int y ){
        return getActiveView().getPartyAt(x,y);
    }

    /**
     * Moves the given party to the given x and y coordinates.
     *
     * @param party The party that is to be moved.
     * @param x The new x coordinate for the party.
     * @param y The new y coordinate for the party.
     */
    public void moveParty(Party party ,int x, int y){
        getActiveView().moveParty(getDiagram(), party,x,y);
        getPaintBoard().refresh();
    }

    /**
     * Removes all components in the current selection from this controller's diagram.
     */
    public void deleteSelection(){
        this.getDiagram().deleteSelection(this.views);
        this.getPaintBoard().refresh();
    }

    /**
     * Append the given char to the current edit session.
     *
     * @param c The char that is to be appended.
     */
    public void appendChar(char c){
        this.getDiagram().setTemporaryLabel(this.getDiagram().getTemporaryLabel() + c);
        getPaintBoard().refresh();
    }

    /**
     * Removes the last char from the label of the active component.
     */
    public void removeLastChar() {
        String temp = this.getDiagram().getTemporaryLabel();
        if (temp.length() > 0) {
            this.getDiagram().setTemporaryLabel(temp.substring(0, temp.length()-1));
            getPaintBoard().refresh();
        }
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

    /**
     * A method that creates a new Subwindow and adds it to top of the list, so that
     */
    private void addNewSubwindow(){


    }

    public static void main(String[] args) { // No documentation
        new DiagramController();
    }
}