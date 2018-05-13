package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.exceptions.*;
import interactr.cs.kuleuven.be.ui.command.Command;
import interactr.cs.kuleuven.be.ui.command.CommandNotProcessedException;
import interactr.cs.kuleuven.be.ui.control.DiagramWindow;
import interactr.cs.kuleuven.be.ui.control.SubWindow;

import java.io.File;
import java.util.*;

/**
 * A class of diagram controllers for managing subwindows within a diagram window.
 *
 * @author Team 25
 * @version 1.0
 */
public class Controller {

    /**
     * Initialize this new diagram controller without any subwindows and without any recording.
     */
    public Controller() {
        this(false);
    }

    /**
     * Initialize this new diagram controller without any subwindows.
     *
     * @param record If a recording session is to be used.
     */
    private Controller(boolean record) {
        this.subWindows = new ArrayList<SubWindow>();
        Window window = new Window("New document - Interactr");
        PaintBoard paintBoard = new PaintBoard(window, this);
        setPaintBoard(paintBoard);
        window.setPaintBoard(paintBoard);
        window.setEventHandler(new EventHandler(this));
        if (record) {
            File file = new File(CanvasWindow.RECORDINGS_PATH + "new.txt");
            window.recordSession(file.getAbsolutePath());
        }
        java.awt.EventQueue.invokeLater(window::show);
    }

    /**
     * Display all subwindows in this diagram controller.
     */
    public void displayAllSubWindows() {
        for (int i=this.getSubWindows().size()-1 ; i>=0 ; i--) // Last window first
            this.getSubWindows().get(i).displayView(getPaintBoard());
    }

    /**
     * Get the currently active view of this diagram controller.
     *
     * @return The diagram view of this controller that's currently active.
     */
    private SubWindow getActiveSubwindow() {
        return (this.getSubWindows().isEmpty() ? null : this.getSubWindows().get(0));
    }

    /**
     * Creates a new diagram with default parameters.
     */
    public void createSubWindow(){
        this.addSubWindow(0, new DiagramWindow());
        getPaintBoard().refresh();
    }

    /**
     * Duplicate the currently active subwindow.
     */
    public void duplicateSubWindow() {
        if (getActiveSubwindow() != null) {
            this.addSubWindow(0, new SubWindow(getActiveSubwindow()));
            getPaintBoard().refresh();
        }
    }

    /**
     * Adds a subwindow at the given index.
     *
     * @param index The index at which the subwindow should be inserted.
     * @param subwindow The subwindow that is to be added.
     */
    private void addSubWindow(int index, SubWindow subwindow){
        this.getSubWindows().add(index, subwindow);
    }

    /**
     * Remove the given subwindow from this controller's list of subwindows.
     *
     * @param subWindow The subwindow that is to be removed.
     * @return The subwindow that was removed.
     */
    private SubWindow removeSubWindow(SubWindow subWindow){
        return getSubWindows().remove(this.getSubWindows().indexOf(subWindow));
    }

    /**
     * Returns the subwindows of this controller.
     */
    private ArrayList<SubWindow> getSubWindows(){
        return subWindows;
    }

    /**
     * Activate the subwindow at the given x and y.
     *
     * @param x The x coordinate at which the subwindow lies.
     * @param y The y coordinate at which the subwindow lies.
     * @throws NoSuchWindowException If no subwindow lies at the given coordinates.
     */
    public void activateSubWindow(int x, int y) throws NoSuchWindowException {
        if (getSubWindowAt(x, y) == null)
            throw new NoSuchWindowException();
        this.addSubWindow(0, removeSubWindow(getSubWindowAt(x, y)));
    }

