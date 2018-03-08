package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.*;
import interactr.cs.kuleuven.be.ui.exceptions.InvalidAddPartyException;
import interactr.cs.kuleuven.be.ui.exceptions.InvalidLabelException;

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
        this.views = new ArrayList<DiagramView>();
        this.selectionManager = new SelectionManager();
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
        getActiveView().display(getPaintBoard(), getDiagram(),selectionManager);
    }

    /**
     * Get the currently active view of this diagram controller.
     *
     * @return The diagram view of this controller that's currently active.
     */
    public DiagramView getActiveView() {
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
     * @throws InvalidAddPartyException The given coordinates point to a component already.
     */
    public void addPartyAt(int x, int y) throws InvalidAddPartyException {
        Party newParty = new ObjectParty();
        try {
            getActiveView().addParty(getDiagram(), newParty, x, y);
            getDiagram().addParty(newParty);
            for (DiagramView view : views)
                if (view != getActiveView())
                    view.registerParty(newParty, x, y);
            getPaintBoard().refresh();
            selectionManager.setActiveComponent(newParty);
        }
        catch (InvalidAddPartyException addException) {
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
    public void addMessageFrom(int x1, int y1, int x2, int y2){
        int height = this.getPaintBoard().getHeight();

        this.getActiveView().setOffSet(height);

        Party sender = getPartyAt(x1,10);
        Party receiver = getPartyAt(x2,10);

        if(this.getActiveView().getMessagesOnYCo().isEmpty()) this.getActiveView().initializeCallStack(sender, receiver, y1);
        else{
            this.getActiveView().updateCallStack(sender, receiver, y1);
        }
    }

    /**
     * A method that returns the editing mode of the selectionManager
     */
    public boolean isEditing() { return selectionManager.getActiveComponent() != null;};

    /**
     * A method that terminates the editing
     */
    public void abortEditing(){
        if (selectionManager.getActiveComponent() != null){
            try {
                selectionManager.getActiveComponent().setLabel(selectionManager.getTemporaryLabel());
                selectionManager.setActiveComponent(null);
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
    public void switchPartyType(Party party) {
        Party newParty;
        if (party instanceof ActorParty)
            newParty = new ObjectParty(party);
        else
            newParty = new ActorParty(party);
        diagram.replaceParty(party, newParty);
        for (DiagramView view : views)
            view.registerPartyReplace(party, newParty);
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
        //TODO Moet dit wel om toe te voegen want deze kan wel null zijn
        selectionManager.addToSelection(component);
        paintBoard.refresh();
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
     * Append the given char to the current edit session.
     *
     * @param c The char that is to be appended.
     */
    public void appendChar(char c){
        selectionManager.setTemporaryLabel(selectionManager.getTemporaryLabel() + c);
        getPaintBoard().refresh();
    }

    /**
     * Removes the last char from the label of the active component.
     */
    public void removeLastChar() {
        String temp = selectionManager.getTemporaryLabel();
        if (temp != "") {
            selectionManager.setTemporaryLabel(temp.substring(0, temp.length()-1));
            getPaintBoard().refresh();
        }
    }

    /**
     * The selected manager associated with this diagram controller.
     */
    private SelectionManager selectionManager;

    public SelectionManager getSelectionManager() {
        return selectionManager;
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