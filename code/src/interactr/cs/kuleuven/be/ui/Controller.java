package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.exceptions.*;
import interactr.cs.kuleuven.be.ui.command.Command;
import interactr.cs.kuleuven.be.ui.command.CommandNotProcessedException;
import interactr.cs.kuleuven.be.ui.command.CreateDialogCommand;
import interactr.cs.kuleuven.be.ui.command.DuplicateDiagramCommand;
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
        for (int i=getSubWindows().size()-1 ; i>=0 ; i--) // Last window first
            getSubWindows().get(i).display(getPaintBoard());
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
     * Creates a new dialog for the currently active subwindow's selection and adds it to the subwindow
     *  list.
     */
    public void createDialog() {
        CreateDialogCommand command = new CreateDialogCommand();
        processCommand(command);
        addSubWindow(0, command.getDialogWindow());
    }

    /**
     * Creates a new diagram with default parameters.
     */
    public void createSubWindow() {
        this.addSubWindow(0, new DiagramWindow());
        getPaintBoard().refresh();
    }

    /**
     * Duplicate the currently active subwindow.
     */
    public void duplicateSubWindow() {
        if (getActiveSubwindow() != null) {
            DuplicateDiagramCommand command = new DuplicateDiagramCommand();
            processCommand(command);
            addSubWindow(0, command.getDiagramWindow());
            getPaintBoard().refresh();
        }
    }

    /**
     * Adds a subwindow at the given index.
     *
     * @param index The index at which the subwindow should be inserted.
     * @param subwindow The subwindow that is to be added.
     */
    private void addSubWindow(int index, SubWindow subwindow) {
        if (subwindow == null)
            throw new IllegalArgumentException();
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
        this.addSubWindow(0, removeSubWindow(getSubWindowAt(x,y)));
        getPaintBoard().refresh();
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
        if (subWindow != null) {
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
     * Process the given command.
     *
     * @param command The command that is to be processed.
     * @throws CommandNotProcessedException The command failed to be executed.
     */
    public void processCommand(Command command) throws CommandNotProcessedException {
        if (command == null)
            throw new CommandNotProcessedException();
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