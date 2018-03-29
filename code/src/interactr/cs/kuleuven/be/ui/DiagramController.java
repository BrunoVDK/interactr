package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.*;
import interactr.cs.kuleuven.be.exceptions.InvalidAddMessageException;
import interactr.cs.kuleuven.be.exceptions.InvalidAddPartyException;
import interactr.cs.kuleuven.be.exceptions.InvalidLabelException;

<<<<<<< HEAD
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

=======
import java.util.ArrayList;
>>>>>>> brunoBranch

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
    public DiagramView getActiveView() {
        return views.get(activeViewIndex);
    }

<<<<<<< HEAD
    //TODO de logica doorverwijzen naar een andere klassen want deze moet hier niet staan
    void handleMouseEvent(int id, int x, int y, int clickCount) {
        switch(id){
            case MouseEvent.MOUSE_CLICKED:
                handleMouseClicked(x,y,clickCount);
                break;

            case MouseEvent.MOUSE_PRESSED:
                handleMousePressed(x,y,clickCount);
                break;

            case MouseEvent.MOUSE_DRAGGED:
                handleMouseDragged(x,y,clickCount);
                break;

            case MouseEvent.MOUSE_RELEASED:
                handleMouseReleased();
                break;
=======
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
        try {
            getDiagram().addParty(x, y, this.getActiveView(), views);
            getPaintBoard().refresh();
        }
        catch (InvalidAddPartyException addException){
            throw addException;
>>>>>>> brunoBranch
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

<<<<<<< HEAD
    private boolean notYetReleased;

    void handleMouseClicked(int x, int y, int clickCount){
        if(clickCount == 2
                && !paintController.getPartyAt(x,y).isPresent() && paintController.canAddParty(x,y)
                && ! paintController.isEditMode()){
            getDiagram().addParty(x,y);
            getWindow().repaint();
        }
        if (clickCount == 2 && paintController.getPartyAt(x,y).isPresent() && ! paintController.isEditMode()){
            getDiagram().changePartyType(paintController.getPartyAt(x,y).get());
            getWindow().repaint();
        }
    }

    void handleMousePressed(int x, int y, int clickcount){
        if(clickcount == 1
                && paintController.getSelectedParty() == null
                && paintController.getPartyAt(x,y).isPresent()
                && ! paintController.isEditMode()) {

            paintController.setSelectedParty(paintController.getPartyAt(x,y).get());
        }

    }
    void handleMouseDragged(int x, int y, int clickcount){
        if (! paintController.isEditMode()) {
            paintController.moveSelectedParty(x, y);
            getWindow().repaint();
        }
    }

    void handleMouseReleased(){
        if(! paintController.isEditMode()) {
            paintController.setSelectedParty(null);
            this.notYetReleased = false;
        }
    }

    void handleKeyEvent(int id, int keyCode, char keyChar) {
        if (keyChar == KeyEvent.VK_TAB && id == KeyEvent.KEY_TYPED && ! paintController.isEditMode()) {
            this.paintController.switchView();
            this.getWindow().repaint();
=======
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
>>>>>>> brunoBranch
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

<<<<<<< HEAD
        }

        else if(paintController.isEditMode() && keyChar == KeyEvent.VK_ENTER && id == KeyEvent.KEY_TYPED){
            if(paintController.getSelectedParty().checkCorrectnessLabel()){
                getDiagram().deleteCursor(paintController.getSelectedParty());
                paintController.setEditMode(false);
                paintController.setSelectedParty(null);
                getWindow().repaint();
            }
        }

        else if(paintController.isEditMode() && keyChar == KeyEvent.VK_BACK_SPACE && id == KeyEvent.KEY_TYPED){
            getDiagram().deleteCharOfLabel(paintController.getSelectedParty());
            getWindow().repaint();
        }

        else if(paintController.isEditMode() && keyChar != KeyEvent.VK_ENTER && id == KeyEvent.KEY_TYPED){
            getDiagram().addCharToLabel(paintController.getSelectedParty(),keyChar);
            getWindow().repaint();
        }
    }


    public void paint(Graphics context) {
        this.paintController.paint(context);
=======
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
>>>>>>> brunoBranch
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
<<<<<<< HEAD
    public void addPartyToView(Party p,int x, int y){
        paintController.setEditMode(true);
        paintController.setSelectedParty(p);
        paintController.addNewPartyToViews(p,x,y);
=======
    public void setPaintBoard(PaintBoard paintBoard) {
        this.paintBoard = paintBoard;
>>>>>>> brunoBranch
    }

    /**
     * Registers the paint board associated with this controller.
     */
    private PaintBoard paintBoard;

    public static void main(String[] args) { // No documentation
        new DiagramController();
    }
}