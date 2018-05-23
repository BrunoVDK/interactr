package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.exceptions.InvalidCloseWindowException;
import interactr.cs.kuleuven.be.exceptions.InvalidMoveWindowException;
import interactr.cs.kuleuven.be.exceptions.InvalidResizeWindowException;
import interactr.cs.kuleuven.be.ui.command.*;
import interactr.cs.kuleuven.be.ui.geometry.Point;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

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
     * @param controller The diagram controller to associate this event handler with.
     */
    public EventHandler(Controller controller) {
        setController(controller);
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
        try {
            if (getController() != null) {
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
        catch (Exception ignored) {}
    }

    /**
     * Handle a mouse click at the given coordinates and with given click count.
     *
     * @param x The x coordinate of the mouse click.
     * @param y The y coordinate of the mouse click.
     * @param clickCount The click count.
     */
    private void handleMouseClick(int x, int y, int clickCount) {
        getController().activateSubWindow(x, y);
        switch (clickCount) {
            case 1: // Single click
                handleSingleMouseClick(x,y);
                break;
            case 2: // Double click
                handleDoubleMouseClick(x,y);
                break;
        }
    }

    /**
     * Handle a single mouse click at the given coordinates.
     *
     * @param x The x coordinate of the mouse click.
     * @param y The y coordinate of the mouse click.
     */
    private void handleSingleMouseClick(int x, int y) {
        getController().activateSubWindow(x, y);
        try {
            getController().closeSubWindow(x,y);
        }
        catch (InvalidCloseWindowException e) {
            getController().processCommand(new SelectCommand(new Point(x,y)));
        }
    }

    /**
     * Handle a double mouse click at the given coordinates.
     *
     * @param x The x coordinate of the mouse click.
     * @param y The y coordinate of the mouse click.
     */
    private void handleDoubleMouseClick(int x, int y) {
        try {
            getController().processCommand(new AddPartyCommand(new Point(x,y)));
        }
        catch (CommandNotProcessedException exception) {
            getController().processCommand(new SwitchPartyCommand(new Point(x,y)));
        }
    }

    /**
     * Handle a mouse press at the given coordinates.
     *
     * @param x The x coordinate of the mouse press.
     * @param y The y coordinate of the mouse press.
     */
    private void handleMousePress(int x, int y) {
        getController().activateSubWindow(x,y);
        lastDragCoordinate = new Point(x,y);
        movePartyCommand = null;
    }

    /**
     * Handle a mouse drag at the given coordinates.
     *
     * @param x The x coordinate of the mouse drag.
     * @param y The y coordinate of the mouse drag.
     */
    private void handleMouseDrag(int x, int y) {
        if (movePartyCommand != null) {
            movePartyCommand.setTargetLocation(new Point(x,y));
            getController().processCommand(movePartyCommand);
        }
        else {
            try {
                getController().moveSubWindow(lastDragCoordinate.getX(), lastDragCoordinate.getY(), x, y);
            }
            catch (InvalidMoveWindowException e1) {
                try {
                    getController().resizeSubWindow(lastDragCoordinate.getX(), lastDragCoordinate.getY(), x, y);
                }
                catch (InvalidResizeWindowException e2) {
                    movePartyCommand = new MovePartyCommand(new Point(x, y), new Point(x, y));
                    return;
                }
            }
            lastDragCoordinate = new Point(x,y);
        }
    }

    /**
     * Handle a mouse release at the given coordinates.
     *
     * @param x The x coordinate of the mouse release.
     * @param y The y coordinate of the mouse release.
     */
    private void handleMouseReleased(int x, int y) {
        if (movePartyCommand == null || !movePartyCommand.movedParty())
            try {getController().processCommand(new AddMessageCommand(lastDragCoordinate, new Point(x,y)));}
            catch (Exception ignored) {}
    }

    /**
     * Registers the target coordinate for the last successful drag session.
     */
    private Point lastDragCoordinate;

    /**
     * Registers the last move party command.
     */
    private MovePartyCommand movePartyCommand;

    /**
     * Handle the key event of given type, having the given key code and key char.
     *
     * @param id The type of key event.
     * @param keyCode The key code for the key event.
     * @param keyChar The key char for the key event.
     * @param keyModifiers The key modifiers for the key event.
     */
    void handleKeyEvent(int id, int keyCode, char keyChar, int keyModifiers) {
        try {
            if (getController() != null) {
                boolean controlIsPressed = (keyModifiers & KeyEvent.CTRL_DOWN_MASK) != 0;
                switch (id) {
                    case KeyEvent.KEY_PRESSED:
                        handleKeyPress(keyCode, controlIsPressed);
                        break;
                    case KeyEvent.KEY_TYPED:
                        handleKeyTyped(keyChar);
                        break;
                }
            }
        }
        catch (Exception ignored) {}
    }

    /**
     * Handle a key press.
     *
     * @param keyCode The key code for the key press.
     * @param controlIsPressed True if and only if the key was pressed while the control key was pressed.
     */
    private void handleKeyPress(int keyCode, boolean controlIsPressed) {
        if (keyCode == KeyEvent.VK_ENTER){
            if (controlIsPressed)
                getController().createDialog();
            else
                getController().processCommand(new AbortEditingCommand());
        }
        else if (keyCode == KeyEvent.VK_DOWN)
            getController().processCommand(new GoDownCommand());
        else if (keyCode == KeyEvent.VK_UP)
            getController().processCommand(new GoUpCommand());
        else if (keyCode == KeyEvent.VK_BACK_SPACE)
            getController().processCommand(new RemoveLastCharCommand());
        else if (keyCode == KeyEvent.VK_DELETE)
            getController().processCommand(new DeleteCommand());
        else if (keyCode == KeyEvent.VK_N && controlIsPressed)
            getController().createSubWindow();
        else if (keyCode == KeyEvent.VK_D && controlIsPressed)
            getController().duplicateSubWindow();
        else if (keyCode == KeyEvent.VK_SPACE)
            getController().processCommand(new ActivateFocusCommand());
    }

    /**
     * Handle a key typed event.
     *
     * @param keyChar The key char for the key that was typed.
     */
    private void handleKeyTyped(char keyChar) {
        if (keyChar == KeyEvent.VK_TAB)
            getController().processCommand(new FocusNextCommand());
        else if (AppendCharCommand.canHaveAsChar(keyChar))
            getController().processCommand(new AppendCharCommand(keyChar));
    }

    /**
     * Returns the diagram controller associated with this event handler.
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Set the diagram controller for this event handler to the given one.
     *
     * @param controller The new diagram controller for this event handler.
     */
    private void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Variable registering the diagram controller of this event handler.
     */
    private Controller controller;

}