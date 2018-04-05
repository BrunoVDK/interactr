package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.exceptions.*;

import java.util.*;

/**
 * A class of diagram controllers for managing subwindows within a diagram window.
 *
 * @author Team 25
 * @version 1.0
 */
public class DiagramController {

    /**
     * Initialize this new diagram controller without any subwindows.
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
     * Toggle the diagram view in this controller's active subwindow
     */
    public void toggleActiveSubWindowView() {
        getActiveSubwindow().nextView();
        getPaintBoard().refresh();
    }

    /**
     * Display all subwindows in this diagram controller.
     */
    public void displayAllSubWindows() {
        for (SubWindow window : subWindows)
            window.displayView(getPaintBoard());
    }

    /**
     * Display the currently active subwindow of this diagram controller.
     */
    public void displaySubWindow() {
        getActiveSubwindow().displayView(getPaintBoard());
    }

    /**
     * Get the currently active view of this diagram controller.
     *
     * @return The diagram view of this controller that's currently active.
     */
    private SubWindow getActiveSubwindow(){
        return (subWindows.isEmpty() ? null : subWindows.get(0));
    }

    /**
     * Creates a new subwindow with default parameters.
     */
    public void createSubWindow(){
        subWindows.add(0, new SubWindow());
    }

    /**
     * Duplicate the currently active subwindow.
     *
     * @throws NoSuchWindowException If no window is currently active.
     */
    public void duplicateSubWindow() {
        if (getActiveSubwindow() != null)
            subWindows.add(0, new SubWindow(getActiveSubwindow()));
        else
            throw new NoSuchWindowException();
    }

    /**
     * Activate the subwindow at the given x and y.
     *
     * @param x The x coordinate at which the subwindow lies.
     * @param y The y coordinate at which the subwindow lies.
     * @throws NoSuchWindowException If no subwindow lies at the given coordinates.
     */
    public void activateSubWindow(int x, int y) throws NoSuchWindowException {
        SubWindow subWindow = getSubWindowAt(x, y);
        if (subWindow == null)
            throw new NoSuchWindowException();
        subWindows.add(0, subWindows.remove(subWindows.indexOf(subWindow)));
    }

    /**
     * Move the active subwindow from the given coordinates to the given coordinates.
     *
     * @param fromX The start x coordinate for the move.
     * @param fromY The start y coordinate for the move.
     * @param toX The end x coordinate for the move.
     * @param toY The end y coordinate for the move.
     * @throws InvalidMoveWindowException The resize operation was not successful.
     */
    public void moveSubWindow(int fromX, int fromY, int toX, int toY) {
        getActiveSubwindow().move(fromX, fromY, toX, toY);
    }

    /**
     * Resize the active subwindow from the given coordinates to the given coordinates.
     *
     * @param fromX The start x coordinate for the resize.
     * @param fromY The start y coordinate for the resize.
     * @param toX The end x coordinate for the resize.
     * @param toY The end y coordinate for the resize.
     * @throws InvalidResizeWindowException The resize operation was not successful.
     */
    public void resizeSubWindow(int fromX, int fromY, int toX, int toY) {
        getActiveSubwindow().resize(fromX, fromY, toX, toY);
    }

    /**
     * Get the subwindow at the given coordinates.
     *
     * @param x The x coordinate to look at.
     * @param y The y coordinate to look at.
     * @return The subwindow at the given coordinates or null if there is none.
     */
    private SubWindow getSubWindowAt(int x, int y) {
        return subWindows.stream().filter( s -> s.getFrame().encloses(x,y)).findFirst().orElse(null);
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
     * Adds a message from the given start coordinate to the given end coordinate.
     *
     * @param fromX The start x coordinate for the add.
     * @param fromY The start y coordinate for the add.
     * @param toX The end x coordinate for the add.
     * @param toY The end y coordinate for the add.
     */
    public void addMessageFrom(int fromX, int fromY, int toX, int toY) {
        try {
            getActiveSubwindow().addMessage(fromX, fromY, toX, toY);
            getPaintBoard().refresh();
        }
        catch(InvalidAddMessageException addException){
            throw addException;
        }
    }

    /**
     * The x & y coordinates of the component.
     *
     * @param x The x coordinate of the component that is to be selected.
     * @param y The y coordinate of the component that is to be selected.
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
    public void switchPartyTypeAt(int x,int y ) {
        getActiveSubwindow().switchTypeOfPartyAt(x,y);
    }

    /**
     * Moves the party at the given start coordinates to the given end coordinates.
     *
     * @param fromX The start x coordinate for the add.
     * @param fromY The start y coordinate for the add.
     * @param toX The end x coordinate for the add.
     * @param toY The end y coordinate for the add.
     */
    public void moveParty(int fromX, int fromY, int toX, int toY){
        getActiveSubwindow().moveParty(fromX, fromY, toX, toY);
        getPaintBoard().refresh();
    }

    /**
     * Removes all components in the current selection from this controller's diagram.
     */
    public void deleteSelection(){
        getActiveSubwindow().deleteSelection();
        this.getPaintBoard().refresh();
    }

    /**
     * A method that returns the editing mode of the selectionManager
     */
    public boolean isEditing() {
        return false;
        // return getDiagram().getActiveComponent() != null;
    };

    /**
     * A method that terminates the editing
     */
    public void abortEditing(){
        /*
        if (getDiagram().getActiveComponent() != null){
            try {
                getDiagram().getActiveComponent().setLabel(getDiagram().getTemporaryLabel());
                getDiagram().setActiveComponent(null);
                getPaintBoard().refresh();
            }
            catch (InvalidLabelException e) {}
        }
        */
    }

    /**
     * Append the given char to the current edit session.
     *
     * @param c The char that is to be appended.
     */
    public void appendChar(char c){
        // this.getDiagram().setTemporaryLabel(this.getDiagram().getTemporaryLabel() + c);
        getPaintBoard().refresh();
    }

    /**
     * Removes the last char from the label of the active component.
     */
    public void removeLastChar() {
        /*
        String temp = this.getDiagram().getTemporaryLabel();
        if (temp.length() > 0) {
            this.getDiagram().setTemporaryLabel(temp.substring(0, temp.length()-1));
            getPaintBoard().refresh();
        }
        */
    }

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

    // Entry point
    public static void main(String[] args) { // No documentation
        new DiagramController();
    }

}