    /**
     * Close the subwindow at the given coordinates.
     *  The given coordinates should lie within the subwindow's close button.
     *
     * @param x The x coordinate for the subwindow.
     * @param y The y coordinate for the subwindow.
     * @throws InvalidCloseWindowException When the given coordinates don't lie in any subwindow's close button.
     */
    public void closeSubWindow(int x, int y) throws InvalidCloseWindowException {
        SubWindow subWindow = this.getSubWindows().stream().filter( s -> s.closeButtonEncloses(x,y)).findFirst().orElse(null);
        if (subWindow != null && !(isEditing() && subWindow == getActiveSubwindow())) {
            subWindow.close();
            this.removeSubWindow(subWindow);
            getPaintBoard().refresh();
        }
        else
            throw new InvalidCloseWindowException();
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
    public void moveSubWindow(int fromX, int fromY, int toX, int toY) throws InvalidMoveWindowException {
        if (getActiveSubwindow() != null) {
            getActiveSubwindow().move(fromX, fromY, toX, toY);
            getPaintBoard().refresh();
        }
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
        if (getActiveSubwindow() != null) {
            getActiveSubwindow().resize(fromX, fromY, toX, toY);
            getPaintBoard().refresh();
        }
    }

    /**
     * Get the subwindow at the given coordinates.
     *
     * @param x The x coordinate to look at.
     * @param y The y coordinate to look at.
     * @return The subwindow at the given coordinates or null if there is none.
     */
    private SubWindow getSubWindowAt(int x, int y) {
        return this.getSubWindows().stream().filter( s -> s.getBorderedFrame().encloses(x,y)).findFirst().orElse(null);
    }

    /**
     * The list of all diagram views kept by this diagram handler.
     */
    private ArrayList<SubWindow> subWindows = new ArrayList<SubWindow>();

    /**
     * Add a new party to the active subwindow at the given x and y coordinate.
     *
     * @param x The x coordinate for the new party.
     * @param y The y coordinate for the new party.
     * @throws InvalidAddPartyException The operation was not successful.
     */
    public void addParty(int x, int y) throws InvalidAddPartyException {
        if (getActiveSubwindow() != null && !isEditing()) {
            getActiveSubwindow().addParty(
                    x - getActiveSubwindow().getFrame().getX(),
                    y - getActiveSubwindow().getFrame().getY());
            getPaintBoard().refresh();
        }
    }

    /**
     * Adds a message to the active subwindow from the given start coordinate to the given end coordinate.
     *
     * @param fromX The start x coordinate for the add.
     * @param fromY The start y coordinate for the add.
     * @param toX The end x coordinate for the add.
     * @param toY The end y coordinate for the add.
     * @throws InvalidAddMessageException The operation was not successful.
     */
    public void addMessage(int fromX, int fromY, int toX, int toY) throws InvalidAddMessageException {
        if (getActiveSubwindow() != null) {
            getActiveSubwindow().addMessage(
                    fromX - getActiveSubwindow().getFrame().getX(),
                    fromY - getActiveSubwindow().getFrame().getY(),
                    toX - getActiveSubwindow().getFrame().getX(),
                    toY - getActiveSubwindow().getFrame().getY());
            getPaintBoard().refresh();
        }
    }

    /**
     * The x & y coordinates of the component.
     *
     * @param x The x coordinate of the component that is to be selected.
     * @param y The y coordinate of the component that is to be selected.
     */
    public void selectComponent(int x, int y) {
        if (getActiveSubwindow() != null) {
            getActiveSubwindow().selectComponent(
                    x - getActiveSubwindow().getFrame().getX(),
                    y - getActiveSubwindow().getFrame().getY());
            getPaintBoard().refresh();
        }
    }

    /**
     * Switches the type of the party at the given coordinate, or null if there is none.
     *
     * @param x The x coordinate for the party.
     * @param y The y coordinate for the party.
     */
    public void switchTypeOfParty(int x, int y) {
        if (getActiveSubwindow() != null && !isEditing()) {
            getActiveSubwindow().switchTypeOfParty(
                    x - getActiveSubwindow().getFrame().getX(),
                    y - getActiveSubwindow().getFrame().getY());
            getPaintBoard().refresh();
        }
    }

    /**
     * Moves the party at the given start coordinates to the given end coordinates.
     *
     * @param fromX The start x coordinate for the add.
     * @param fromY The start y coordinate for the add.
     * @param toX The end x coordinate for the add.
     * @param toY The end y coordinate for the add.
     * @throws NoSuchPartyException If there is no party at the given start coordinates.
     * @throws InvalidMovePartyException If the party could not be moved to the given end coordinates.
     */
    public void moveParty(int fromX, int fromY, int toX, int toY) throws NoSuchPartyException, InvalidMovePartyException {
        if (getActiveSubwindow() != null) {
            getActiveSubwindow().moveParty(
                    fromX - getActiveSubwindow().getFrame().getX(),
                    fromY - getActiveSubwindow().getFrame().getY(),
                    toX - getActiveSubwindow().getFrame().getX(),
                    toY - getActiveSubwindow().getFrame().getY());
            getPaintBoard().refresh();
        }
    }

    /**
     * Removes all components in the current selection from this controller's diagram.
     */
    public void deleteSelection() {
        if (getActiveSubwindow() != null) {
            getActiveSubwindow().deleteSelection();
            getPaintBoard().refresh();
        }
    }

    /**
     * Returns whether or not the active subwindow is currently editing a component.
     *
     * @return True if and only if the active subwindow exists and its selection is active.
     */
    public boolean isEditing() {
        return (getActiveSubwindow() != null && getActiveSubwindow().selectionIsActive());
    }

    /**
     * Terminate the current editing session, if any.
     *
     * @throws InvalidLabelException If the current label for the editing session is not a valid one
     *  for the selected component.
     */
    public void abortEditing() throws InvalidLabelException {
        if (isEditing()) {
            getActiveSubwindow().deselectAll();
            getPaintBoard().refresh();
        }
    }

    /**
     * Append the given char to the current edit session.
     *
     * @param c The char that is to be appended.
     */
    public void appendChar(char c) {
        if (isEditing()) {
            getActiveSubwindow().setSelectedLabel(getActiveSubwindow().getSelectedLabel() + c);
            getPaintBoard().refresh();
        }
    }

    /**
     * Removes the last char from the label of the active component.
     */
    public void removeLastChar() {
        if (isEditing()) {
            String temp = getActiveSubwindow().getSelectedLabel();
            if (temp.length() > 0) {
                getActiveSubwindow().setSelectedLabel(temp.substring(0, temp.length()-1));
                getPaintBoard().refresh();
            }
        }
    }

    /**
     * Process the given command.
     *
     * @param command The command that is to be processed.
     * @throws CommandNotProcessedException The command failed to be executed.
     */
    public void processCommand(Command command) throws CommandNotProcessedException {
        command.execute(getActiveSubwindow());
        getPaintBoard().refresh();
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
        new Controller(args.length > 0 && args[0].equalsIgnoreCase("-r"));
    }

}