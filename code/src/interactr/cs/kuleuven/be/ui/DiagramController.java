package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.domain.*;
import interactr.cs.kuleuven.be.exceptions.*;

import java.util.*;

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
     * Initialize this new diagram controller with given diagram and diagram views.
     */
    public DiagramController() {
        this.subWindows = new ArrayList<SubWindow>();
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
        getActiveSubwindow().displayView(getPaintBoard());
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
            getActiveSubwindow().addPartyAt(x, y);
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
            getActiveSubwindow().addMessage(x1, y1, x2, y2);
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
     * The x & y coordinates of the component.
     *
     * @param x The x coordinate of the component that is to be selected.
     * @param y The y coordinate of the commponent that is to be selected.
     */
    public void selectComponentAt(int x, int y) {
        getActiveSubwindow().selectComponentAt(x, y);
        getPaintBoard().refresh();
    }

    /**
     * Returns the party at the given coordinate, or null if there is none.
     *
     * @param x The x coordinate for the party.
     * @param y The y coordinate for the party.
     * @return The party at the given coordinate, or null if there is none.
     */
    public void switchPartyTypeAt(int x,int y ){
        getActiveSubwindow().switchTypeofPartyAt(x,y);
    }

    /**
     * Moves the given party to the given x and y coordinates.
     * @param x The new x coordinate for the party.
     * @param y The new y coordinate for the party.
     */
    public void movePartyTo(int x, int y){
        getActiveSubwindow().movePartyTo(x,y);
        getPaintBoard().refresh();
    }

    public void movePartyAt(int x, int y){
        getActiveSubwindow().movePartyAt(x,y);

    }

    /**
     * Removes all components in the current selection from this controller's diagram.
     */
    public void deleteSelection(){
        getActiveSubwindow().deleteSelection();
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
    public void addNewSubWindow(){
        subWindows.add(0, new SubWindow());
    }

    /**
     * A method that creates a dublpicate subWindow
     */
    public void addDuplicateSubWindow(){
        SubWindow temp = subWindows.remove(0);
        subWindows.add(0,new SubWindow(temp));
        subWindows.add(temp);
    }

    /**
     * Switch from subwindow at the given x and y, if there is no subwindow nothing happens
     * @param x
     * @param y
     */
    public void switchSubWindow(int x, int y){
        SubWindow sub = subWindows.stream().filter( s -> s.getFrame().encloses(x,y)).findFirst().get();
        if(sub != null)
            subWindows.add(0, subWindows.remove(subWindows.indexOf(sub)));
    }

    /**
     * A method
     * @param x
     * @param y
     */
    public void resizeSubWindowAt(int x, int y){
        SubWindow sub = subWindows.stream().filter( s -> s.enclosesBorders(x,y)).findFirst().get();
        if(sub != null)
            selectedSubWindow = sub;
        else
            throw new InvalidResizeWindowException();

    }

    /**
     * A method that resizethe subwindow accordingly
     * @param x
     * @param y
     */
    public void resizeSubWindowTo(int x, int y) {
        selectedSubWindow.resizeSubWindowFrame(x,y);
    }

    /**
     * A window that selects the subwindow for moving if one exists, else throw a new exception that the eventhandle can catch
     * @param x
     * @param y
     */
    public void moveSubWindowAt(int x,int y){
        SubWindow sub = subWindows.stream().filter( s -> s.enclosesTitleBar(x,y)).findFirst().get();
        if(sub != null)
            selectedSubWindow = sub;
        else
            throw new InvalidMoveWindowException();
    }

    /**
     * A method that moves the selected SubWindow
     * @param x
     * @param y
     */
    public void moveSubWindowTo(int x, int y){
        selectedSubWindow.moveSubWindowFrame(x,y);
    }

    /**
     * A method that calls the reset operation of the just moved party
     */
    public void resetResizeRhumb(){
        selectedSubWindow.resetResizeRhumb();
    }


    /**
     * A subwindow that is currently selected for moving or resizing
     */
    private SubWindow selectedSubWindow;

    public static void main(String[] args) { // No documentation
        new DiagramController();
    }

    public static final int EAST = 2;
    public static final int SOUTH = 3;
    public static final int WEST = 5;

}