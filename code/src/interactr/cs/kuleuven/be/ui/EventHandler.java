package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.exceptions.InvalidAddPartyException;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import interactr.cs.kuleuven.be.exceptions.*;
import interactr.cs.kuleuven.be.ui.geometry.Point;

/**
 * A class of event handlers for interpreting incoming mouse/key events
 *  and forwarding them to the associated diagram controller.
 *
 * @author Team 25
 * @version 1.0
 */
public class EventHandler {

    /**
     * Initialize this new event handler with given diagram controller.
     *
     * @param diagramController The diagram controller to associate this event handler with.
     */
    public EventHandler(DiagramController diagramController) {
        setDiagramController(diagramController);
    }

    /**
     * Handle the mouse event at given location, of given type and with given click count.
     * Nothing can be done when in editing mode
     *
     * @param id The type of the mouse event.
     * @param x The x coordinate for the mouse event.
     * @param y The y coordinate for the mouse event.
     * @param clickCount The amount of clicks for this mouse events.
     */
    public void handleMouseEvent(int id, int x, int y, int clickCount) throws IllegalArgumentException {

        // Mouse events are ignored when the diagram controller is editing
        if (getDiagramController() != null) {
            switch (id) {
                case MouseEvent.MOUSE_CLICKED:
                    handleMouseClick(x,y,clickCount);
                    break;
                case MouseEvent.MOUSE_PRESSED:
                    handleMousePress(x,y);
                    break;
                case MouseEvent.MOUSE_DRAGGED:
                    handleMouseDrag(x,y);
                    break;
                case MouseEvent.MOUSE_RELEASED:
                    handleMouseReleased(x,y);
                    break;
            }
        }

    }

    /**
     * Handle a mouse click at the given coordinates and with given click count.
     *
     * @param x The x coordinate of the mouse click.
     * @param y The y coordinate of the mouse click.
     * @param clickCount The click count.
     */
    private void handleMouseClick(int x, int y, int clickCount) {
        try {
            getDiagramController().activateSubWindow(x, y);
            switch (clickCount) {
                case 1: // Single click
                    try {
                        getDiagramController().closeSubWindow(x,y);
                    }
                    catch (InvalidCloseWindowException e) {
                        getDiagramController().selectComponent(x,y);
                    }
                    break;
                case 2: // Double click
                    try {
                        getDiagramController().addParty(x,y);
                    }
                    catch (InvalidAddPartyException exception) {
                        getDiagramController().switchTypeOfParty(x, y);
                    }
                    break;
            }
        }
        catch (NoSuchWindowException ignored) {}
    }

    /**
     * Handle a mouse press at the given coordinates.
     *
     * @param x The x coordinate of the mouse press.
     * @param y The y coordinate of the mouse press.
     */
    private void handleMousePress(int x, int y) {
        lastDragCoordinate = new Point(x,y); // Keep track of drag coordinates
        try {
            getDiagramController().activateSubWindow(x,y);
            this.setDragOperationType(DragOperationType.DRAG_VALID);
        }
        catch (NoSuchWindowException ignored) {}
    }

    /**
     * Handle a mouse drag at the given coordinates.
     *
     * @param x The x coordinate of the mouse drag.
     * @param y The y coordinate of the mouse drag.
     */
    private void handleMouseDrag(int x, int y) {

        // Nothing to drag
        if (dragOperationType == DragOperationType.DRAG_NONE)
            return;

        // Prioritise dragging in a diagram if it was previously successful
        //  This prevents the alteration of subwindow frames when dragging something in a diagram
        //  close to the subwindow's borders / title bar
        if (dragOperationType == DragOperationType.DRAG_DIAGRAM) {
            moveParty(x,y);
        }
        else {
            try {
                getDiagramController().resizeSubWindow(lastDragCoordinate.getX(), lastDragCoordinate.getY(), x, y);
            }
            catch (InvalidResizeWindowException e1) {
                try {
                    getDiagramController().moveSubWindow(lastDragCoordinate.getX(), lastDragCoordinate.getY(), x, y);
                }
                catch (InvalidMoveWindowException e2) {
                    moveParty(x,y);
                    return;
                }
            }
            lastDragCoordinate = new Point(x,y);
        }

    }

