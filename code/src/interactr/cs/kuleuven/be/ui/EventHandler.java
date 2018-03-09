package interactr.cs.kuleuven.be.ui;

import interactr.cs.kuleuven.be.exceptions.InvalidAddPartyException;


import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import interactr.cs.kuleuven.be.domain.*;
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
    public void handleMouseEvent(int id, int x, int y, int clickCount) throws IllegalArgumentException{

        // Mouse events are ignored when the diagram controller is editing
        if (getDiagramController() != null && ! getDiagramController().isEditing()) {

            // Mouse click
            if (id == MouseEvent.MOUSE_CLICKED) {
                switch (clickCount) {
                    case 1: // Single click
                        getDiagramController().selectComponentAt(x,y);
                        break;
                    case 2: // Double click
                        try {
                            getDiagramController().addPartyAt(x,y);
                        }
                        catch (InvalidAddPartyException exception) {
                            Party party = getDiagramController().getPartyAt(x, y);
                            if (party != null)
                                getDiagramController().switchPartyType(party);
                        }
                        break;
                }
            }

            // Mouse press
            if(id == MouseEvent.MOUSE_PRESSED){
                focusedParty = getDiagramController().getPartyAt(x,y);
                focusCoordinate = new Point(x,y);
            }

            // Mouse drag
            if(id == MouseEvent.MOUSE_DRAGGED){
                if(focusedParty != null){
                    getDiagramController().moveParty(focusedParty, x, y);
                }
            }

            // Mouse release
            if(id == MouseEvent.MOUSE_RELEASED){
                if (focusedParty == null && focusCoordinate != null) {
                    getDiagramController().addMessageFrom(focusCoordinate.getX()
                            ,focusCoordinate.getY(),x,y);
                }
                else if(focusedParty != null) {
                    focusedParty = null;
                    focusCoordinate = null;
                }

            }

        }

    }

    /**
     * Handle the key event of given type, having the given key code and key char.
     *
     * @param id The type of key event.
     * @param keyCode The key code for the key event.
     * @param keyChar The key char for the key event.
     */
    void handleKeyEvent(int id, int keyCode, char keyChar) {

        if (getDiagramController() != null) {
            switch (id) {
                case KeyEvent.KEY_PRESSED:
                    if (keyCode == KeyEvent.VK_ENTER)
                        getDiagramController().abortEditing();
                    else if (keyCode == KeyEvent.VK_BACK_SPACE) {
                        if (getDiagramController().isEditing())
                            getDiagramController().removeLastChar();
                        else
                            getDiagramController().deleteSelection();
                    }
                    break;
                case KeyEvent.KEY_TYPED:
                    if (keyChar == KeyEvent.VK_TAB && !getDiagramController().isEditing())
                        getDiagramController().nextView();
                    else if (Character.isLetter(keyChar) || Character.isDigit(keyChar) || keyChar == ':')
                        getDiagramController().appendChar(keyChar);

            }
        }

    }

    /**
     * Registers the focused party for this event handler.
     */
    private Party focusedParty = null;

    /**
     * Registers the focus coordinate for this event handler.
     */
    private Point focusCoordinate = null;

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
     * @post The diagram controller associated with this event handler matches the given one.
     */
    public void setDiagramController(DiagramController diagramController) {
        this.diagramController = diagramController;
    }

    /**
     * Variable registering the diagram controller of this event handler.
     */
    private DiagramController diagramController;

}