    /**
     * Move a party at the given coordinates.
     *
     * @param x The x coordinate to move the party to.
     * @param y The y coordinate to move the party to.
     */
    private void moveParty(int x, int y) {
        try {

            // Move party
            getDiagramController().moveParty(lastDragCoordinate.getX(), lastDragCoordinate.getY(), x, y);

            // Move party was successful, keep track of the changes
            this.setDragOperationType(DragOperationType.DRAG_DIAGRAM);
            lastDragCoordinate = new Point(x,y);

        }
        catch (Exception ignored) {}
    }

    /**
     * Handle a mouse release at the given coordinates.
     *
     * @param x The x coordinate of the mouse release.
     * @param y The y coordinate of the mouse release.
     */
    private void handleMouseReleased(int x, int y) {
        if (dragOperationType == DragOperationType.DRAG_VALID)
            try {
                getDiagramController().addMessage(lastDragCoordinate.getX(), lastDragCoordinate.getY(), x, y);
            }
            catch (InvalidAddMessageException ignored) {}
        this.setDragOperationType(DragOperationType.DRAG_NONE);
    }

    /**
     * An enumeration of drag operation types.
     *  This is used to override the priority of a particular type of operation.
     *  Default priority :
     *   RESIZE > MOVE > DRAG IN DIAGRAM
     *  If something is dragged within a diagram, this operation type is prioritised.
     */
    private enum DragOperationType {
        DRAG_NONE, // When nothing at all is dragged
        DRAG_VALID, // When anything is dragged
        DRAG_DIAGRAM; // When something is dragged within a diagram
    }

    private void setDragOperationType(DragOperationType type){
        this.dragOperationType = type;
    }

    /**
     * Registers the current type of dragging operation.
     */
    private DragOperationType dragOperationType = DragOperationType.DRAG_NONE;

    /**
     * The coordinates for the last successful drag operation.
     */
    private Point lastDragCoordinate;

    private boolean controlIsPressed = false;

    /**
     * Handle the key event of given type, having the given key code and key char.
     *
     * @param id The type of key event.
     * @param keyCode The key code for the key event.
     * @param keyChar The key char for the key event.
     * @param keyModifiers The key modifiers for the key event.
     */
    void handleKeyEvent(int id, int keyCode, char keyChar, int keyModifiers) {
        if (getDiagramController() != null) {
            boolean controlIsPressed = (keyModifiers & KeyEvent.CTRL_DOWN_MASK) != 0;
            switch (id) {
                case KeyEvent.KEY_PRESSED:
                    if (keyCode == KeyEvent.VK_ENTER) {
                        try {
                            getDiagramController().abortEditing();
                        }
                        catch (InvalidLabelException ignored) {}
                    }
                    else if (keyCode == KeyEvent.VK_BACK_SPACE) {
                        if (getDiagramController().isEditing())
                            getDiagramController().removeLastChar();
                        else
                            getDiagramController().deleteSelection();
                    }
                    else if (keyCode == KeyEvent.VK_N && controlIsPressed)
                        getDiagramController().createSubWindow();
                    else if (keyCode == KeyEvent.VK_D && controlIsPressed)
                        getDiagramController().duplicateSubWindow();
                    else if (keyCode == KeyEvent.VK_CONTROL)
                        controlIsPressed = true;
                    break;
                case KeyEvent.KEY_TYPED:
                    if (keyChar == KeyEvent.VK_TAB && !getDiagramController().isEditing())
                        getDiagramController().toggleActiveSubWindowView();
                    else if (Character.isLetter(keyChar)
                            || Character.isDigit(keyChar)
                            || ":();-_<>*&[]".indexOf(Character.toString(keyChar)) != -1)
                        getDiagramController().appendChar(keyChar);
                    break;
            }
        }
    }

    /**
     * Returns the diagram controller associated with this event handler.
     */
    public DiagramController getDiagramController() {
        return diagramController;
    }

    /**
     * Set the diagram controller for this event handler to the given one.
     *
     * @param diagramController The new diagram controller for this event handler.
     */
    private void setDiagramController(DiagramController diagramController) {
        this.diagramController = diagramController;
    }

    /**
     * Variable registering the diagram controller of this event handler.
     */
    private DiagramController diagramController;

}